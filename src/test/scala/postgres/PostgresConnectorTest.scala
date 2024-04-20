package postgres

import cats.effect.unsafe.IORuntime
import common.Config.{Database, DatabaseConfig, Schema}
import org.scalatest.{EitherValues, GivenWhenThen}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PostgresConnectorTest extends AnyFlatSpec with GivenWhenThen with Matchers with EitherValues {

  implicit val ioRuntime: IORuntime = IORuntime.global

  "connections method" should "return right with all viable configurations" in {
    Given("two valid configurations")
    val config1 = DatabaseConfig("localhost",
      "5432",
      "docker",
      "docker",
      Database("postgres_1"),
      Schema("public"))
    val config2 = DatabaseConfig("localhost",
      "5432",
      "docker",
      "docker",
      Database("postgres_2"),
      Schema("public"))
    val configs = List(config1, config2)

    When("creating connections from these configurations")
    val connector = new PostgresConnector(configs)
    val result = connector.connections.unsafeRunSync()

    Then("result is right")
    assert(result.isRight)

    And("contains all valid connections")
    result.value.size shouldBe configs.size
  }

  it should "return left when all of the connections are invalid" in {
    Given("two invalid configurations")
    val config1 = DatabaseConfig("", "", "", "", Database(""), Schema(""))
    val config2 = DatabaseConfig("", "", "", "", Database(""), Schema(""))
    val configs = List(config1, config2)

    When("creating connections from these configurations")
    val connector = new PostgresConnector(configs)
    val result = connector.connections.unsafeRunSync()

    Then("result is left")
    assert(result.isLeft)
  }

  it should "return left when one connections is invalid and the rest is valid" in {
    Given("one invalid configuration")
    val config1 = DatabaseConfig("", "", "", "", Database(""), Schema(""))

    And("one valid configuration")
    val config2 = DatabaseConfig("localhost",
      "5432",
      "docker",
      "docker",
      Database("postgres_2"),
      Schema("public"))

    val configs = List(config1, config2)

    When("creating connections from these configurations")
    val connector = new PostgresConnector(configs)
    val result = connector.connections.unsafeRunSync()

    Then("result is left")
    assert(result.isLeft)
  }

}

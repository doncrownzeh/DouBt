package postgres.services

import cats.effect.unsafe.IORuntime
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{EitherValues, GivenWhenThen}

class PostgresValidationServiceTest extends AnyFlatSpec with GivenWhenThen with Matchers with EitherValues {

  implicit val ioRuntime: IORuntime = IORuntime.global

  behavior of "checking tables names"

  "tables validation" should "return success true if all DBs have exactly the same tables" in {
    Given("Three DBs with exactly the same tables")
    val firstDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val secondDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val thirdDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val allTables = List(firstDbTables, secondDbTables, thirdDbTables)

    When("Validating those DBs")
    val postgresValidationService = createTestService(allTables)
    val result = postgresValidationService.validateTableNames().unsafeRunSync().value

    Then("Validation returns success true")
    result.success shouldBe true
  }

  it should "return success false if at least one DB has different tables" in {
    Given("Two DBs with exactly the same tables")
    val firstDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val secondDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")

    And("One DB with different tables")
    val differentTables = List("DIFF_1", "DIFF_2", "DIFF_3")

    val allTables = List(firstDbTables, secondDbTables, differentTables)

    When("Validating those DBs")
    val postgresValidationService = createTestService(allTables)
    val result = postgresValidationService.validateTableNames().unsafeRunSync().value

    Then("Validation returns success false")
    result.success shouldBe false
  }


  it should "return success false if at least one DB has too many tables" in {
    Given("Two DBs with exactly the same tables")
    val firstDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val secondDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")

    And("One DB with more tables")
    val tooManyTables = List("TABLE_1", "TABLE_2", "TABLE_3", "TABLE_4")

    val allTables = List(firstDbTables, secondDbTables, tooManyTables)

    When("Validating those DBs")
    val postgresValidationService = createTestService(allTables)
    val result = postgresValidationService.validateTableNames().unsafeRunSync().value

    Then("Validation returns success false")
    result.success shouldBe false
  }

  it should "return success false if at least one DB is missing any table" in {
    Given("Two DBs with exactly the same tables")
    val firstDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")
    val secondDbTables = List("TABLE_1", "TABLE_2", "TABLE_3")

    And("One DB with less tables")
    val tooManyTables = List("TABLE_1", "TABLE_2")

    val allTables = List(firstDbTables, secondDbTables, tooManyTables)

    When("Validating those DBs")
    val postgresValidationService = createTestService(allTables)
    val result = postgresValidationService.validateTableNames().unsafeRunSync().value

    Then("Validation returns success false")
    result.success shouldBe false
  }

  private def createTestService(tablesToReturn: List[List[String]]) = {
    implicit val postgresConnectionService: PostgresConnectionServiceStub = new PostgresConnectionServiceStub(tablesToReturn)
    new PostgresValidationService()
  }

}

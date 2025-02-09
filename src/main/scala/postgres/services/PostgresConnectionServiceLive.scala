package postgres.services

import cats.effect._
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging
import common.Config._
import common.interface.DatabaseConnection
import doobie.Transactor
import doobie.implicits._
import postgres.domain.PostgresConnection


class PostgresConnectionServiceLive(configs: List[DatabaseConfig]) extends LazyLogging with PostgresConnectionService {


  override def connections: IO[Either[String, List[DatabaseConnection]]] = configs.map(createConnectionFromConfig)
    .traverse(testConnection)
    .map(handleCheckedConnections)

  private def createConnectionFromConfig(config: DatabaseConfig) = {
    val url = s"jdbc:postgresql://${config.host}:${config.port}/${config.database}"
    logger.info(s"creating connection for: $url")
    val transactor = Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver",
      url = url,
      user = config.username,
      password = config.password,
      logHandler = None
    )
    PostgresConnection(transactor, config)
  }

  private def testConnection(connection: PostgresConnection) = {
    logger.info(s"testing connection: ${connection.description}")
    testConnectionQuery.option.transact(connection.transactor).attempt map {
      case Right(_) =>
        logger.info(s"Successfully connected to DB: ${connection.description}")
        connection.asRight[String]
      case Left(error) =>
        val res = s"[ERROR] Couldn't establish connection to DB: ${connection.description}, error: $error"
        logger.error(res)
        res.asLeft
    }
  }

  private val testConnectionQuery = sql"""select 1""".query[Boolean]

  private def handleCheckedConnections(checkedConnections: List[Either[String, PostgresConnection]]) = {
    val (invalid, valid) = checkedConnections.separateFoldable
    if (invalid.isEmpty) valid.asRight
    else invalid.mkString("\n").asLeft
  }

}
  
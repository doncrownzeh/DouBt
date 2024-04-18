package postgres

import cats.effect._
import common.Config._
import doobie.Transactor

class PostgresConnector(configs: List[DatabaseConfig]) {
  lazy val connections: List[PostgresConnection] = configs.map { config =>
    val transactor = Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver",
      url = s"jdbc:postgresql://${config.host}:${config.port}/${config.database}",
      user = config.username,
      password = config.password,
      logHandler = None
    )
    PostgresConnection(transactor, config.schema)
  }
}

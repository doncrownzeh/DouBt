package postgres.services

import cats.effect.IO
import common.interface.DatabaseConnection
import postgres.domain.PostgresConnection

trait PostgresConnectionService {

  def connections: IO[Either[String, List[DatabaseConnection]]]
}

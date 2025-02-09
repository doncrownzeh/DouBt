package postgres.services

import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}
import common.interface.DatabaseConnection

class PostgresConnectionServiceStub(tablesToReturn: List[List[String]] = List.empty) extends PostgresConnectionService {

  override def connections: IO[Either[String, List[DatabaseConnection]]] = {
    tablesToReturn.map { tableList =>
      new DatabaseConnection {
        override def description: String = ""

        override def getTables: IO[List[String]] = tableList.pure[IO]

        override def getColumns: IO[List[String]] = List.empty.pure[IO]

        override def getConstraints: IO[List[String]] = List.empty.pure[IO]

        override def getIndices: IO[List[String]] = List.empty.pure[IO]
      }
    }
  }.asRight.pure[IO]
}

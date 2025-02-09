package postgres.services

import cats.data.EitherT
import cats.effect.IO
import cats.implicits.toTraverseOps
import common.interface.{DatabaseConnection, TablesValidation}
import common.model.TablesValidationResult

import scala.annotation.tailrec

class PostgresValidationService(implicit postgresConnectionService: PostgresConnectionService) extends TablesValidation {

  override def validateTableNames(): IO[Either[String, TablesValidationResult]] = {
    (for {
      connections <- EitherT[IO, String, List[DatabaseConnection]](postgresConnectionService.connections)
      results <- EitherT.right[String](connections.traverse(_.getTables))
      success = compareTables(results)
    } yield TablesValidationResult.create(success)).value
  }

  private def compareTables(tables: List[List[String]]): Boolean = {
    @tailrec
    def loop(tables: List[List[String]], acc: Boolean = true): Boolean = {
      tables match {
        case Nil => acc
        case _ :: Nil => acc
        case first :: second :: tail =>
          val remaining = second :: tail
          loop(remaining, first == second)
      }
    }

    loop(tables)
  }
}

package common.interface

import cats.effect.IO

// TODO correct types
trait DatabaseConnection {
  def getTables: IO[List[String]]

  def getColumns: IO[List[String]]

  def getConstraints: IO[List[String]]

  def getIndices: IO[List[String]]
}

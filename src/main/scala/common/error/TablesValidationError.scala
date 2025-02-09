package common.error

import common.model.TablesValidationResult

sealed trait TablesValidationError {
  def message: String
}

object TablesValidationError {
  case class NamesMismatch(result: TablesValidationResult) extends TablesValidationError {
    override def message: String = "???" // TODO
  }
}

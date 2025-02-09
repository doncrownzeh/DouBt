package common.interface

import cats.effect.IO
import common.model.TablesValidationResult

trait TablesValidation {

  def validateTableNames(): IO[Either[String, TablesValidationResult]]

}

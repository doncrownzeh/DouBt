package common.model

case class TablesValidationResult private(success: Boolean)

object TablesValidationResult {
  def create(success: Boolean): TablesValidationResult = new TablesValidationResult(success) // TODO
}

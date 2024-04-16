package common.model

case class DatabaseConstraint(name: String, table: String, column: String, referencedColumn: Option[String])

// TODO doobie GET
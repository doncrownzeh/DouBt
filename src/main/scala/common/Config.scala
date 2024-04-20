package common

object Config {

  case class Config(databaseConfigs: List[DatabaseConfig])

  case class DatabaseConfig(host: String, port: String, username: String, password: String, database: Database, schema: Schema)

  case class Database(value: String) extends AnyVal {
    override def toString: String = value
  }

  case class Schema(value: String) extends AnyVal {
    override def toString: String = value
  }

}
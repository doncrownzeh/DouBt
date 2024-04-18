package common

object Config {

  case class Config(databaseConfigs: List[DatabaseConfig])

  case class DatabaseConfig(host: String, port: String, username: String, password: String, database: Database, schema: Schema)

  class Database(val value: String) extends AnyVal {
    override def toString: String = value
  }

  class Schema(val value: String) extends AnyVal {
    override def toString: String = value
  }

}
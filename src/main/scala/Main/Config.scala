package Main

object Config {
  case class Config(databaseConfigs: List[DatabaseConfig])
  case class DatabaseConfig(host: String, port: String, username: String, password: String)
}
package common

import cats.data.EitherT
import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}
import common.interface.DatabaseConnection
import common.model.TablesValidationResult
import postgres.services.{PostgresConnectionServiceLive, PostgresValidationService}
import pureconfig._
import pureconfig.generic.ProductHint
import pureconfig.generic.auto._

import scala.io.Source

object Main extends IOApp.Simple {

  implicit def hint[A]: ProductHint[A] = ProductHint[A](ConfigFieldMapping(CamelCase, KebabCase))

  private val configPath = "common/main.conf"

  private def loadConfig: IO[Config.Config] = {
    def closeFile(source: Source): IO[Unit] = IO(source.close())

    def readLines(source: Source): IO[String] = IO(source.getLines().mkString("\n"))

    Resource.make(IO(Source.fromResource(configPath)))(closeFile).use(readLines).map { stringConfig =>
      val result = ConfigSource.string(stringConfig).load[Config.Config]

      result match {
        case Right(validConfig) => validConfig
        case _ => throw new IllegalStateException("invalid config")
      }
    }
  }

  override def run: IO[Unit] = {
    (for {
      config <- EitherT.right[String](loadConfig)
      postgresConnector = new PostgresConnectionServiceLive(config.databaseConfigs)
      postgresValidator = new PostgresValidationService()(postgresConnector)
      _ <- EitherT[IO, String, List[DatabaseConnection]](postgresConnector.connections)
      validationResult <- EitherT[IO, String, TablesValidationResult](postgresValidator.validateTableNames())
    } yield println(validationResult)).leftMap(err => System.err.println(err)).merge

  }

}

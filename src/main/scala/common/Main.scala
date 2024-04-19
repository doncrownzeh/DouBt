package common

import cats.data.EitherT
import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}
import postgres.PostgresConnector
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
      postgresConnector = new PostgresConnector(config.databaseConfigs)
      _ <- EitherT(postgresConnector.connections)
    } yield println("[SUCCESS]")).leftMap(err => System.err.println(err)).merge

  }

}

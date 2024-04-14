package Main

import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}
import pureconfig.generic.ProductHint
import pureconfig._
import pureconfig.generic.auto._
import cats.syntax.either._

import scala.io.Source

object Main extends IOApp.Simple {

  implicit def hint[A]: ProductHint[A] = ProductHint[A](ConfigFieldMapping(CamelCase, KebabCase))

  private val configPath = "main.conf"

  private def loadConfig: IO[Config.Config] = {
    def sourceIO: IO[Source] = IO(Source.fromResource(configPath))
    def closeFile(source: Source): IO[Unit] = IO(source.close())
    def readLines(source: Source): IO[String] = IO(source.getLines().mkString("\n"))
    Resource.make(sourceIO)(src => closeFile(src)).use(readLines).map{ stringConfig =>
      val result = ConfigSource.string(stringConfig).load[Config.Config]

      result match {
        case Right(validConfig) => validConfig
        case _ => throw new IllegalStateException("invalid config")
      }
    }

  }


  override def run: IO[Unit] = {
    for {
      config <- loadConfig
    } yield println(config)
  }

}

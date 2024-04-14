name := "DouBt"

version := "0.1"

scalaVersion := "2.13.13"

val doobieVersion = "1.0.0-RC4"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.17.6"
libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"      % doobieVersion,
  "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
)
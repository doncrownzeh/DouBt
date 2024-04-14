name := "DouBt"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.17.6"

libraryDependencies ++= Seq(

  // Start with this one
  "org.tpolecat" %% "doobie-core"      % "1.0.0-M5",

  // And add any of these as needed
  "org.tpolecat" %% "doobie-postgres"  % "1.0.0-M5",          // Postgres driver 42.6.0 + type mappings.
)
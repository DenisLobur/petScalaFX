name := "fx"
version := "0.1"
scalaVersion := "2.12.2"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  "org.postgresql" % "postgresql" % "42.1.4",
  "org.slf4j" % "slf4j-nop" % "1.7.10"
)
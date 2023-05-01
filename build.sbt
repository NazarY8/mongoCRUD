name := "GRCRUD"

version := "0.1"

scalaVersion := "2.13.10"

//avoid a potential import http4sBlaze error
val http4sVersion = "0.23.18"
val http4sBlaze = "0.23.13"

libraryDependencies ++= Seq(
  //read from yaml
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  //reactive mongo
  "org.reactivemongo" %% "reactivemongo" % "1.0.10",
  // htt4s routes + blaze server
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sBlaze,
  "org.http4s" %% "http4s-blaze-client" % http4sBlaze,
  //DI framework
  "com.softwaremill.macwire" %% "macros" % "2.5.0" % "provided",
  //json
  "io.circe" %% "circe-generic" % "0.14.3",
  "org.http4s" %% "http4s-circe" % "0.21.7",
)



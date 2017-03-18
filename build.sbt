name := "akkaintroduction"

version := "1.0"

scalaVersion := "2.11.7"

val akkaHttpV = "2.3.16"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaHttpV,
  "com.typesafe.akka" %% "akka-testkit" % akkaHttpV,
  "org.scalatest" %% "scalatest" % "2.2.5"
)
    

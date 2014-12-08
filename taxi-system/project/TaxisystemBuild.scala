import sbt._
import sbt.Keys._

object TaxisystemBuild extends Build {

  lazy val taxiSystem = Project(
    id = "taxi-system",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "taxi-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.7",
  libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.10" % "2.3.7"
    )
  )
}

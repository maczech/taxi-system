import sbt._
import sbt.Keys._

object GpssystemBuild extends Build {

  lazy val gpssystem = Project(
    id = "gps-system",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "gps-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      //resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      //libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.3.7"
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
      libraryDependencies +=
      		"com.typesafe.akka" %% "akka-actor" % "2.3.7"
    )
  )
}

import sbt._
import sbt.Keys._

object SchedulersystemBuild extends Build {

  lazy val schedulerSystem = Project(
    id = "scheduler-system",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "scheduler-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"
    )
  )
}

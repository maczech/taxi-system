import sbt._
import sbt.Keys._

object ManagementcenterBuild extends Build {

  lazy val managementcenter = Project(
    id = "management-center",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "management-center",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.7",
      libraryDependencies += "com.mcz" %% "gps-system" % "0.1-SNAPSHOT"
    )
  )
}

import sbt._
import sbt.Keys._

object TaxiApp extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "taxi",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",

      libraryDependencies +=
        "com.typesafe.akka" %% "akka-actor" % "2.3.7",
        libraryDependencies += "com.mcz" %% "management-center" % "0.1-SNAPSHOT",
        libraryDependencies += "com.mcz" %% "taxi-system" % "0.1-SNAPSHOT",
        libraryDependencies += "com.mcz" %% "gps-system" % "0.1-SNAPSHOT")).aggregate(taxiSystem, managementcenter, gpssystem)

  lazy val taxiSystem = Project(
    id = "taxi-system",
    base = file("taxi-system"),
    settings = Project.defaultSettings ++ Seq(
      name := "taxi-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",

      libraryDependencies +=
        "com.typesafe.akka" %% "akka-actor" % "2.3.7",
      libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.10" % "2.3.7",
      libraryDependencies += "com.mcz" %% "management-center" % "0.1-SNAPSHOT",
      libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.7" % "test",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test",
      libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1"  % "test",
      libraryDependencies +="org.mockito" % "mockito-core" % "1.9.5" % "test")).dependsOn(managementcenter)

  lazy val gpssystem = Project(
    id = "gps-system",
    base = file("gps-system"),
    settings = Project.defaultSettings ++ Seq(
      name := "gps-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",

      libraryDependencies +=
        "com.typesafe.akka" %% "akka-actor" % "2.3.7",
      libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.7",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2"))

  lazy val managementcenter = Project(
    id = "management-center",
    base = file("management-center"),
    settings = Project.defaultSettings ++ Seq(
      name := "management-center",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.4",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.7",
      libraryDependencies += "org.log4s" %% "log4s" % "1.1.3",
      libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7",
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2",
      libraryDependencies += "com.mcz" %% "gps-system" % "0.1-SNAPSHOT",
      libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.7" % "test",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test",
      libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1"  % "test",
      libraryDependencies +="org.mockito" % "mockito-core" % "1.9.5" % "test")).dependsOn(gpssystem)

}
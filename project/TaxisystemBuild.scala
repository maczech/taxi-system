import sbt._
import sbt.Keys._


object TaxisystemBuild extends Build {

  lazy val buildSettings = Seq(
    organization        := "com.mcz",
    version             := "0.1-SNAPSHOT",
    scalaVersion        := "2.10.4",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.7"
  )

  //TODO: Use default settings
  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "taxi",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.7"
    )
    
  ).dependsOn(taxiSystem)
  

  
  lazy val taxiSystem = Project(
    id = "taxi-system",
    base = file("taxi-system"),
    settings = Project.defaultSettings ++ Seq(
      name := "taxi-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.7"
    )
  ).dependsOn(gpssystem,schedulersystem,managementcenter)
  
    lazy val gpssystem = Project(
    id = "gps-system",
    base = file("gps-system"),
    settings = Project.defaultSettings ++ Seq(
      name := "gps-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 
libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.7"
    )
  )
  
  lazy val schedulersystem = Project(
    id = "scheduler-system",
    base = file("scheduler-system"),
    settings = Project.defaultSettings ++ Seq(
      name := "scheduler-system",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"
    )
  )
  lazy val managementcenter = Project(
    id = "management-center",
    base = file("management-center"),
    settings = Project.defaultSettings ++ Seq(
      name := "management-center",
      organization := "com.mcz",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"
    )
  )
  
}

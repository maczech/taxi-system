package com.mcz

import com.mcz.gps.GPSActor
import com.mcz.management.ManagementCenterActor

import akka.actor.ActorSystem
import akka.actor.Props


object Taxisystem extends App {
  val system = ActorSystem("Taxisystem")

  val gpsActor = system.actorOf(Props[GPSActor], "GpsActor")
  val managementCenterActor = system.actorOf(Props[ManagementCenterActor], "ManagementCenterActor")

  system.shutdown()
}

package com.mcz

import akka.actor.ActorSystem
import akka.actor.Props
import com.mcz.gps.GPSActor



object Gpssystem extends App {
  val system = ActorSystem("Gpssystem")

  val actor = system.actorOf(Props[GPSActor],"GpsActor")

}

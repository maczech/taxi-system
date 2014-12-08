package com.mcz

import scala.concurrent.duration.DurationInt

import com.mcz.gps.message.LocationRequest
import com.mcz.taxi.TaxiActor

import akka.actor.ActorSystem
import akka.actor.Props

object Taxisystem extends App {
  val system = ActorSystem("Taxisystem")

  val taxiActor = system.actorOf(Props[TaxiActor],"taxiActor")

  import system.dispatcher
  system.scheduler.schedule(0 milliseconds,1 second, taxiActor,LocationRequest)
  

 // system.shutdown()
}

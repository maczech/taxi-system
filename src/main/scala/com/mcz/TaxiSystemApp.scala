package com.mcz

import scala.concurrent.duration.DurationInt
import com.mcz.gps.message.LocationRequest
import com.mcz.taxi.TaxiActor
import akka.actor.ActorSystem
import akka.actor.Props
import com.typesafe.config.ConfigFactory

object Taxisystem extends App {
  
  val config = ConfigFactory.load()
  
  val system = ActorSystem("Taxisystem")

  val taxiActor = system.actorOf(TaxiActor.create,"TaxiActor")
  import system.dispatcher
  system.scheduler.schedule(0 milliseconds,config.getInt("taxi-system.position-request-frequency") second, taxiActor,LocationRequest)

}

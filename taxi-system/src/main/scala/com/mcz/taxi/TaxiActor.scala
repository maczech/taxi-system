package com.mcz.taxi

import com.mcz.gps.GPSActor
import com.mcz.gps.message.Location
import akka.event.Logging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import com.mcz.gps.message.LocationRequest
import com.mcz.management.ManagementCenterActor

class TaxiActor extends Actor {
  
  val log = Logging(context.system, this)
  
  var gpsActor:AnyRef=_
  var managementCenterActor:AnyRef=_
  
  override def preStart() {
	gpsActor = context.actorSelection("GpsActor").resolveOne
	managementCenterActor = context.actorSelection("ManagementCenterActor").resolveOne
  }
  
  def receive = {
    case LocationRequest => gpsActor ! LocationRequest
    case location:Location => managementCenterActor ! location
    case _ => log.warning("Taxi Actor received unknown message")
  }

}
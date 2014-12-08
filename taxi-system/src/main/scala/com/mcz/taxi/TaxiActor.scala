package com.mcz.taxi

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import com.mcz.gps.GPSActor
import com.mcz.gps.message.Coordinate
import com.mcz.gps.message.Coordinate
import com.mcz.gps.message.LocationRequest
import com.mcz.management.ManagementCenterActor
import com.mcz.management.message.Location
import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import akka.event.Logging

object TaxiActor {
  val config = ConfigFactory.load()
  val tubeStationLocator: TubeStationLocatorAdapter = new TubeStationLocatorAdapter
  def create(): Props = Props(new TaxiActor(config.getInt("taxi-system.taxi-id"),Props[GPSActor],ManagementCenterActor.create,tubeStationLocator))
}

class TaxiActor(val taxiId: Int,gpsActorProps:Props, managementActorProps:Props, val tubeStationLocator: TubeStationLocatorAdapter) extends Actor {

  val log = Logging(context.system, this)

  var gpsActor: ActorRef = _
  var managementCenterActor: ActorRef = _
  

  val config = ConfigFactory.load()

  override def preStart() {
    gpsActor = context.actorOf(gpsActorProps, config.getString("taxi-system.actor.gps.name"))
    managementCenterActor = context.actorOf(managementActorProps, config.getString("taxi-system.actor.management-center.name"))
  }

  def receive = {
    case LocationRequest => gpsActor ! LocationRequest
    case coordinate: Coordinate => sendLocation(coordinate)
    case _ => log.warning("Taxi Actor received unknown message")
  }

  def sendLocation(coordinate: Coordinate) {
    import context.dispatcher
    val tubeLocationCall = Future {
      log.debug("Calling tube location call with coordinate:"+coordinate)
      tubeStationLocator.closeToTubeStation(coordinate);
    }
    tubeLocationCall.onComplete {
      case Success(result) => if (result) managementCenterActor ! new Location(taxiId, coordinate.latitude, coordinate.longitude, coordinate.timestamp)
      case Failure(failure) => log.error("Exception from tube station locator " + failure)

    }
  }
}
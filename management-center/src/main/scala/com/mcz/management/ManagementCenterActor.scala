package com.mcz.management

import akka.actor.Actor
import akka.event.Logging
import com.mcz.gps.message.Coordinate
import com.mcz.management.message.Location
import scala.concurrent.Future

class ManagementCenterActor extends Actor {

  val log = Logging(context.system, this)
  val managementCenter: ManagementCenterAdaptor = new ManagementCenterAdaptor
  def receive = {
	  
    case location: Location => handleLocation(location)
    case _ => log.warning("Management Center Actor received unknown message")
  }

  def handleLocation(location:Location) {
    import context.dispatcher
    Future {
      managementCenter.logPosition(location)
    }.onFailure{
      case e: Exception => log.error("Exception during location reporting "+e )
    }
  }
  
}
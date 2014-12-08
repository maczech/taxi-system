package com.mcz.gps

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import com.mcz.gps.message.LocationRequest
import com.mcz.gps.message.Coordinate
import java.util.Date
import akka.event.Logging
import com.mcz.gps.message.Coordinate

class GPSActor extends Actor {

  val log = Logging(context.system, this)
  
 def receive = {
    case LocationRequest => sender() ! Coordinate(1,1, new Date)
    case _  => log.warning("GPS Actor received unknown message")
  }
  
}
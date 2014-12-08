package com.mcz.management

import akka.actor.Actor
import akka.event.Logging
import com.mcz.gps.message.Location

class ManagementCenterActor extends Actor {

  val log = Logging(context.system, this)
  
 def receive = {
    case location:Location => log.info("Location in management system")
    case _  => log.warning("GPS Actor received unknown message")
  }

}
package com.mcz

import akka.actor.ActorSystem
import akka.actor.Props
import com.mcz.management.ManagementCenterActor


object Managementcenter extends App {
  val system = ActorSystem("Managementcenter")
  
  val actor = system.actorOf(ManagementCenterActor.create,"ManagementCenterActor")

}

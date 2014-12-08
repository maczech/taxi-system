package com.mcz.gps

import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import akka.actor.Props
import akka.testkit.TestActors
import akka.testkit.DefaultTimeout
import akka.actor.ActorSystem
import scala.util.Random
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import akka.testkit.{ TestActors, DefaultTimeout, ImplicitSender, TestKit }
import scala.concurrent.duration._
import com.mcz.gps.message.Coordinate
import com.mcz.gps.message.LocationRequest
import akka.testkit.TestProbe
import akka.testkit.TestActorRef
import akka.testkit.EventFilter
import com.typesafe.config.ConfigFactory

class GPSActorTest extends TestKit(ActorSystem("GPSActorTest",ConfigFactory.parseString("""
		  akka.loggers = ["akka.testkit.TestEventListener"]
		  """)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  val gpsActor = system.actorOf(Props(classOf[GPSActor]))

  override def afterAll {
    shutdown()
  }
  "A GPS Actor" should {
    "Respond with coordinates message" in {
      within(500 millis) {

        val gpsActor = system.actorOf(Props(classOf[GPSActor]))
        gpsActor ! LocationRequest

        expectMsgPF() {
          case Coordinate(1, 1, _) => true
        }
      }
    }
  }

  "A GPS Actor" should {
    "Log warning when wrong message type" in {
      within(500 millis) {
		
        EventFilter.warning(message = "GPS Actor received unknown message", occurrences = 1) intercept {
          gpsActor ! "message"
        }
      }
    }
  }

}
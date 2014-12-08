package com.mcz.taxi

import akka.testkit.TestKit
import akka.testkit.DefaultTimeout
import akka.testkit.ImplicitSender
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import akka.actor.Props
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import akka.testkit.EventFilter
import akka.actor.Actor
import akka.actor.ActorRef
import com.mcz.gps.message.Coordinate
import java.util.Date
import com.mcz.management.message.Location
import com.mcz.gps.message.Coordinate
import akka.testkit.TestActorRef
import akka.testkit.TestProbe
import akka.event.Logging
import com.mcz.gps.message.LocationRequest
import com.mcz.gps.message.LocationRequest

class TaxiActorTest extends TestKit(ActorSystem("TaxiActorTest", ConfigFactory.parseString("""
		  akka.loggers = ["akka.testkit.TestEventListener"]
		  """)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  
  val actor = system.actorOf(Props(new TaxiActor(1, Props(new FakeGpsActor),Props(new FakeManagementCenterActor),new TubeStationLocatorAdapter)))
  
  override def afterAll {
    shutdown()
  }

  "An Taxi Actor" should {
    "log warning when wrong message type" in {
      EventFilter.warning(message = "Taxi Actor received unknown message", occurrences = 1) intercept {
        actor ! "message"
      }
    }
  }

  "An Taxi Actor" should {
    "send location whem close to the sation" in {
        EventFilter.info(message = "Location sent ok!", occurrences = 1) intercept {
           actor ! Coordinate(1, 1, new Date)
        }
    }
  }
  
 "An Taxi Actor" should {
    "send call gps when location requested" in {
        EventFilter.info(message = "Location request sent ok!", occurrences = 1) intercept {
           actor ! LocationRequest
        }
    }
  }

}

class FakeGpsActor extends Actor {
  val log = Logging(context.system, this)
  def receive = { case LocationRequest => log.info("Location request sent ok!")}
}

class FakeManagementCenterActor extends Actor {
    val log = Logging(context.system, this)
  def receive = { case Location(1, 1, 1, _) => log.info("Location sent ok!")}
}
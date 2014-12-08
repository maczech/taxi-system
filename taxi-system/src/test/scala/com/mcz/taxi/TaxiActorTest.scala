package com.mcz.taxi

import java.util.Date
import scala.concurrent.duration._
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Matchers
import org.scalatest.WordSpecLike
import org.scalatest.mock.MockitoSugar
import com.mcz.gps.GPSActor
import com.mcz.gps.message.Coordinate
import com.mcz.gps.message.Coordinate
import com.mcz.gps.message.LocationRequest
import com.mcz.gps.message.LocationRequest
import com.mcz.management.message.Location
import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.event.Logging
import akka.testkit.DefaultTimeout
import akka.testkit.EventFilter
import akka.testkit.ImplicitSender
import akka.testkit.TestKit
import org.mockito.Mockito._
import org.mockito.Matchers._
import com.mcz.gps.message.Coordinate
import com.mcz.management.ManagementCenterActor

class TaxiActorTest extends TestKit(ActorSystem("TaxiActorTest", ConfigFactory.parseString("""
		  akka.loggers = ["akka.testkit.TestEventListener"]
		  """)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {

  val mockTubeStationLocator = mock[TubeStationLocatorAdapter]

  when(mockTubeStationLocator closeToTubeStation (any[Coordinate])) thenReturn true
  val actor = system.actorOf(Props(new TaxiActor(1, Props(new FakeGpsActor), Props(new FakeManagementCenterActor), mockTubeStationLocator)))

  override def afterAll {
    shutdown()
  }

  "A Taxi Actor" should {
    "log warning when wrong message type" in {
      EventFilter.warning(message = "Taxi Actor received unknown message", occurrences = 1) intercept {
        actor ! "message"
      }
    }
  }

  "A Taxi Actor" should {
    "send location when close to the sation" in {
      EventFilter.info(message = "Location sent ok!", occurrences = 1) intercept {
        actor ! Coordinate(1, 1, new Date)
      }
    }
  }

  "A Taxi Actor" should {
    "send call gps when location requested" in {
      EventFilter.info(message = "Location request sent ok!", occurrences = 1) intercept {
        actor ! LocationRequest
      }
    }
  }


  "A Taxi Actor" should {
    "log error when exception from Tube station locator" in {

      when(mockTubeStationLocator closeToTubeStation (any[Coordinate])) thenThrow (new RuntimeException)

      EventFilter.error(message = "Exception from tube station locator java.lang.RuntimeException", occurrences = 1) intercept {
        actor ! Coordinate(1, 1, new Date)
      }
    }
  }

}

class FakeGpsActor extends Actor {
  val log = Logging(context.system, this)
  def receive = { case LocationRequest => log.info("Location request sent ok!") }
}

class FakeManagementCenterActor extends Actor {
  val log = Logging(context.system, this)
  def receive = { case Location(1, 1, 1, _) => log.info("Location sent ok!") }
}
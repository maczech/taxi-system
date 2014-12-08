package com.mcz.management

import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import org.scalatest.mock.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import akka.testkit.DefaultTimeout
import org.scalatest.Matchers
import org.scalatest.WordSpecLike
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import org.mockito.Mockito._
import org.mockito.Matchers._
import akka.actor.Props
import akka.testkit.EventFilter
import com.mcz.management.message.Location
import java.util.Date

class ManagementCenterActorTest extends TestKit(ActorSystem("ManagementCenterActorTest", ConfigFactory.parseString("""
		  akka.loggers = ["akka.testkit.TestEventListener"]
		  """)))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  
  val mockManagmentCenterAdaptor = mock[ManagementCenterAdaptor]
  val actor = system.actorOf(Props(new ManagementCenterActor(mockManagmentCenterAdaptor)))
  
  override def afterAll {
    shutdown()
  }
  
  "A Management Center Actor" should {
    "log warning when wrong message type" in {
      EventFilter.warning(message = "Management Center Actor received unknown message", occurrences = 1) intercept {
        actor ! "message"
      }
    }
  }
  
  "A Management Center Actor" should {
    "log error when exception from Management Center" in {
      
      when(mockManagmentCenterAdaptor.logPosition(any[Location])) thenThrow new RuntimeException
      
      EventFilter.error(message = "Exception during location reporting java.lang.RuntimeException", occurrences = 1) intercept {
         actor ! new Location(1,1,1,new Date)
      }
    }
  }
  
    "A Management Center Actor" should {
    "send location to Management Center when Location Event" in {
        reset(mockManagmentCenterAdaptor)
        actor ! new Location(1,1,1,new Date)
        
        verify(mockManagmentCenterAdaptor,times(1)) logPosition any[Location]
    }
  }

}
package com.shashank.akka.testing

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestProbe}
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

/**
  * Created by shashank on 20/03/17.
  */
class AsynchronousTest extends TestKit(ActorSystem("MySpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Testing actors" must {

    "echo actor" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      expectMsg("hello world")
    }

    "buffer actor adds values to buffer" in {
      val bufferActor = system.actorOf(Props[ActorBasedBuffer], "bufferactor")
      bufferActor ! (10)
      expectNoMsg()
      bufferActor ! (20)
      expectNoMsg()

      bufferActor ! ("sum")
      expectMsg(30)

      println(bufferActor.path.name)
      bufferActor.path.name shouldEqual "bufferactor"
    }

    "killing actor" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      val probe = TestProbe()
      probe watch echo
      echo ! PoisonPill
      probe.expectTerminated(echo)

    }

  }
}
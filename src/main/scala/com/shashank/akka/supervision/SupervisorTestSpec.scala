package com.shashank.akka.supervision

import akka.actor._
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestProbe}
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

/**
 * Created by shashank on 20/03/17.
 */
class SupervisorTestSpec extends TestKit(ActorSystem("MySpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  implicit val timeout = Timeout(2 seconds)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  val supervisor = system.actorOf(Props[Supervisor], "supervisor")

  "Testing supervision" must {

    "normal condition" in {
      supervisor ! Props[Child]
      val child = expectMsgType[ActorRef]

      child ! 42 // set state to 42
      child ! "get"
      expectMsg(42)


      child ! new ArithmeticException // crash it, should resume
      child ! "get"
      expectMsg(42)

      child ! new NullPointerException // crash it, should restart
      child ! "get"
      expectMsg(0)

      watch(child) // have testActor watch “child”
      child ! new IllegalArgumentException // break it
      expectMsgPF() { case Terminated(`child`) => () }


      supervisor ! Props[Child] // create new child
      val child2 = expectMsgType[ActorRef]
      watch(child2)
      child2 ! "get" // verify it is alive
      expectMsg(0)

      child2 ! new Exception("CRASH") // escalate failure
      expectMsgPF() {
        case t @ Terminated(`child2`) if t.existenceConfirmed => ()
      }
    }
  }
}

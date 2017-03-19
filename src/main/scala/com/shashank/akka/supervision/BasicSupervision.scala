package com.shashank.akka.supervision

import java.io.FileNotFoundException

import akka.actor._
import akka.pattern.ask
import akka.testkit.{TestActor, TestKitBase}
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by shashank on 18/03/17.
 */

class Child extends Actor {
  var state = 0
  def receive = {
    case ex: Exception => throw ex
    case x: Int => state = x
    case "status" => println(state)
  }
}

class Supervisor extends Actor {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._


  @scala.throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("ReStarting supervisor")
    super.preRestart(reason, message)
  }


  @scala.throws[Exception](classOf[Exception])
  override def postRestart(reason: Throwable): Unit = {
    println("Restarted supervisor")
    super.postRestart(reason)
  }

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute, loggingEnabled = false) {
      case _: ArithmeticException =>
        println("Resuming the actor back. Restoring its state")
        Resume
      case _: NullPointerException =>
        println("Restarting the actor back. Clearing its state")
        Restart
      case _: IllegalArgumentException =>
        println("Actor Killed. Will not restart/resume")
        Stop
      case _: Exception =>
        println("Severe Error. Escalating")
        Escalate
    }

  def receive = {
    case p: Props => sender() ! context.actorOf(p)
  }
}

object BasicSupervision{

  def main(args: Array[String]) {
    val system:ActorSystem = ActorSystem("supervisionexample")
    val supervisor = system.actorOf(Props[Supervisor], "supervisor")
    implicit val timeout = Timeout(2 seconds)

    SupervisorStrategy.stoppingStrategy

    val childActor = Await.result((supervisor ? Props[Child]), timeout.duration).asInstanceOf[ActorRef]

    /*childActor ! 42 // set state to 42
    childActor ! "status"*/

    /*childActor ! 42 // set state to 42
    childActor ! new ArithmeticException // It Resumes
    childActor ! "status"*/


    /*childActor ! 42 // set state to 42
    childActor ! new IllegalArgumentException // It stops
    childActor ! "status"*/

    childActor ! 42 // set state to 42
    childActor ! new FileNotFoundException // It escalates
    childActor ! "status"


    Thread.sleep(1000)
    system.shutdown()

  }

}

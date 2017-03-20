package com.shashank.akka.basic

import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
/**
 * Created by shashank on 19/03/17.
 */
object ShuttingDown {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val infoLoggerActor = system.actorOf(Props[LoggerActor], name = "infologger")
    val errorLoggerActor = system.actorOf(Props[LoggerActor], name = "errorlogger")
    implicit val timeout = Timeout(2 seconds)

    system.shutdown()

    infoLoggerActor ? (PoisonPill)
  }

}

package com.shashank.akka.basic

import akka.actor.{ActorSystem, Props}

/**
  * Created by shashank on 18/03/17.
  */
object MultipleActorThreadHostage {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val infoLoggerActor = system.actorOf(Props[LoggerActor], name = "infologger")
    val errorLoggerActor = system.actorOf(Props[LoggerActor], name = "errorlogger")

    println("In main function "+ Thread.currentThread())
    infoLoggerActor ! "Message 1"
    errorLoggerActor ! "FAILED"

    Thread.sleep(1000)

    infoLoggerActor ! "Message 2"
    errorLoggerActor ! "FAILED Again"
    system.shutdown()
  }
}

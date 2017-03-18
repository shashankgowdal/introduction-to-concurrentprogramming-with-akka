package com.shashank.akka.basic

import akka.actor.{Actor, ActorSystem, Props}



object MultipleActor {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val infoLoggerActor = system.actorOf(Props[LoggerActor], name = "infologger")
    val errorLoggerActor = system.actorOf(Props[LoggerActor], name = "errorlogger")

    println("In main function "+ Thread.currentThread())
    infoLoggerActor ! "Message 1"
    errorLoggerActor ! "FAILED"

    infoLoggerActor ! "Message 2"
    errorLoggerActor ! "FAILED Again"
    system.shutdown()
  }
}

package com.shashank.akka.basic

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by shashank on 18/03/17.
  */

object ActorConstructor {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val infoLoggerActor = system.actorOf(Props(new LoggerActorWithConstructor("INFO")), name = "infologger")
    val errorLoggerActor = system.actorOf(Props(new LoggerActorWithConstructor("ERROR")), name = "errorlogger")

    println("In main function "+ Thread.currentThread())
    infoLoggerActor ! "Message 1"
    errorLoggerActor ! "FAILED"

    infoLoggerActor ! "Message 2"
    errorLoggerActor ! "FAILED Again"
    system.shutdown()
  }

}


class LoggerActorWithConstructor(loggerType:String) extends Actor {
  def receive = {
    case x:String => println(s"[$loggerType] $x")
    case _ => println(s"[$loggerType] huh?")
  }
}



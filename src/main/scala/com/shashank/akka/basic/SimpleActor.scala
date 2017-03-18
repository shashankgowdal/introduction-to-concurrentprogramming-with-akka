package com.shashank.akka.basic

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
 * Created by shashank on 18/03/17.
 */

object SimpleActor{
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("LogSystem")
    // default Actor constructor
    val loggerActor = system.actorOf(Props[LoggerActor], name = "loggeractor")
    println("In main function "+ Thread.currentThread())
    loggerActor ! "Message 1"
    loggerActor ! "Message 2"
    system.shutdown()
  }
}

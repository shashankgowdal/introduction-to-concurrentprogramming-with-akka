package com.shashank.akka.basic

import java.io.File

import akka.actor._
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory

/**
  * Created by shashank on 20/03/17.
  */

class LoggerActorWorker extends Actor {
  def receive = {
    case x:Any => println(s"Message from ${context.self.path}")
  }
}

object Router {
  def main(args: Array[String]) {
    val config = ConfigFactory.parseFile(new File("src/main/resources/router/application.conf"))
    
    val system = ActorSystem("RouterExample", config)
    val router1: ActorRef = system.actorOf(FromConfig.props(Props[LoggerActorWorker]), "router1")

    val onesArray = Array.fill(20)(1)
    onesArray.toArray.par.foreach(value => {
      router1 ! (value)
      Thread.sleep(100)
    })

  }
}

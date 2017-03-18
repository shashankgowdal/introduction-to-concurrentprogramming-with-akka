package com.shashank.akka.basic


import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props


/**
  * Created by shashank on 18/03/17.
  */

class LoggerActor extends Actor {
  def receive = {
    case x:String => println(s"$x from thread  ${Thread.currentThread()}")
    case _ => println(s"huh? ${Thread.currentThread()}")
  }
}

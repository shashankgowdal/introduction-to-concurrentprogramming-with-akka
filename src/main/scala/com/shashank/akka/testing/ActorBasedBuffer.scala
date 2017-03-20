package com.shashank.akka.testing

import akka.actor.Actor
import scala.collection.mutable.ListBuffer

/**
  * Created by shashank on 20/03/17.
  */
class ActorBasedBuffer extends Actor {
  val buffer = ListBuffer[Int]()
  def receive = {
    case x:Int => buffer += x
    case "sum" =>
      sender() ! (buffer.toArray.sum)
    case "avg" =>
      sender() ! (buffer.sum / buffer.size)

  }
}



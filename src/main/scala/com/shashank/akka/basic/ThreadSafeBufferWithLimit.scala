package com.shashank.akka.basic

import akka.actor._

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.HashSet

/**
  * Created by shashank on 18/03/17.
  */

case class Add(value:Int)
case class Remove(value:Int)

class ActorBasedBufferWithLimit extends Actor {
  import context._
  val buffer = HashSet[Int]()
  val bufferSizeLimit = 3

  def bufferActive:Receive = {
    case Add(x:Int) =>
      buffer += x
      if(buffer.size == bufferSizeLimit)
        become(bufferFull)
    case Remove(x:Int) =>
      buffer.-=(x)
    case "get" =>
      sender() ! ("Status active. Contents are "+buffer.toArray.mkString(","))
  }

  def bufferFull:Receive = {
    case Add(x:Int) =>
      println(s"Buffer full. Dropping value $x")
    case Remove(x:Int) =>
      if(buffer.contains(x)){
        buffer.-=(x)
        become(bufferActive)
      }
    case "get" =>
      sender() ! ("Status full. Contents are "+buffer.toArray.mkString(","))
  }


  def receive = bufferActive
}

object ThreadSafeBufferWithLimit {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    val bufferActor = system.actorOf(Props[ActorBasedBufferWithLimit], name = "buffer")
    implicit val timeout = Timeout(25 seconds)

    bufferActor ! Add(10)
    bufferActor ! Add(20)

    (bufferActor ? ("get")).onSuccess{
      case contents => println(contents)
    }

    bufferActor ! Add(30)
    bufferActor ! Add(40)

    (bufferActor ? ("get")).onSuccess{
      case contents => println(contents)
    }

    bufferActor ! Remove(30)
    bufferActor ! Add(40)

    (bufferActor ? ("get")).onSuccess{
      case contents => println(contents)
    }

    Thread.sleep(2000)
    system.shutdown()
  }

}
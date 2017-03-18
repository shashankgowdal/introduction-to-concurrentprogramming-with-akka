package com.shashank.akka.basic

import akka.actor._
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by shashank on 18/03/17.
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

object ThreadSafeBuffer {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    val bufferActor = system.actorOf(Props[ActorBasedBuffer], name = "buffer")
    implicit val timeout = Timeout(25 seconds)

    val onesArray = Array.fill(1000)(1)
    onesArray.zipWithIndex.toArray.par.foreach(valueIndex => {
      val (value1, index1) = valueIndex
      bufferActor ! (value1)
      println(s"Added ${index1} th value from thread ${Thread.currentThread()}")
    })

    val sumFuture = bufferActor ? ("sum")
    val avgFuture = bufferActor ? ("avg")
    sumFuture.onComplete(sumTry => {
      val sumValue = sumTry.getOrElse(0)
      avgFuture.onComplete(avgTry => {
        val avgValue = avgTry.getOrElse(0)
        println(s"Sum value is ${sumValue} and Avg value is ${avgValue}")
        system.shutdown()
      })
    })
  }

}

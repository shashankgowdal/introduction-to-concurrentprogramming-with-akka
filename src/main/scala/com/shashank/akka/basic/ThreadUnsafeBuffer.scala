package com.shashank.akka.basic

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout

import scala.collection.mutable.ListBuffer

/**
 * Created by shashank on 18/03/17.
 */
class TraditionalBuffer {
  val buffer = ListBuffer[Int]()

  def update(value: Int): Unit = {
    buffer += value
  }

  def sum(): Double = {
    buffer.sum.toDouble
  }

  def avg(): Double = {
    buffer.sum.toDouble / buffer.size
  }
}

object ThreadUnsafeBuffer {

  def main(args: Array[String]) {
    val buffer = new TraditionalBuffer

    val onesArray = Array.fill(1000)(1)
    onesArray.zipWithIndex.toArray.par.foreach(valueIndex => {
      val (value1, index1) = valueIndex
      buffer.update(value1)
      println(s"Added ${index1} th value from thread ${Thread.currentThread()}")
    })

    val sumValue = buffer.sum()
    val avgValue = buffer.avg()
    println(s"Sum value is ${sumValue} and Avg value is ${avgValue}")
  }

}
package com.shashank.akka.examples.executionhandler

import akka.actor._
import com.shashank.akka.examples.executionhandler.Models.WaitTask
import com.shashank.akka.examples.shoppingcart.Models._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Success

/**
  * Created by shashank on 18/03/17.
  */
object Executor{

  def main(args: Array[String]) {
    val actorSystem = ActorSystem("shoppingapp")
    val executerHandler = actorSystem.actorOf(Props[ExecutorHandler])
    implicit val timeout = Timeout(25 seconds)

    executerHandler ! (WaitTask(10))

    executerHandler ! (WaitTask(1))

    Thread.sleep(10000)

  }

}

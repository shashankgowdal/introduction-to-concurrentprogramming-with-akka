package com.shashank.akka.examples.executionhandler

import akka.actor.{Actor, ActorRef, Props}
import com.shashank.akka.examples.executionhandler.Models.{DoneExecution, Execute, WaitTask}

import scala.collection.mutable.ListBuffer

/**
  * Created by shashank on 18/03/17.
  */
class ExecutorHandler extends Actor{

  override def receive: Receive = {
    case waitTask:WaitTask =>
      println(s"Recived task $waitTask")
      val executorActor = context.actorOf(Props[ExecutorActor])
      executorActor ! Execute(self, waitTask)

    case done:DoneExecution =>
      println(s"Finished execution of task ${done.task}")
  }

}

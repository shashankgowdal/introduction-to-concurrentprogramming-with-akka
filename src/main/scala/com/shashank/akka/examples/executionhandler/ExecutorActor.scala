package com.shashank.akka.examples.executionhandler

import akka.actor._
import com.shashank.akka.examples.executionhandler.Models.{Execute, WaitTask,DoneExecution}

/**
  * Created by shashank on 18/03/17.
  */
class ExecutorActor extends Actor{


  override def receive: Receive = {
    case execute:Execute =>

      val waitTask = execute.task
      println(s"Sleeping ${waitTask.time}s")
      Thread.sleep(waitTask.time.toInt * 1000)
      execute.senderActor ! DoneExecution(execute.task)

  }

}

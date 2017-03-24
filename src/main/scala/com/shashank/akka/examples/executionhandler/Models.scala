package com.shashank.akka.examples.executionhandler

import akka.actor.ActorRef

/**
  * Created by shashank on 18/03/17.
  */
object Models {

  case class WaitTask(time:Int)

  case class Execute(senderActor:ActorRef, task:WaitTask)

  case class DoneExecution(task:WaitTask)

}

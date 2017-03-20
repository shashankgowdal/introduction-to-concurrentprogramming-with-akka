package com.shashank.akka.remote

import akka.actor._
import com.typesafe.config.ConfigFactory
import java.io.File

/**
  * Created by shashank on 20/03/17.
  */
class RemoteActor extends Actor{

  override def receive: Receive = {
    case msg: String => {
      println("remote received " + msg + " from " + sender)
      sender ! "hi"
    }
    case _ => println("Received unknown msg ")
  }

}


object RemoteActor {
  def main(args: Array[String]) {
    val config = ConfigFactory.parseFile(new File("src/main/resources/remote/remote_application.conf"))
    val system = ActorSystem("RemoteSystem" , config)
    val remote = system.actorOf(Props[RemoteActor], name="remote")
    println(remote)

  }
}

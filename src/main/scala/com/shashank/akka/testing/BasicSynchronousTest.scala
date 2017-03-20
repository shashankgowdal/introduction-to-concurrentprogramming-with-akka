package com.shashank.akka.testing


import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import scala.concurrent.duration._
import scala.util.Success


/**
  * Created by shashank on 20/03/17.
  */
object BasicSynchronousTest extends WordSpecLike with Matchers{

  def main(args: Array[String]) {

    implicit val system = ActorSystem("Testing")
    implicit val timeout = Timeout(25 seconds)

    val actorRef = TestActorRef[ActorBasedBuffer]
    val actor = actorRef.underlyingActor

    actorRef ! (10)
    actorRef ! (20)

    val sumFuture = actorRef ? "sum"
    val Success(sumValue: Int) = sumFuture.value.get
    sumValue should be(30)

    val avgFuture = actorRef ? "avg"
    val Success(avgValue: Int) = avgFuture.value.get
    avgValue should be(15)

    Thread.sleep(1000)
    system.shutdown()

  }

}

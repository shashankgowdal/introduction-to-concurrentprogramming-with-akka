package com.shashank.akka.basic

import akka.actor.{TypedActor, _}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by shashank on 19/03/17.
  */

trait Squarer {
  def squareDontCare(i: Int): Unit //fire-forget

  def square(i: Int): Future[Int] //non-blocking send-request-reply

  def squareNowPlease(i: Int): Option[Int] //blocking send-request-reply

  def squareNow(i: Int): Int //blocking send-request-reply

  @throws(classOf[Exception]) //declare it or you will get an UndeclaredThrowableException
  def squareTry(i: Int): Int //blocking send-request-reply with possible exception
}

class SquarerImpl(val name: String) extends Squarer {

  def this() = this("default")
  def squareDontCare(i: Int): Unit = i * i //Nobody cares :(

  def square(i: Int): Future[Int] = Future.successful(i * i)

  def squareNowPlease(i: Int): Option[Int] = Some(i * i)

  def squareNow(i: Int): Int = i * i

  def squareTry(i: Int): Int = throw new Exception("Catch me!")
}


object TypedActorExample {

  def main(args: Array[String]) {
    val system = ActorSystem("HelloSystem")
    val mySquarer: Squarer =
      TypedActor(system).typedActorOf(TypedProps[SquarerImpl]())
    mySquarer.square(2).onSuccess{
      case squared => println(s"Squared value ${squared} from square method")
    }

    println(s"Squared value ${mySquarer.squareNow(2)} from squareNow method")

    TypedActor(system).poisonPill(mySquarer)
    system.shutdown()
  }

}

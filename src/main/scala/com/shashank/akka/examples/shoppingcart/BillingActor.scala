package com.shashank.akka.examples.shoppingcart

import akka.actor.Actor.Receive
import akka.actor._
import com.shashank.akka.examples.shoppingcart.Models._

/**
  * Created by shashank on 18/03/17.
  */
class BillingActor extends Actor{


  override def receive: Receive = {
    case order:Order =>
      val billAmount = order.item.map{
        case item:Item =>
          item.quantity * item.rate
      }.sum
      println(s"Here your bill ${Bill(billAmount)}")
  }

}

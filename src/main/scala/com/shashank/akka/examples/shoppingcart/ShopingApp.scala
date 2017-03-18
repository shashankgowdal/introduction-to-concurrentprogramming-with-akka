package com.shashank.akka.examples.shoppingcart

import akka.actor._
import com.shashank.akka.examples.shoppingcart.Models._

/**
  * Created by shashank on 18/03/17.
  */
object ShopingApp{

  def main(args: Array[String]) {
    val actorSystem = ActorSystem("shoppingapp")
    val billingActor = actorSystem.actorOf(Props[BillingActor])
    val cartActor = actorSystem.actorOf(Props(new CartActor(billingActor)))

    cartActor ! Item("Apple", 3, 20.0)
    cartActor ! Item("Orange", 3, 15.0)
    cartActor ! Item("Butter", 1, 70.0)
    cartActor ! Done

    actorSystem.shutdown()

  }

}

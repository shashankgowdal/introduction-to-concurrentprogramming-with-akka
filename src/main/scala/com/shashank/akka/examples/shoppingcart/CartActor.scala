package com.shashank.akka.examples.shoppingcart

import akka.actor.{Actor, ActorRef}
import com.shashank.akka.examples.shoppingcart.Models.{Done, Item, Order}

import scala.collection.mutable.ListBuffer

/**
  * Created by shashank on 18/03/17.
  */
class CartActor(billingActor:ActorRef) extends Actor{

  val items = ListBuffer[Item]()

  override def receive: Receive = {
    case item:Item =>
      items += (item)

    case Done =>
      billingActor ! Order(items.toList)
      items.clear()
  }

}

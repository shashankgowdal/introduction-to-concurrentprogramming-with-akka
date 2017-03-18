package com.shashank.akka.examples.shoppingcart

/**
  * Created by shashank on 18/03/17.
  */
object Models {

  case class Item(name:String, quantity:Int, rate:Double)

  case object Done
  case class Order(item:List[Item])

  case class Bill(amount:Double)

}

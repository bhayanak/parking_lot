package com.speed.rest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Advanced extends App {
  // Lazy evaluation
  lazy val aLazyValue = 2

  lazy val aLazyValueWithSideEffects = {
    println("I am Lazy")
    43
  } //It wont run now as its defined lazy

  //It will evaluated only after its called once, for example
  val eagerVal = aLazyValueWithSideEffects + 1 //If you comment this line nothing will be printed as above val is lazy
  //Lazy val is good for infinite collections

  //Pseudo collections : Option, Try
  def methodWhichCanReturnNull(): String = "Some String"

  val anOption = Option(methodWhichCanReturnNull())
  val stringProcessing = anOption match {
    case Some(value) => s"Got valid value $value"
    case None => "I got none"
  }

  def aMethodCanThrow: String = throw new RuntimeException

  val aTry = Try(aMethodCanThrow)
  val anotherStringProcessing = aTry match {
    case Success(value) => s"Got valid string : $value"
    case Failure(exception) => s"Got exception $exception"
  }

  /*
  Async programming
   */
  val aFuture = Future {
    println("Loding ...")
    Thread.sleep(1000)
    println("Values is calculated.")
    67
  }
  //Future is a "collection" which contains a value when its evaluated
  // Future is composable with map, filterMap and flatMap

  /*
  Implicits basics:

   */
  // 1. Implicit arguments
  def aMethodWithImplicit(implicit arg: Int) = arg + 1

  implicit val myImplicitInt = 46

  println(aMethodWithImplicit) //This is equivalent to aMethodWithImplicit(myImplicitInt)

  // #2 Implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven()) //mew MyRichInteger(23).isEven()
  //Use implicit with care.
}

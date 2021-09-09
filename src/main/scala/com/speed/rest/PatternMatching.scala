package com.speed.rest

object PatternMatching extends App {
  val anInteger = 65
  val order = anInteger match {
    case 1 => "first"
    case 2 => "Second"
    case 3 => "Third"
    case _ => anInteger + "th"
  }

  case class Person(name: String, age: Int)

  val bob = Person("Bob", 23)

  val personGreeting = bob match {
    case Person(n, a) => s"I am $n and $a years old"
    case _ => "something else"
  }
  // pattern matching gets tried in sequence but goes into just one branch

  print(personGreeting)
}

package com.speed.rest

object ObjectOrientation extends App {
  //class instance
  class Animal {
    //define fields
    val age: Int = 0

    // define methods
    def eat() = println("I am Eating.")
  }

  val anAnimal = new Animal

  // Inheritance
  class Dog(val name: String) extends Animal

  val aDog = new Dog("Rocky")
  // Constructor arguments are not fields, Need to put val before constructor arguments to make that happen
  aDog.name

  val aAnimal: Animal = new Dog("Jacky")
  aAnimal.eat()

  //Abstract class
  abstract class WalkingAnimal {
    val hasLegs = true //by default all are public, can restrict using private/protected
  }

  //Interface = Ultimate abstract type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  //Single class inheritance, multi trait "mixing"
  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("I am eating you, Animal")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog //Infix Notation

  //Anonymous class
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am dinosaur, can eat anything !")
  }
  /*
    Here above we tell compiler that do following:
    class Carnivore_Anonymous_358686 extends Carnivore{
      override def eat(animal: Animal): Unit = println("I am dinosaur, can eat anything !")
    }
    val dinosaur = new Carnivore_Anonymous_358686
   */

  // Singleton object
  object MySingleton {
    val myValue = 569

    def myMethod(): Int = 897

    def apply(x: Int): Int = x + 1
  } //The only instance of type MySingleton

  MySingleton.myMethod()
  MySingleton.apply(65)
  MySingleton(65) // This call apply method of class, which is in every class

  object Animal { //Companion object as there is a class already with same name
    //Companions can access each others private field/method(s)
    //singleton Animal and instances of Animal are 2 diff things.
    val canLiveIndefinitely = false
  }

  val animalCanLiveForever = Animal.canLiveIndefinitely //Static fields/methods

  /*
    case classes = lightweight data structure with boiler plate
    -- equals and hash code
    -- Serialization
    -- Pattern matching
   */

  case class Person(name: String, age: Int)

  val bob = Person("Bob", 26)

  // Exception
  try {
    val x: String = null
    x.length
  } catch {
    case e: Exception => "some error message: String is null"
  } finally {
    //Execute this always
  }

  // Generics
  abstract class MyList[T] {
    def head: T

    def tail: MyList[T]
  }

  //Using generic with concrete type
  val aList: List[Int] = List(1, 2, 3) // = List.apply(1,2,3)
  val first = aList.head
  val rest = aList.tail
  val stringList = List("Hello", "Scala")
  val firstStr = stringList.head

  // 1. In scala operate with immutable values.
  // Any modification to object returns a new object
  /*
      a. Works best in multi threaded environment
      b. Helps readability
   */
  val reversedList = aList.reverse //Return a new list
  // 2. Scala as closest to OOP ideal
}

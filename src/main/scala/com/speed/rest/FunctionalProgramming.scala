package com.speed.rest

object FunctionalProgramming extends App {
  class Person(name: String) {
    def apply(age: Int) = println(s"I am $name and $age years")
  }

  val bob = new Person("Bob")
  println(bob(23))


  /*
  Scala runs in JVM
  Functional programimg:
    - compose function
    - pass function as arguments
    - return function as result/return

    Conclusion: FunctionX = Function1, Function2,...Function22
   */
  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }
  simpleIncrementer(23) //Will return 24 as it calls apply method
  // All scala functions are instances of these FunctionX types

  // Function with 2 args and string return
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("Hello", " Scala")

  // This can be written as below for syntactic sugar
  val doubler1: Function1[Int, Int] = (x: Int) => 2 * x
  // This can be written as below for further syntactic sugar
  val doubler3: (Int) => Int = (x: Int) => 2 * x

  // Higher order functions: take functions as arguments and returns functions or both
  val mappedList = List(1, 2, 3).map(x => x + 1) //HOF
  val flatMappedList = List(1, 2, 3).flatMap(x => List(x, 2 * x))
  val filteredList = List(1, 2, 3, 4, 5).filter(_ <= 3) // = equivalent to  (x=> x>=3)


  // create pairs between (1,2,3) (a,b,c) all combinations
  val createPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))

  // As above gets too complicated to read, there is for comprehensions
  val alternatePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"
  // equivalent to above flatmap/map on line 45

  // Collections
  val aList = List(1, 2, 3, 4, 5)
  val firstElement = aList.head
  val rest = aList.tail

  val aPrepandedList = 0 :: aList //List (0,1,2,3,4,5)
  val extededList = 0 +: aList :+ 6 //List (0,1,2,3,4,5,6)


  // Sequence, Vector, Set
  val aSeq: Seq[Int] = Seq(1, 2, 3)

  //Ranges
  val aRange = 1 to 100
  val twoByTwo = aRange.map(_ * 2).toList //List(2,4,6,8...2000)

  //Tuples = groups of values under same value
  val aTuple = ("Hello", "scala", 2012)

  //Maps
  val aPhoneBook: Map[String, Int] = Map(
    ("Ram", 8236087),
    ("Rame", 9739424)
  )
  println(aPhoneBook)
}

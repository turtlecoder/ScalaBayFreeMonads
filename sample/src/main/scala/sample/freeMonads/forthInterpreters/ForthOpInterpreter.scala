/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthInterpreters

import sample.freeMonads.forthOperators._
import sample.freeMonads.forthOperators.ForthOp._
import scala.collection.mutable
import scalaz._
import scalaz.Id._
import scala.util.Random


/**
  * A stateful Natural Transform to convert [[sample.freeMonads.forthOperators.ForthOp]] free monads to a [[scalaz.Id]]
  */
class ForthOpInterpreter extends (ForthOp ~> Id) {
  val stack = new mutable.Stack[Int]()
  override def apply[A](fa: ForthOp[A]): Id[A] = fa match {
    case Push(n, next) =>
      stack.push(n)
      next(\/-(()))
    case Pop(next) => try{
      next(\/-(stack.pop()))
    } catch {
      case (e:java.util.NoSuchElementException) => next(-\/(e))
    }
  }
}

/**
  * The use of this "stateful" object illustrates the
  * dangers of mutable objects. The use of the interpreter
  * repeatedly will result in different results
  */
object ForthOpInterpreter extends ForthOpInterpreter


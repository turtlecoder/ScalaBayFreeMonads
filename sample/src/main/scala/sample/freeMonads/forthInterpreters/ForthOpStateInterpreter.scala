/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthInterpreters


import sample.freeMonads.forthOperators._
import sample.freeMonads.forthOperators.ForthOp._
import scala.NoSuchElementException
import scalaz._
import Scalaz._

/**
  * A natural transform to transform [[sample.freeMonads.forthOperators.ForthOp]] free monads to [[scalaz.State]] monad.
  * This is a pure interpreter.
  */
object ForthOpStateInterpreter extends (ForthOp ~> NTState) {
  override def apply[A](fa: ForthOp[A]): NTState[A] = fa match {
    case Push(n, next) => State {
      state => ((n :: state), next(\/-(())))
    }
    case Pop(next) => State {
      case top :: rest => (rest, next(\/-(top)))
      case err @ Nil   => (err, next(-\/(new NoSuchElementException())))
    }
  }
}

/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.kvInterpreters

import sample.freeMonads.kvOperators.KVOp
import sample.freeMonads.kvOperators.KVOp._

import scalaz._
import Scalaz._
import scalaz.syntax._
import scalaz.syntax.std._

/**
  * A natural transform to map [[sample.freeMonads.kvOperators.KVOp]] free monads to [[scalaz.State]] monad. This is a
  * pure interpreter, i.e. it does not have any side-effects
  */
object KVStateInterpreter extends (KVOp ~> NTState)  {
  override def apply[A](fa: KVOp[A]): NTState[A] = fa match {
    case Create(key, value, next) => State {
      state => (state + (key -> value), next(\/-(())))
    }

    case Read(key, next) => State {
      state => (state, next(state.get(key).toRightDisjunction(new Throwable("Key Undefined!"))))
    }

    case Update(key, value, next) => State {
      state => (state + (key -> value), next(().right[Throwable]))
    }

    case Delete(key, next) => State {
      state => (state - key, next(().right[Throwable]))
    }
  }
}

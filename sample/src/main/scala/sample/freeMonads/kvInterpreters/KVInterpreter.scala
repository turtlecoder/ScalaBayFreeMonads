/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.kvInterpreters

import sample.freeMonads.kvOperators.KVOp
import sample.freeMonads.kvOperators.KVOp._

import scalaz._
import Scalaz._

/**
  * A natural transform to [[sample.freeMonads.kvOperators]] free monads to a [[scalaz.Id]] monad. This is an interpreter
  * with side effects
  */
object KVInterpreter extends (KVOp ~> Id) {

  var kvStore = scala.collection.mutable.Map[String, Int]()

  override def apply[A](fa: KVOp[A]): Id[A] = fa match {
    case Create(key, value, next) =>
      if (!kvStore.isDefinedAt(key)) {
        kvStore += (key -> value);
        next(().right[Throwable])
      } else next((new Throwable("Key already defined!")).left[Unit])
    case Read(key, next) =>
      if(kvStore.isDefinedAt(key))
        next(kvStore(key).right[Throwable])
      else
        next((new Throwable("Key Undefined!")).left[Int])
    case Update(key, value, next) =>
      if(kvStore.isDefinedAt(key)) {
        kvStore += (key -> value)
        next(().right[Throwable])
      } else {
        next((new Throwable("Key Undefined!")).left[Unit])
      }
    case Delete(key, next) =>
      if(!kvStore.isDefinedAt(key)) {
        kvStore.remove(key)
        next(().right[Throwable])
      } else {
        next((new Throwable("Key undefined!").left[Unit]))
      }
  }
}

/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import sample.freeMonads.forthInterpreters.NTState

import scalaz._
import Scalaz._

/**
  * Intermediate [[scalaz.NaturalTransformation]] to convert [[sample.freeMonads.forthInterpreters.NTState]] to [[sample.freeMonads.ForthOpKVState]]
  */
object ForthStateToForthKVState extends (forthInterpreters.NTState ~> ForthOpKVState) {
  override def apply[A](outerMonad: forthInterpreters.NTState[A]): ForthOpKVState[A] = outerMonad.liftM[KVState]
}

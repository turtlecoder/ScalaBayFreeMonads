/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import sample.freeMonads.kvInterpreters.NTState

import scalaz._
import Scalaz._

object KVStateToForthKVState extends (kvInterpreters.NTState ~> ForthOpKVState) {
  override def apply[A](innerMonad: kvInterpreters.NTState[A]): ForthOpKVState[A] = {
    StateT[forthInterpreters.NTState, Map[String, Int], A] {
      state => innerMonad.run(state).point[forthInterpreters.NTState]
    }
  }
}

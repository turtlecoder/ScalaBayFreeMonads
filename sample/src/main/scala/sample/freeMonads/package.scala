/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample

import sample.freeMonads.forthInterpreters
import sample.freeMonads.forthInterpreters.NTState

import scalaz._
import Scalaz._
import scalaz.syntax._
import scalaz.syntax.std._
import scala.language.higherKinds

package object freeMonads {
  /**
    * Key Value State Monad will be are inner monad
    *
    * @tparam OM
    *            The outer monad
    * @tparam A
    *           The type parameter
    *
    */
  type KVState[OM[_], A] = StateT[OM, Map[String, Int], A]

  /**
    * The Forth operator state monad where the state is a
    * List of Integers
    *
    * @tparam A
    */
  type ForthOpKVState[A] = KVState[NTState, A]

  implicit object ForthOpKVStateMonadImpl extends ForthOpKVStateMonad

}

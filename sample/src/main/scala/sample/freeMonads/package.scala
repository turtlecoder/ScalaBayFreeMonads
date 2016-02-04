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

  /**
    * An implicit Monad for [[ForthOpKVState]]
    */
  implicit val forthOpKVStateMonad:Monad[ForthOpKVState] = new Monad[ForthOpKVState]{

    override def point[A](a: => A): ForthOpKVState[A] = {
      StateT.apply[forthInterpreters.NTState, Map[String, Int], A] {
        (s1:Map[String, Int]) => State.apply {
          (s2:List[Int]) => (s2, (s1, a))
        }
      }
    }
    override def bind[A, B](fa: ForthOpKVState[A])(f: (A) => ForthOpKVState[B]): ForthOpKVState[B] = {
      fa flatMap f
    }
  }


}

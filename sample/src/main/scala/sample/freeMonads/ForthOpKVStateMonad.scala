/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import scalaz.{State, StateT, Monad}

/**
  * An implicit Monad for [[ForthOpKVState]]
  */

trait ForthOpKVStateMonad extends Monad[ForthOpKVState]{
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

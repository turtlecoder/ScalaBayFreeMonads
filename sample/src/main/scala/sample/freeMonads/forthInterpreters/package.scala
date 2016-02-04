/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import scalaz._
import Scalaz._

package object forthInterpreters {
  /**
    * Type alias of a [[scalaz.State]] monad that modifies a [[scala.collection.immutable.List]] of [[scala.Int]]
    * @tparam A
    */
  type NTState[A] = State[List[Int], A]
}

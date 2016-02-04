/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import scalaz._
import Scalaz._

package object kvInterpreters {
  /**
    * Type alias for State to get around type argument
    * projection
    *
    * @tparam A
    */
  type NTState[A] = State[Map[String, Int], A]
}

/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthOperators

import scalaz._
import Scalaz._

sealed trait Op[A]

object Op {

  final case class Push[A](n: Int, next: A) extends Op[A]

  final case class Pop(next: Int => A)

  final case class Add[A](next: A) extends Op[A]

  final case class Mul[A](next: A) extends Op[A]

  final case class Div[A](next: Throwable \/ Double => A)

  final case class Dup[A](next: A) extends Op[A]

}

trait OpsInstances {

  type OpsFree[A] = Free[Op, A]

  implicit val opsMonad = scalaz.Free.freeMonad[Op]
}

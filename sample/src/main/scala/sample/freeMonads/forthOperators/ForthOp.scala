/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthOperators

import scalaz._
import Scalaz._
import scala.language.higherKinds

sealed trait ForthOp[A]

object ForthOp {

  final case class Push[A](n: Int, next: Throwable \/ Unit => A) extends ForthOp[A]

  final case class Pop[A](next: Throwable \/ Int => A) extends ForthOp[A]

}




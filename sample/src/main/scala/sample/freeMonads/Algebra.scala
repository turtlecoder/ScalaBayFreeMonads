/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import scalaz._
import sample.freeMonads.forthOperators.{ForthOpInstances, ForthOp}
import sample.freeMonads.kvInterpreters.KVInterpreter
import sample.freeMonads.kvOperators.{KVOp, KVOpInstances}

object Algebra {
  /**
    * A [[scalaz.Coproduct]] of [[sample.freeMonads.forthOperators.ForthOp]] and [[sample.freeMonads.kvOperators.KVOp]]
    * @tparam A
    *           The type parameter
    */
  type C0[A] = Coproduct[ForthOp, KVOp, A]
  // ... add a new algebra here as and replace it in AppAlgebra
  // type C1[A] = Coproduct[C0, SomeAlgebra, A]

  /**
    * This is the Coproduct of all the algebras defined in the program.
    * Here we use C0.
    * @tparam A
    *           The type argument for the algebra
    */
  type AppAlgebra[A] = C0[A]

  /**
    * Helper object that mixes in smart constructors of the
    * all algebras in the final program
    */
  object All extends ForthOpInstances with KVOpInstances
}

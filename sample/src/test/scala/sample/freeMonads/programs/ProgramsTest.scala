/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.programs

import org.specs2.mutable._
import sample.freeMonads.ForthOpKVStateMonad
import sample.freeMonads.InjectUtils._
import sample.freeMonads.forthInterpreters.ForthOpStateInterpreter
import sample.freeMonads.kvInterpreters.KVStateInterpreter
import sample.freeMonads.{ForthStateToForthKVState, programs, _}

import scalaz._
import Scalaz._

class ProgramsTest extends Specification {

  "KVOp and ForthOp Interpreters" should {
    "Create and Update pure values" in {
      // create the interpreter, This interpreter will interpret a language which is a
      // coproduct of two languages (ForthOp[_] and KVOp[_])

      val interpreter = (ForthOpStateInterpreter andThen ForthStateToForthKVState) or (KVStateInterpreter andThen KVStateToForthKVState)

      val prg1 = programs.createPrg[Algebra.C0]("create", 2, 2).foldMap(interpreter).run(Map()).run(List())

      prg1 must_== (List(),(Map("create"->4), \/-(())))
    }
  }
}

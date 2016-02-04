/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.kvInterpreters

import org.specs2.mutable._
import sample.freeMonads.InjectUtils.-~>
import sample.freeMonads.kvOperators.KVOp
import scalaz._
import Scalaz._
import scalaz.syntax._
import scala.language.higherKinds

class KVOpInterpreterTest extends Specification {

  "Key-Value Store Interpreter" should {
    "Create a key" in {
      import sample.freeMonads.Algebra.All._
      def prg[F[_]](key:String, value:Int)(implicit I:KVOp -~> F) = create[F](key, value)
      val res = prg("key1", 1).foldMap(KVInterpreter)
      res must_== \/-(())
      KVInterpreter.kvStore.get("key1") must_== Some(1)
    }

    "Read a key" in {
      import sample.freeMonads.Algebra.All._
      (for {
        _ <- create("key1", 1)
        _ <- create("key2", 2)
        x <- read("key1")
      } yield x).foldMap(KVInterpreter) must_== (\/-(1))
    }

  }

}

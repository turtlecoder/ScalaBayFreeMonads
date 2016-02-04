/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthInterpreters

import org.specs2.mutable._
import sample.freeMonads.InjectUtils.-~>
import sample.freeMonads.forthOperators.ForthOp
import scalaz._
import Scalaz._
import scalaz.syntax._
import scala.language.higherKinds

class ForthOpInterpreterTest extends Specification {
  "ForthInterpreter Object" should {

    "Push and Pop a simple stack" in {
      import sample.freeMonads.Algebra.All._

      def program[F[_]](implicit I: ForthOp -~> F ) = for {
        a <- push[F](1)
        b <- pop[F]
      } yield b

      val x = program.foldMap(new ForthOpInterpreter)

      x must_== \/-(1)
    }
    "Add two  numbers" in {
      import sample.freeMonads.Algebra.All._
      def program[F[_]](a:Int, b:Int)(implicit I: ForthOp -~> F) = for {
        _ <- push[F](a)
        _ <- push[F](b)
        _ <- add[F]
        res <- pop[F]
      } yield res

      val x = program(2,2).foldMap(new ForthOpInterpreter)
      x must_== \/-(4)
    }

    "Multiply 3 numbers" in {
      import sample.freeMonads.Algebra.All._
      def program[F[_]](a:Int, b:Int, c:Int)(implicit I: ForthOp -~> F) = for {
        _ <- push[F](a)
        _ <- push[F](b)
        _ <- push[F](c)
        _ <- mul[F]
        _ <- mul[F]
        res <- pop[F]
      } yield res

      val x = program(2,4,2).foldMap(new ForthOpInterpreter)
      x must_== \/-(16)
    }

    "Divide 2 natural numbers" in {
      import sample.freeMonads.Algebra.All._


      def program[F[_]](a:Int, b:Int)(implicit I:ForthOp -~> F) = for {
        _ <- push[F](a)
        _ <- push[F](b)
        _ <- div[F]
        quot <- pop[F]
        rem <- pop[F]
      } yield ((quot |@| rem) { ((_, _))})

      val res = program(8,2).foldMap(ForthOpInterpreter);

      res must_==(\/-((4,0)))
    }
  }
}

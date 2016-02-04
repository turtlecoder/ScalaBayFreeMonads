/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.forthOperators

import scala.annotation.tailrec
import scalaz._
import Scalaz._
import scalaz.syntax._

import scala.language.higherKinds


/**
  * Smart constructors for [[sample.freeMonads.forthOperators.ForthOp]] free monads
  */
trait ForthOpInstances {

  import sample.freeMonads.InjectUtils._

  type ForthOpFree[A] = Free[ForthOp, A]

  implicit val forthOpMonad: Monad[ForthOpFree] = Free.freeMonad[ForthOp]

  import ForthOp._
  import Inject._

  def push[F[_]](n: Int)(implicit I: ForthOp -~> F) = inject[F, ForthOp, Throwable \/ Unit](Push(n, Free.point(_)))

  def pop[F[_]](implicit I: ForthOp -~> F) = inject[F, ForthOp, Throwable \/ Int](Pop(Free.point(_)))

  def add[F[_]](implicit I: ForthOp -~> F) : Free[F, Throwable \/ Unit] = for {
    a <- pop[F]
    b <- pop[F]
    c =  (a |@| b) { _ + _ }
    res <- c match {
      case \/-(r) => push[F](r)
      case err @ -\/(_) => Free.point[F, Throwable \/ Unit](err)
    }
  } yield res

  def mul[F[_]](implicit I: ForthOp -~> F): Free[F, Throwable \/ Unit] = for {
    a <- pop[F]
    b <- pop[F]
    c = (a |@| b) { (_:Int) * (_:Int) }
    res <- c match {
      case \/-(x) => push[F](x)
      case -\/(th) => Free.point[F, Throwable \/ Unit](-\/(th))
    }
  } yield res


  def div[F[_]](implicit I: ForthOp -~> F) : Free[F, Throwable \/ Unit ] = {
    def divRec[F[_]](implicit I: ForthOp -~> F): Free[F, Throwable \/ Unit] = for {
      quotient <- pop[F]
      divisor <- pop[F] map { dv =>
        dv flatMap (x => if (x==0) -\/(new IllegalArgumentException("Divide by zero")) else \/-(x))
      }
      remainder <- pop[F]
      qdr <- Free.point[F, Throwable \/ (Int, Int, Int)]( (quotient |@| divisor |@| remainder) { (_, _, _) } )
      res <- qdr match {
        case \/-((q,d,r)) =>
          if (r < d) {
            for {
              _ <- push[F](r);
              res <- push[F](q)
            }
            yield res
          } else for {
            _ <- push[F](r - d);
            _ <- push[F](d);
            _ <- push[F](q+1);
            res <- divRec[F]
          } yield res
        case -\/(th) => Free.point[F, Throwable \/ Unit](th.left[Unit])
      }
    } yield res

    for {
        res <- push[F](0)
        res <- divRec[F]
    } yield res
  }
}



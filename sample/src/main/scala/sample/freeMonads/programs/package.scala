/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import Algebra.All._
import sample.freeMonads.forthOperators.ForthOp
import java.util.Calendar

import sample.freeMonads.kvOperators.KVOp
import scalaz._
import Scalaz._
import scalaz.syntax._
import InjectUtils._

import scala.language.higherKinds


package object programs {
  /**
    * A simple program to <br>
    *   - push two numbers <br>
    *   - multiply them <br>
    *   - pop the result <br>
    *   - create an entry in the data store <br>
    */
  def createPrg[F[_]](key:String, a:Int, b:Int)(implicit FO: ForthOp -~> F, KVO: KVOp -~> F) = for {
    _ <- push[F](a)
    _ <- push[F](b)
    _ <- mul[F]
    res <- pop[F]
    cr <- res.fold(th => th.left[Unit] |> (Free.point[F, Throwable \/ Unit]),
                    x => create[F](key, x) )

  } yield cr

  /**
    * Another simple program to increment an entry in the data store<br>
    *   - read a value from the data store<br>
    *   - push the value on the stack<br>
    *   - push the argument on the stack<br>
    *   - add the the two values<br>
    *   - pop the result<br>
    */
  def incrementPrg[F[_]](key:String, n: Int )(implicit FO:ForthOp -~> F, KVO: KVOp -~> F) = for {
    valueA <- read[F](key)
    valueB = n.right[Throwable]
    // wrap this in a free monad, to get the types inline
    abv <- Free.point[F, Throwable \/ (Int, Int)]( (valueA |@| valueB ) { (_, _) } )
    cv <- abv.fold( _.left[Int] |> (Free.point[F, Throwable \/ Int]) , {
      case (a, b) => for {
        _ <- push[F](a)
        _ <- push[F](b)
        _ <- add[F]
        r <- pop[F]
      } yield r
    })
    rr <- cv.fold(_.left[Unit] |> (Free.point[F, Throwable \/ Unit]), update[F](key, _))
  } yield rr

}

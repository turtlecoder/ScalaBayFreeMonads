/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.kvOperators

import scalaz._
import Scalaz._
import scalaz.syntax._
import scala.language.higherKinds

/**
  * Smart constructors for the tokens in the algebra
  */
trait KVOpInstances {

  import sample.freeMonads.InjectUtils._

  type KVOpFree[A] = Free[KVOp, A]

  /**
    * Implicit monad so tha a free monad can be bound to
    * a subsequent computations
    */
  implicit val KVOpMonad:Monad[KVOpFree] = Free.freeMonad[KVOp]

  import KVOp._
  import Inject._

  def create[F[_]](key:String, value:Int)(implicit I:KVOp -~> F) = inject[F, KVOp, Throwable \/ Unit](Create(key, value, Free.point(_)))

  def read[F[_]](key:String)(implicit I:KVOp -~> F) = inject[F, KVOp, Throwable \/ Int](Read(key, Free.point(_)))

  def update[F[_]](key:String, value:Int)(implicit I:KVOp -~> F) = inject[F, KVOp, Throwable \/ Unit](Update(key, value, Free.point(_)))

  def delete[F[_]](key:String)(implicit I:KVOp -~> F) = inject[F, KVOp, Throwable \/ Unit](Delete(key, Free.point(_)))

}

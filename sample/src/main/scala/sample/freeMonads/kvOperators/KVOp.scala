/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads.kvOperators

import scalaz._
import Scalaz._
import scalaz.syntax._

/**
  * F type for the free monad. Used to simulate an instruction for the
  * CRUD language
  * @tparam A
  */
sealed trait KVOp[A]

object KVOp {

  /**
    * Creates a key, value in the some data store
    */
  case class Create[A](key:String, value:Int, next: (Throwable \/ Unit => A)) extends KVOp[A]

  /**
    * Reads a value from the key store, based on the key,
    * returns a lift disjuction if key is not found.
    */
  case class Read[A](key:String, next:(Throwable \/ Int => A) ) extends KVOp[A]

  /**
    * Update key-value pair with a new pair. Return an error if key is not found.
    */
  case class Update[A](key:String, value:Int, next : (Throwable \/ Unit => A)) extends KVOp[A]

  /**
    * Remote a key-value pair from the data store
    */
  case class Delete[A](key:String, next: (Throwable \/ Unit => A)) extends KVOp[A]
}

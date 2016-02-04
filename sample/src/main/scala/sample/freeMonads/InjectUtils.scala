/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

import scala.language.higherKinds
import scalaz._

object InjectUtils {

  /**
    * A type definition for Coproducts.
    * This helps with type projections.
    * @tparam F
    *           The type of the object on the right
    * @tparam G
    *           The tye of the object on the left
    */
  type Copro[F[_], G[_]] = { type f[x] = Coproduct[F, G, x] } // just for better readability

  /**
    * Type alias for Inject. Injects a language F into a language G
    */
  type -~>[F[_], G[_]] = Inject[F, G]

  /**
    * Extension class to pimp
    */
  implicit class NaturalTransformationOrOps[F[_], H[_]]( private val nt: F ~> H) /*extends AnyVal*/ {
    /**
      * Given F ~> H and G ~> H we derive Copro[F, G]#f ~> H
      */
    def or[G[_]](f: G ~> H): Copro[F, G]#f ~> H =
      new (Copro[F, G]#f ~> H) {
        def apply[A](c: Coproduct[F, G, A]): H[A] = c.run match {
          case -\/(fa) => nt(fa)
          case \/-(ga) => f(ga)
        }
      }
  }


}

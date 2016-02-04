/*
 * Copyright 2010-2015 HealthExpense, Inc
 * All Rights Reserved
 */

package sample.freeMonads

object HelloWorld {
  def main(args:Array[String]):Unit = {
    println("Hello, World!")
  }

  def div(x:Int, y:Int): (Int, Int) = {
    def divHelper(remainder:Int, c:Int):(Int, Int) = {
      if(remainder<y)
        (c, remainder)
      else
        divHelper(remainder-y, c+1)
    }
    divHelper(x, 0)
  }
}

/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.swing

object Orientation extends Enumeration {
  import java.awt.Adjustable._
  val Horizontal   : Orientation.Value = Value(HORIZONTAL)
  val Vertical     : Orientation.Value = Value(VERTICAL)
  val NoOrientation: Orientation.Value = Value(NO_ORIENTATION)
}

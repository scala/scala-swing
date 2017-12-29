/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.swing

/**
 * Horizontal and vertical alignments. We sacrifice a bit of type-safety
 * for simplicity here.
 *
 * @see javax.swing.SwingConstants
 */
object Alignment extends Enumeration {
  import javax.swing.SwingConstants._
  val Left    : Alignment.Value = Value(LEFT)
  val Right   : Alignment.Value = Value(RIGHT)
  val Center  : Alignment.Value = Value(CENTER)
  val Top     : Alignment.Value = Value(TOP)
  val Bottom  : Alignment.Value = Value(BOTTOM)
  //1.6: val Baseline = Value(BASELINE)

  val Leading : Alignment.Value = Value(LEADING)
  val Trailing: Alignment.Value = Value(TRAILING)
}


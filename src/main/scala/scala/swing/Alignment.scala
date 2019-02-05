/*
 * scala-swing (https://www.scala-lang.org)
 *
 * Copyright EPFL, Lightbend, Inc., contributors
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

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


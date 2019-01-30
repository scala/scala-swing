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

object Orientation extends Enumeration {
  import java.awt.Adjustable._
  val Horizontal   : Orientation.Value = Value(HORIZONTAL)
  val Vertical     : Orientation.Value = Value(VERTICAL)
  val NoOrientation: Orientation.Value = Value(NO_ORIENTATION)
}

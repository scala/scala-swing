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
package event

/** An event that indicates some editing operation that can be still
 *  in progress.
 *
 *  Example: dragging a slider creates a number of `AdjustmentEvents`
 *  with `adjusting == '''true'''` until the user finally releases the
 *  mouse button.
 */
trait AdjustingEvent extends ComponentEvent {
  def adjusting: Boolean
  def committed: Boolean = !adjusting
}

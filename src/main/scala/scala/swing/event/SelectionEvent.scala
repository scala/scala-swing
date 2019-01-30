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

/**
 * An event that indicates a change in a selection such as in a list view or a table.
 */
trait SelectionEvent

/**
 * An event that indicates a selection of a range of indices.
 */
trait ListSelectionEvent extends SelectionEvent {
  def range: Range
}

case class SelectionChanged(override val source: Component) extends ComponentEvent with SelectionEvent

object ListSelectionChanged {
  def unapply[A](e: ListSelectionChanged[A]): Option[(ListView[A], Range, Boolean)] =
    Some((e.source, e.range, e.live))
}

class ListSelectionChanged[A](override val source: ListView[A], val range: Range, val live: Boolean)
  extends SelectionChanged(source) with ListEvent[A]

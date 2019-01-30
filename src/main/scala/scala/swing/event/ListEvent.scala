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

trait ListEvent[A] extends ComponentEvent {
  override val source: ListView[A]
}

//case class ElementSelected[A](override val source: ListView[A], range: Range, live: Boolean)
//           extends ListEvent[A] with AdjustingEvent with ListSelectionEvent

abstract class ListChange[A](override val source: ListView[A]) extends ListEvent[A]

object ListChanged {
  def unapply[A](e: ListChanged[A]) = Some(e.source)
  def apply[A](source: ListView[A]) = new ListChanged(source)
}

class ListChanged[A](override val source: ListView[A]) extends ListChange(source)

object ListElementsAdded {
  def unapply[A](e: ListElementsAdded[A]) = Some((e.source, e.range))
  def apply[A](source: ListView[A], range: Range) = new ListElementsAdded(source, range)
}

class ListElementsAdded[A](override val source: ListView[A], val range: Range)
           extends ListChange(source)

object ListElementsRemoved {
  def unapply[A](e: ListElementsRemoved[A]) = Some((e.source, e.range))
  def apply[A](source: ListView[A], range: Range) = new ListElementsRemoved(source, range)
}
class ListElementsRemoved[A](override val source: ListView[A], val range: Range)
           extends ListChange(source)

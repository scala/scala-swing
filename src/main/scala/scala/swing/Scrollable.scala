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

object Scrollable {
  trait Wrapper extends Scrollable {
    protected def scrollablePeer: javax.swing.Scrollable
    def preferredViewportSize: Dimension = scrollablePeer.getPreferredScrollableViewportSize

    def tracksViewportHeight: Boolean = scrollablePeer.getScrollableTracksViewportHeight
    def tracksViewportWidth: Boolean = scrollablePeer.getScrollableTracksViewportWidth

    def blockIncrement(visibleRect: Rectangle, orientation: Orientation.Value, direction: Int): Int =
      scrollablePeer.getScrollableBlockIncrement(visibleRect, orientation.id, direction)

    def unitIncrement(visibleRect: Rectangle, orientation: Orientation.Value, direction: Int): Int =
      scrollablePeer.getScrollableUnitIncrement(visibleRect, orientation.id, direction)
  }
}

/**
 * A component that is specially suitable for being placed inside a
 * <code>ScrollPane</code>.
 *
 * @see javax.swing.Scrollable
 */
trait Scrollable extends Component {
  def preferredViewportSize: Dimension

  def tracksViewportHeight: Boolean
  def tracksViewportWidth: Boolean

  def blockIncrement(visibleRect: Rectangle, orientation: Orientation.Value, direction: Int): Int
  def unitIncrement(visibleRect: Rectangle, orientation: Orientation.Value, direction: Int): Int
}

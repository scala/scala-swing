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

import java.awt.BorderLayout

object BorderPanel {
  /**
   * The position of a component in a <code>BorderPanel</code>
   */
  object Position extends Enumeration {
    import BorderLayout._
    val North : Position.Value = Value(NORTH)
    val South : Position.Value = Value(SOUTH)
    val West  : Position.Value = Value(WEST)
    val East  : Position.Value = Value(EAST)
    val Center: Position.Value = Value(CENTER)
  }
  private[swing] def wrapPosition(s: String): Position.Value = {
    import BorderLayout._
    s match {
      case NORTH   => Position.North
      case SOUTH   => Position.South
      case WEST    => Position.West
      case EAST    => Position.East
      case CENTER  => Position.Center
    }
  }
}

/**
 * A container that arranges its children around a central component that
 * takes most of the space. The other children are placed on one of four
 * borders: north, east, south, west.
 *
 * @see javax.swing.BorderLayout
 */
class BorderPanel extends Panel with LayoutContainer {
  import BorderPanel._
  def layoutManager: BorderLayout = peer.getLayout.asInstanceOf[BorderLayout]
  override lazy val peer = new javax.swing.JPanel(new BorderLayout) with SuperMixin

  type Constraints = Position.Value

  protected def constraintsFor(comp: Component): Constraints =
    wrapPosition(layoutManager.getConstraints(comp.peer).asInstanceOf[String])

  protected def areValid(c: Constraints): (Boolean, String) = (true, "")

  protected def add(c: Component, l: Constraints): Unit = {
    // we need to remove previous components with the same constraints as the new one,
    // otherwise the layout manager loses track of the old one
    val old = layoutManager.getLayoutComponent(l.toString)
    if(old != null) peer.remove(old)
    peer.add(c.peer, l.toString)
  }
}

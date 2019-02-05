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

import scala.collection.immutable

/**
 * The root of a component hierarchy. Contains at most one component.
 *
 * @see javax.swing.RootPaneContainer
 */
trait RootPanel extends Container {
  def peer: java.awt.Component with javax.swing.RootPaneContainer

  /**
   * At most one component.
   */
  def contents: immutable.Seq[Component] =
    if (peer.getContentPane.getComponentCount == 0) Nil
    else {
      val c = peer.getContentPane.getComponent(0).asInstanceOf[javax.swing.JComponent]
      List(UIElement.cachedWrapper[Component](c))
    }

  def contents_=(c: Component): Unit = {
    if (peer.getContentPane.getComponentCount > 0) {
      val old = peer.getContentPane.getComponent(0)
      peer.getContentPane.remove(old)
    }
    peer.getContentPane.add(c.peer)
  }
}

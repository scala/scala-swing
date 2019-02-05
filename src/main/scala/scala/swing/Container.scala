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

import scala.swing.event.{ComponentAdded, ComponentRemoved}

object Container {
  /**
   * Utility trait for wrapping containers. Provides an immutable
   * implementation of the contents member.
   */
  trait Wrapper extends Container with Publisher {
    override def peer: javax.swing.JComponent

    protected val _contents = new Content
    def contents: scala.collection.Seq[Component] = _contents

    protected class Content extends BufferWrapper[Component] {
      override def clear(): Unit = peer.removeAll()

      override def remove(n: Int): Component = {
        val c = peer.getComponent(n)
        peer.remove(n)
        UIElement.cachedWrapper[Component](c)
      }

      override def insert(n: Int, c: Component): Unit = peer.add(c.peer, n)

      override def addOne(c: Component): this.type = { peer.add(c.peer) ; this }

      def length: Int = peer.getComponentCount

      def apply(n: Int): Component = UIElement.cachedWrapper[Component](peer.getComponent(n))
    }

    peer.addContainerListener(new java.awt.event.ContainerListener {
      def componentAdded(e: java.awt.event.ContainerEvent): Unit =
        publish(ComponentAdded(Wrapper.this,
          UIElement.cachedWrapper[Component](e.getChild.asInstanceOf[javax.swing.JComponent])))

      def componentRemoved(e: java.awt.event.ContainerEvent): Unit =
        publish(ComponentRemoved(Wrapper.this,
          UIElement.cachedWrapper[Component](e.getChild.asInstanceOf[javax.swing.JComponent])))
    })
  }
}

/**
 * The base traits for UI elements that can contain <code>Component</code>s.
 *
 * @note [Java Swing] This is not the wrapper for java.awt.Container but a trait
 * that extracts a common interface for components, menus, and windows.
 */
trait Container extends UIElement {
  /**
   * The child components of this container.
   */
  def contents: scala.collection.Seq[Component]
}

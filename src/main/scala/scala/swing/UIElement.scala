/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.awt.{ComponentOrientation, Cursor, Toolkit}
import java.util
import java.util.Locale

import scala.ref._
import scala.swing.event._

object UIElement {
  private val ClientKey = "scala.swingWrapper"
  private[this] val wrapperCache = new util.WeakHashMap[java.awt.Component, WeakReference[UIElement]]

  private def cache(e: UIElement) = e.peer match {
    case p: javax.swing.JComponent => p.putClientProperty(ClientKey, e)
    case _ => wrapperCache.put(e.peer, new WeakReference(e))
  }

  /**
   * Looks up the internal component cache for a wrapper of the given
   * Java Swing peer. If this method finds one of the given type `C`,
   * it will return that wrapper. Otherwise it returns `null`. This
   * method never throws an exception.
   *
   * Clients should be extremely careful with type parameter `C` and
   * its interaction with type inference. Better err on the side of caution
   * and explicitly specify `C`.
   */
  private[swing] def cachedWrapper[C>:Null<:UIElement](c: java.awt.Component): C = {
    val w = c match {
      case c: javax.swing.JComponent => c.getClientProperty(ClientKey)
      case _ => wrapperCache.get(c)
    }
    try { w.asInstanceOf[C] } catch { case _: Exception => null }
  }

  /**
   * Returns a wrapper for a given Java Swing peer. If there is a
   * compatible wrapper in use, this method will return it.
   *
   * `wrap` methods in companion objects of subclasses of `UIElement` have
   * the  same behavior, except that they return more specific wrappers.
   */
  def wrap(c: java.awt.Component): UIElement = {
    val w = cachedWrapper[UIElement](c)
    if (w != null) w
    else new UIElement { def peer = c }
  }
}

/**
 * The base trait of all user interface elements. Subclasses belong to one
 * of two groups: top-level elements such as windows and dialogs, or
 * `Component`s.
 *
 * @note [Java Swing] This trait does not have an exact counterpart in
 * Java Swing. The peer is of type java.awt.Component since this is the
 * least common upper bound of possible underlying peers.
 *
 * @note [Implementation] A UIElement automatically adds itself to the
 * component cache on creation.
 *
 * @see java.awt.Component
 */
trait UIElement extends Proxy with LazyPublisher {
  /**
   * The underlying Swing peer.
   */
  def peer: java.awt.Component
  def self = peer

  UIElement.cache(this)

  def foreground: Color = peer.getForeground
  def foreground_=(c: Color): Unit = peer setForeground c
  def background: Color = peer.getBackground
  def background_=(c: Color): Unit = peer setBackground c

  def minimumSize: Dimension = peer.getMinimumSize
  def minimumSize_=(x: Dimension): Unit = peer setMinimumSize x
  def maximumSize: Dimension = peer.getMaximumSize
  def maximumSize_=(x: Dimension): Unit = peer setMaximumSize x
  def preferredSize: Dimension = peer.getPreferredSize
  def preferredSize_=(x: Dimension): Unit = peer setPreferredSize x
  def componentOrientation: ComponentOrientation = peer.getComponentOrientation
  def componentOrientation_=(x: ComponentOrientation): Unit = peer setComponentOrientation x

  def font: Font = peer.getFont
  def font_=(f: Font): Unit = peer setFont f


  def locationOnScreen: Point = peer.getLocationOnScreen
  def location: Point = peer.getLocation
  def bounds: Rectangle = peer.getBounds
  def size: Dimension = peer.getSize

  def locale: Locale = peer.getLocale
  def toolkit: Toolkit = peer.getToolkit

  def cursor: Cursor = peer.getCursor
  def cursor_=(c: Cursor): Unit = peer.setCursor(c)

  def visible: Boolean = peer.isVisible
  def visible_=(b: Boolean): Unit = peer.setVisible(b)
  def showing: Boolean = peer.isShowing
  def displayable: Boolean = peer.isDisplayable

  def validate(): Unit = peer.validate()
  def repaint(): Unit = peer.repaint()
  def repaint(rect: Rectangle): Unit = peer.repaint(rect.x, rect.y, rect.width, rect.height)
  def ignoreRepaint: Boolean = peer.getIgnoreRepaint
  def ignoreRepaint_=(b: Boolean): Unit = peer.setIgnoreRepaint(b)

  protected def onFirstSubscribe(): Unit = {
    peer.addComponentListener(new java.awt.event.ComponentListener {
      def componentHidden(e: java.awt.event.ComponentEvent): Unit =
        publish(UIElementHidden(UIElement.this))

      def componentShown(e: java.awt.event.ComponentEvent): Unit =
        publish(UIElementShown(UIElement.this))

      def componentMoved(e: java.awt.event.ComponentEvent): Unit =
        publish(UIElementMoved(UIElement.this))

      def componentResized(e: java.awt.event.ComponentEvent): Unit =
        publish(UIElementResized(UIElement.this))
    })
  }
  protected def onLastUnsubscribe(): Unit = ()
}

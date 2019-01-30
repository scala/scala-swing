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

import java.awt.event.{WindowEvent, WindowListener}
import java.awt.{Window => AWTWindow}

/**
 * A window with decoration such as a title, border, and action buttons.
 *
 * An AWT window cannot be wrapped dynamically with this class, i.e., you cannot
 * write something like new Window { def peer = myAWTWindow }
 *
 * @see javax.swing.JFrame
 */
abstract class Window extends UIElement with RootPanel with Publisher { outer =>
  def peer: AWTWindow with InterfaceMixin

  protected trait InterfaceMixin extends javax.swing.RootPaneContainer

  protected trait SuperMixin extends AWTWindow {
    override protected def processWindowEvent(e: WindowEvent): Unit = {
      super.processWindowEvent(e)
      if (e.getID == WindowEvent.WINDOW_CLOSING)
        closeOperation()
    }
  }

  /**
   * This method is called when the window is closing, after all other window
   * event listeners have been processed.
   */
  def closeOperation(): Unit = ()

  override def contents_=(c: Component): Unit = {
    super.contents_=(c)
    peer.pack() // pack also validates, which is generally required after an add
  }
  def defaultButton: Option[Button] =
    toOption(peer.getRootPane.getDefaultButton) map UIElement.cachedWrapper[Button]
  def defaultButton_=(b: Button): Unit = {
    peer.getRootPane.setDefaultButton(b.peer)
  }
  def defaultButton_=(b: Option[Button]): Unit = {
    peer.getRootPane.setDefaultButton(b.map(_.peer).orNull)
  }

  def dispose(): Unit = peer.dispose()

  def pack(): this.type = { peer.pack(); this }

  def setLocationRelativeTo(c: UIElement): Unit = peer.setLocationRelativeTo(c.peer)
  def centerOnScreen(): Unit = peer.setLocationRelativeTo(null)
  def location_=(p: Point): Unit = peer.setLocation(p)
  def size_=(size: Dimension): Unit = peer.setSize(size)
  def bounds_=(rect: Rectangle): Unit = peer.setBounds(rect)

  def owner: Window = UIElement.cachedWrapper[Window](peer.getOwner)

  def open (): Unit = peer.setVisible(true)
  def close(): Unit = peer.setVisible(false)

  peer.addWindowListener(new WindowListener {
    def windowActivated   (e: WindowEvent): Unit = publish(event.WindowActivated  (outer))
    def windowClosed      (e: WindowEvent): Unit = publish(event.WindowClosed     (outer))
    def windowClosing     (e: WindowEvent): Unit = publish(event.WindowClosing    (outer))
    def windowDeactivated (e: WindowEvent): Unit = publish(event.WindowDeactivated(outer))
    def windowDeiconified (e: WindowEvent): Unit = publish(event.WindowDeiconified(outer))
    def windowIconified   (e: WindowEvent): Unit = publish(event.WindowIconified  (outer))
    def windowOpened      (e: WindowEvent): Unit = publish(event.WindowOpened     (outer))
  })
}

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

import javax.swing.JPopupMenu
import javax.swing.event.{PopupMenuEvent, PopupMenuListener}

/**
 * A popup menu.
 * 
 * Example usage:
 *
 * {{{
 * val popupMenu = new PopupMenu {
 *   contents += new Menu("menu 1") {
 *     contents += new RadioMenuItem("radio 1.1")
 *     contents += new RadioMenuItem("radio 1.2")
 *   }
 *   contents += new Menu("menu 2") {
 *     contents += new RadioMenuItem("radio 2.1")
 *     contents += new RadioMenuItem("radio 2.2")
 *   }
 * }
 * val button = new Button("Show Popup Menu")
 * reactions += {
 *   case e: ButtonClicked => popupMenu.show(button, 0, button.bounds.height)
 * }
 * listenTo(button)
 * }}}
 * 
 * @author John Sullivan
 * @author Ingo Maier
 * @see javax.swing.JPopupMenu
 */
class PopupMenu extends Component with SequentialContainer.Wrapper with Publisher {
  override lazy val peer: JPopupMenu = new JPopupMenu with SuperMixin 

  peer.addPopupMenuListener(new PopupMenuListener {
  	def popupMenuCanceled(e: PopupMenuEvent): Unit =
  	  publish(event.PopupMenuCanceled(PopupMenu.this))

  	def popupMenuWillBecomeInvisible(e: PopupMenuEvent): Unit =
  	  publish(event.PopupMenuWillBecomeInvisible(PopupMenu.this))

  	def popupMenuWillBecomeVisible(e: PopupMenuEvent): Unit =
  	  publish(event.PopupMenuWillBecomeVisible(PopupMenu.this))
  })

  def show(invoker: Component, x: Int, y: Int): Unit = peer.show(invoker.peer, x, y)

  def margin: Insets = peer.getMargin
  
  def label: String = peer.getLabel
  def label_=(s: String): Unit = peer.setLabel(s)

  /** Lays out the popup menu so that it uses the minimum space
    * needed to display its contents.
    */
  def pack(): Unit = peer.pack()
}


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

import javax.swing.JColorChooser
import scala.swing.event._
import scala.swing.Swing._

/**
 * Wrapper for JColorChooser. Publishes  `ColorChanged` events, when the color selection changes.
 * 
 * @author andy@hicks.net
 * @author Ingo Maier
 * @see javax.swing.JColorChooser
 */
object ColorChooser {
  def showDialog(parent: PeerContainer, title: String, color: scala.swing.Color): scala.Option[Color] = {
    toOption[Color](javax.swing.JColorChooser.showDialog(parent.peer, title, color))
  }
}

class ColorChooser(color0: Color) extends Component  {
  def this() = this(java.awt.Color.white)
  
  override lazy val peer: JColorChooser =  new JColorChooser(color0) with SuperMixin

  peer.getSelectionModel.addChangeListener(new javax.swing.event.ChangeListener {
    def stateChanged(e: javax.swing.event.ChangeEvent): Unit =
      publish(ColorChanged(ColorChooser.this, peer.getColor))
  })

  def color: Color = peer.getColor
  def color_=(c: Color): Unit = peer.setColor(c)

  def dragEnabled: Boolean = peer.getDragEnabled
  def dragEnabled_=(b: Boolean): Unit = peer.setDragEnabled(b)
}
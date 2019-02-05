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

import javax.swing.JCheckBox

/**
 * Two state button that can either be checked or unchecked.
 *
 * @see javax.swing.JCheckBox
 */
class CheckBox(text: String) extends ToggleButton {
  override lazy val peer: JCheckBox = new JCheckBox(text) with SuperMixin
  def this() = this("")

  def borderPaintedFlat: Boolean = peer.isBorderPaintedFlat
  def borderPaintedFlat_=(flat: Boolean): Unit = peer.setBorderPaintedFlat(flat)
}

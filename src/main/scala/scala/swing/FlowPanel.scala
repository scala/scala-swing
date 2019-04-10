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

import java.awt.FlowLayout

import javax.swing.JPanel

object FlowPanel {
  object Alignment extends Enumeration {
    import FlowLayout._
    val Leading : Alignment.Value = Value(LEADING)
    val Trailing: Alignment.Value = Value(TRAILING)
    val Left    : Alignment.Value = Value(LEFT)
    val Right   : Alignment.Value = Value(RIGHT)
    val Center  : Alignment.Value = Value(CENTER)
  }
}

/**
 * A panel that arranges its contents horizontally, one after the other.
 * If they don't fit, this panel will try to insert line breaks.
 *
 * @see java.awt.FlowLayout
 */
class FlowPanel(alignment: FlowPanel.Alignment.Value)(contents0: Component*) extends Panel with SequentialContainer.Wrapper {
  override lazy val peer: JPanel =
    new JPanel(new java.awt.FlowLayout(alignment.id)) with SuperMixin
  def this(contents0: Component*) = this(FlowPanel.Alignment.Center)(contents0: _*)
  def this() = this(FlowPanel.Alignment.Center)()

  contents ++= contents0

  private def layoutManager: FlowLayout = peer.getLayout.asInstanceOf[FlowLayout]

  def vGap    : Int         = layoutManager.getVgap
  def vGap_=(n: Int): Unit  = layoutManager.setVgap(n)

  def hGap    : Int         = layoutManager.getHgap
  def hGap_=(n: Int): Unit  = layoutManager.setHgap(n)

  def alignOnBaseline         : Boolean         = layoutManager.getAlignOnBaseline
  def alignOnBaseline_=(value : Boolean): Unit  = layoutManager.setAlignOnBaseline(value)
}

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

import javax.swing.{JLabel, JSlider}

/**
 * Lets users select a value from a given range. Visually, this is represented
 * as a draggable knob on a horizontal or vertical bar.
 *
 * Fires a ValueChanged event whenever the slider's value changes and
 * when the knob is released.
 *
 * @see javax.swing.JSlider
 */
class Slider extends Component with Orientable.Wrapper with Publisher {
  override lazy val peer: JSlider = new JSlider with SuperMixin

  def min: Int = peer.getMinimum
  def min_=(v: Int): Unit = peer.setMinimum(v)
  def max: Int = peer.getMaximum
  def max_=(v: Int): Unit = peer.setMaximum(v)
  def value: Int = peer.getValue
  def value_=(v: Int): Unit = peer.setValue(v)
  def extent: Int = peer.getExtent
  def extent_=(v: Int): Unit = peer.setExtent(v)

  def paintLabels: Boolean = peer.getPaintLabels
  def paintLabels_=(v: Boolean): Unit = peer.setPaintLabels(v)
  def paintTicks: Boolean = peer.getPaintTicks
  def paintTicks_=(v: Boolean): Unit = peer.setPaintTicks(v)
  def paintTrack: Boolean = peer.getPaintTrack
  def paintTrack_=(v: Boolean): Unit = peer.setPaintTrack(v)

  def snapToTicks: Boolean = peer.getSnapToTicks
  def snapToTicks_=(v: Boolean): Unit = peer.setSnapToTicks(v)

  def minorTickSpacing: Int = peer.getMinorTickSpacing
  def minorTickSpacing_=(v: Int): Unit = peer.setMinorTickSpacing(v)
  def majorTickSpacing: Int = peer.getMajorTickSpacing
  def majorTickSpacing_=(v: Int): Unit = peer.setMajorTickSpacing(v)

  def adjusting: Boolean = peer.getValueIsAdjusting

  def labels: scala.collection.Map[Int, Label] = {
    import scala.collection.JavaConverters._
    val labelTable = peer.getLabelTable.asInstanceOf[java.util.Map[Int, JLabel]].asScala
    labelTable.map { case (k, v) => (k, UIElement.cachedWrapper[Label](v)) }
  }

  def labels_=(l: scala.collection.Map[Int, Label]): Unit = {
    // TODO: do some lazy wrapping
    val table = new java.util.Hashtable[java.lang.Integer, javax.swing.JComponent]
    for ((k,v) <- l) table.put(k, v.peer)
    peer.setLabelTable(table)
  }

  peer.addChangeListener(new javax.swing.event.ChangeListener {
    def stateChanged(e: javax.swing.event.ChangeEvent): Unit =
      publish(new event.ValueChanged(Slider.this))
  })
}

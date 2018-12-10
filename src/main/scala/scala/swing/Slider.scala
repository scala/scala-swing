/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.swing

import javax.swing.{JSlider, JLabel}
import scala.swing.event._

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
    //labelTable.mapValues(v => UIElement.cachedWrapper[Label](v)).toMap
    labelTable.map { case (k,v) => (k, UIElement.cachedWrapper[Label](v)) }
  }
  def labels_=(l: scala.collection.Map[Int, Label]): Unit = {
    // TODO: do some lazy wrapping
    val table = new java.util.Hashtable[java.lang.Integer, javax.swing.JComponent]
    for ((k,v) <- l) table.put(k, v.peer)
    peer.setLabelTable(table)
  }

  peer.addChangeListener(new javax.swing.event.ChangeListener {
    def stateChanged(e: javax.swing.event.ChangeEvent): Unit =
      publish(new ValueChanged(Slider.this))
  })
}

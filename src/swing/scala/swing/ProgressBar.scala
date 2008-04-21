package scala.swing

import event._

/**
 * @see javax.swing.JProgressBar
 */
class ProgressBar(override val peer: javax.swing.JProgressBar) extends Component(peer) with Orientable {
  def this() = this(new javax.swing.JProgressBar)

  def min: Int = peer.getMinimum
  def min_=(v: Int) { peer.setMinimum(v) }
  def max: Int = peer.getMaximum
  def max_=(v: Int) { peer.setMaximum(v) }
  def value: Int = peer.getValue
  def value_=(v: Int) { peer.setValue(v) }

  def labelPainted: Boolean = peer.isStringPainted
  def labelPainted_=(v: Boolean) { peer.setStringPainted(v) }

  def label: String = peer.getString
  def label_=(v: String) = peer.setString(v)

  def indeterminate: Boolean = peer.isIndeterminate
  def indeterminate_=(v: Boolean) { peer.setIndeterminate(v) }

  def paintBorder: Boolean = peer.isBorderPainted
  def paintBorder(v: Boolean) { peer.setBorderPainted(v) }
}
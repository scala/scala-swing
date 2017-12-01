/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



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

  private def layoutManager = peer.getLayout.asInstanceOf[java.awt.FlowLayout]

  def vGap: Int = layoutManager.getVgap
  def vGap_=(n: Int): Unit = layoutManager.setVgap(n)
  def hGap: Int = layoutManager.getHgap
  def hGap_=(n: Int): Unit = layoutManager.setHgap(n)
}

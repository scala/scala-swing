/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.awt.{GridBagConstraints, GridBagLayout}

object GridBagPanel {
  object Fill extends Enumeration {
    import GridBagConstraints._
    val None          : Fill.Value    = Value(NONE)
    val Horizontal    : Fill.Value    = Value(HORIZONTAL)
    val Vertical      : Fill.Value    = Value(VERTICAL)
    val Both          : Fill.Value    = Value(BOTH)
  }
  object Anchor extends Enumeration {
    import GridBagConstraints._
    val North         : Anchor.Value  = Value(NORTH)
    val NorthEast     : Anchor.Value  = Value(NORTHEAST)
    val East          : Anchor.Value  = Value(EAST)
    val SouthEast     : Anchor.Value  = Value(SOUTHEAST)
    val South         : Anchor.Value  = Value(SOUTH)
    val SouthWest     : Anchor.Value  = Value(SOUTHWEST)
    val West          : Anchor.Value  = Value(WEST)
    val NorthWest     : Anchor.Value  = Value(NORTHWEST)
    val Center        : Anchor.Value  = Value(CENTER)

    val PageStart     : Anchor.Value  = Value(PAGE_START)
    val PageEnd       : Anchor.Value  = Value(PAGE_END)
    val LineStart     : Anchor.Value  = Value(LINE_START)
    val LineEnd       : Anchor.Value  = Value(LINE_END)
    val FirstLineStart: Anchor.Value  = Value(FIRST_LINE_START)
    val FirstLineEnd  : Anchor.Value  = Value(FIRST_LINE_END)
    val LastLineStart : Anchor.Value  = Value(LAST_LINE_START)
    val LastLineEnd   : Anchor.Value  = Value(LAST_LINE_END)
  }
}

/**
 * A panel that arranges its children in a grid. Layout details can be
 * given for each cell of the grid.
 *
 * @see java.awt.GridBagLayout
 */
class GridBagPanel extends Panel with LayoutContainer {
  override lazy val peer = new javax.swing.JPanel(new GridBagLayout) with SuperMixin
  import GridBagPanel._

  private def layoutManager = peer.getLayout.asInstanceOf[GridBagLayout]

  /**
   * Convenient conversion from xy-coords given as pairs to
   * grid bag constraints.
   */
  implicit def pair2Constraints(p: (Int, Int)): Constraints = {
    val c = new Constraints
    c.gridx = p._1
    c.gridy = p._2
    c
  }

  class Constraints(val peer: GridBagConstraints) extends Proxy {
    def self = peer
    def this(gridx: Int, gridy: Int,
             gridwidth: Int, gridheight: Int,
             weightx: Double, weighty: Double,
             anchor: Int, fill: Int, insets: Insets,
             ipadx: Int, ipady: Int) =
      this(new GridBagConstraints(gridx, gridy,
                                  gridwidth, gridheight,
                                  weightx, weighty,
                                  anchor, fill, insets,
                                  ipadx, ipady))
    def this() = this(new GridBagConstraints())
    def gridx: Int = peer.gridx
    def gridx_=(x: Int): Unit = { peer.gridx = x }
    def gridy: Int = peer.gridy
    def gridy_=(y: Int): Unit = { peer.gridy = y }
    def grid: (Int, Int) = (gridx, gridy)
    def grid_=(c: (Int, Int)): Unit = {
      gridx = c._1
      gridy = c._2
    }

    def gridwidth: Int = peer.gridwidth
    def gridwidth_=(w: Int): Unit = { peer.gridwidth = w }
    def gridheight: Int = peer.gridheight
    def gridheight_=(h: Int): Unit = { peer.gridheight = h }
    def weightx: Double = peer.weightx
    def weightx_=(x: Double): Unit = { peer.weightx = x }
    def weighty: Double = peer.weighty
    def weighty_=(y: Double): Unit = { peer.weighty = y }
    def anchor: Anchor.Value = Anchor(peer.anchor)
    def anchor_=(a: Anchor.Value): Unit = { peer.anchor = a.id }
    def fill: Fill.Value = Fill(peer.fill)
    def fill_=(f: Fill.Value): Unit = { peer.fill = f.id }
    def insets: Insets = peer.insets
    def insets_=(i: Insets): Unit = { peer.insets = i }
    def ipadx: Int = peer.ipadx
    def ipadx_=(x: Int): Unit = { peer.ipadx = x }
    def ipady: Int = peer.ipady
    def ipady_=(y: Int): Unit = { peer.ipady = y }
  }

  protected def constraintsFor(comp: Component) =
    new Constraints(layoutManager.getConstraints(comp.peer))

  protected def areValid(c: Constraints): (Boolean, String) = (true, "")
  protected def add(c: Component, l: Constraints): Unit = peer.add(c.peer, l.peer)
}

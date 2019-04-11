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

import javax.swing.JSplitPane

import scala.collection.immutable
import scala.swing.Swing.nullPeer

/** A container with exactly two children. Arranges them side by side, either
  * horizontally or vertically. Displays a draggable divider component between
  * them that lets the user adjust the size ratio of the children.
  *
  * @param  o the orientation of the divider. Note that we are using
  *           `Orientation.Horizontal` and `Orientation.Vertical`, which are
  *           different from the underlying `JSplitPane` values.
  *           `Orientation.Horizontal` corresponds with `VERTICAL_SPLIT`, thus
  *           producing a left and right component and a "vertical divider",
  *           and `Orientation.Vertical` corresponds with `HORIZONTAL_SPLIT`, thus
  *           producing a top and bottom component and a "horizontal divider".
  *
  * @see javax.swing.JSplitPane
  */
class SplitPane(o: Orientation.Value, left: Component, right: Component)
  extends Component with Container with Orientable.Wrapper {

  def this(o: Orientation.Value) = this(o, new Component {}, new Component {})
  def this() = this(Orientation.Horizontal)

  override lazy val peer: JSplitPane =
    new javax.swing.JSplitPane(o.id, left.peer, right.peer) with SuperMixin

  def contents: immutable.Seq[Component] = List(leftComponent, rightComponent)

  def contents_=(left: Component, right: Component): Unit = {
    peer.setLeftComponent (nullPeer(left))
    peer.setRightComponent(nullPeer(right))
  }

  def topComponent: Component =
    UIElement.cachedWrapper[Component](peer.getTopComponent.asInstanceOf[javax.swing.JComponent])
  def topComponent_=(c: Component): Unit = peer.setTopComponent(nullPeer(c))

  def bottomComponent: Component =
    UIElement.cachedWrapper[Component](peer.getBottomComponent.asInstanceOf[javax.swing.JComponent])
  def bottomComponent_=(c: Component): Unit = peer.setBottomComponent(nullPeer(c))

  def leftComponent     : Component         =   topComponent
  def leftComponent_= (c: Component): Unit  = { topComponent = c }

  def rightComponent    : Component         =   bottomComponent
  def rightComponent_=(c: Component): Unit  = { bottomComponent = c }

  def dividerLocation       : Int             = peer.getDividerLocation
  def dividerLocation_=   (n: Int): Unit      = peer.setDividerLocation(n)

  /*def proportionalDividerLocation: Double =
    if (orientation == Orientation.Vertical) dividerLocation / (size.height - dividerSize)
    else dividerLocation / (size.width - dividerSize)*/
  def dividerLocation_=(f: Double): Unit = peer.setDividerLocation(f)

  def dividerSize           : Int             = peer.getDividerSize
  def dividerSize_=       (n: Int): Unit      = peer.setDividerSize(n)

  def resizeWeight          : Double          = peer.getResizeWeight
  def resizeWeight_=      (n: Double): Unit   = peer.setResizeWeight(n)

  def resetToPreferredSizes(): Unit = peer.resetToPreferredSizes()

  def oneTouchExpandable    : Boolean         = peer.isOneTouchExpandable
  def oneTouchExpandable_=(b: Boolean): Unit  = peer.setOneTouchExpandable(b)

  def continuousLayout      : Boolean         = peer.isContinuousLayout
  def continuousLayout_=  (b: Boolean): Unit  = peer.setContinuousLayout(b)
}

package scala.swing

import javax.swing.JDesktopPane

import scala.collection.immutable


/**
 * A pane that can host nested internal windows (represented by [[InternalFrame]]).
 */
class DesktopPane extends Component with SequentialContainer.Wrapper {
  import DesktopPane._

  override lazy val peer : JDesktopPane = new JDesktopPane with SuperMixin

  /**
   * Returns all internal frames in this pane, including iconified ones.
   *
   * @return a list of internal frames.
   */
  def frames : immutable.Seq[InternalFrame] =
    peer.getAllFrames.toSeq.view.map(UIElement.cachedWrapper[InternalFrame]).toVector

  /**
   * Returns the currently selected frame, if one is selected.
   *
   * @return the currently selected frame, or `None` if none is selected.
   */
  def selectedFrame : Option[InternalFrame] =
    Option(peer.getSelectedFrame).map(UIElement.cachedWrapper[InternalFrame])

  /**
   * Indicates how dragged frames will be animated.
   *
   * @return either `LiveDragMode` or `OutlineDragMode`.
   */
  def dragMode : DragMode = DragMode(peer.getDragMode)

  /**
   * Specifies how dragged frames will be animated.
   *
   * @param newMode either `LiveDragMode` or `OutlineDragMode`.
   */
  def dragMode_= (newMode : DragMode) : Unit = peer.setDragMode(newMode.intValue)
}


object DesktopPane {

  /**
   * Indicates how an internal frame will be animated as it is dragged.
   */
  final case class DragMode(intValue : Int)

  /**
   * Indicates that a dragged internal frame will be animated with its contents.
   */
  val LiveDragMode: DragMode = DragMode(JDesktopPane.LIVE_DRAG_MODE)

  /**
   * Indicates that a dragged internal frame will only render as an outline.
   */
  val OutlineDragMode: DragMode = DragMode(JDesktopPane.OUTLINE_DRAG_MODE)
}

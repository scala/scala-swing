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
   * @param newMode either `DragMode.Live` or `DragMode.Outline`.
   */
  def dragMode_= (newMode : DragMode) : Unit = peer.setDragMode(newMode.id)
}


object DesktopPane {

  /**
   * Supported drag modes for internal frames.
   */
  //noinspection TypeAnnotation
  object DragMode extends Enumeration {
    /**
     * Renders the contents of the frame while dragging.
     */
    val Live = Value(JDesktopPane.LIVE_DRAG_MODE)

    /**
     * Renders only the outline of the frame while dragging.
     */
    val Outline = Value(JDesktopPane.OUTLINE_DRAG_MODE)
  }

  /**
   * Type indicating how an internal frame will be animated as it is dragged.
   */
  type DragMode = DragMode.Value

}

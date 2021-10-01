package scala.swing

import javax.swing.{Icon, JInternalFrame, WindowConstants}

import scala.swing.event.{InternalFrameActivated, InternalFrameClosed, InternalFrameClosing, InternalFrameDeactivated, InternalFrameDeiconified, InternalFrameIconified, InternalFrameOpened}


/**
 * A window that can be nested inside another window (typically within a [[DesktopPane]]).
 */
class InternalFrame extends Component with RootPanel with Publisher { outer =>
  import javax.swing.event.{InternalFrameEvent => PeerEvent, InternalFrameListener => PeerListener}

  override lazy val peer: JInternalFrame = new JInternalFrame with InterfaceMixin
  peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

  trait InterfaceMixin extends JInternalFrame with SuperMixin {
    override def doDefaultCloseAction(): Unit = {
      super.doDefaultCloseAction()
      closeOperation()
    }
  }

  def title: String = peer.getTitle
  def title_=(s: String): Unit = peer.setTitle(s)

  /**
    * The menu bar of this frame or [[NoMenuBar]] if no menu bar is set.
    */
  def menuBar: MenuBar = {
    val m = UIElement.cachedWrapper[MenuBar](peer.getJMenuBar)
    if (m != null) m else MenuBar.NoMenuBar
  }

  /**
    * Set the current menu bar of this frame. Pass `NoMenuBar` if this frame
    * should not show a menu bar.
    */
  def menuBar_=(m: MenuBar): Unit =
    peer.setJMenuBar(if (m == MenuBar.NoMenuBar) null else m.peer)

  def resizable_=(b: Boolean): Unit = peer.setResizable(b)
  def resizable: Boolean = peer.isResizable

  def maximizable : Boolean = peer.isMaximizable
  def maximizable_= (b : Boolean) : Unit = peer.setMaximizable(b)

  def maximized : Boolean = peer.isMaximum
  def maximize() : Unit = peer.setMaximum(true)
  def unmaximize() : Unit = peer.setMaximum(false)

  def location_=(p: Point): Unit = peer.setLocation(p)
  def size_=(size: Dimension): Unit = peer.setSize(size)
  def bounds_=(rect: Rectangle): Unit = peer.setBounds(rect)

  def closable : Boolean = peer.isClosable
  def closable_= (b : Boolean): Unit = peer.setClosable(b)

  def closed : Boolean = peer.isClosed
  def close() : Unit = peer.setClosed(true)

  def iconifiable : Boolean = peer.isIconifiable
  def iconifiable_= (b : Boolean) : Unit = peer.setIconifiable(b)

  def iconified : Boolean = peer.isIcon
  def iconify() : Unit = peer.setIcon(true)
  def uniconify() : Unit = peer.setIcon(false)

  def frameIcon : Icon = peer.getFrameIcon
  def frameIcon_= (icon : Icon) : Unit = peer.setFrameIcon(icon)

  def dispose(): Unit = peer.dispose()

  def pack(): this.type = { peer.pack(); this }

  def show() : Unit = peer.show()
  def hide() : Unit = peer.hide()

  def moveToBack() : Unit = peer.moveToBack()
  def moveToFront() : Unit = peer.moveToFront()

  def layer : Int = peer.getLayer
  def layer_= (n : Int) : Unit = peer.setLayer(n)

  def selected : Boolean = peer.isSelected
  def select() : Unit = peer.setSelected(true)
  def deselect() : Unit = peer.setSelected(false)

  /**
    * This method is called when the window is closing, after all other window
    * event listeners have been processed.
    *
    * Default behavior is to dispose of the internal frame, but other options include hiding the frame
    * or doing nothing at all.
    */
  def closeOperation(): Unit = dispose()

  peer.addInternalFrameListener(new PeerListener {
    override def internalFrameOpened(e: PeerEvent): Unit = publish(InternalFrameOpened(outer))

    override def internalFrameClosing(e: PeerEvent): Unit = publish(InternalFrameClosing(outer))

    override def internalFrameClosed(e: PeerEvent): Unit = publish(InternalFrameClosed(outer))

    override def internalFrameIconified(e: PeerEvent): Unit = publish(InternalFrameIconified(outer))

    override def internalFrameDeiconified(e: PeerEvent): Unit = publish(InternalFrameDeiconified(outer))

    override def internalFrameActivated(e: PeerEvent): Unit = publish(InternalFrameActivated(outer))

    override def internalFrameDeactivated(e: PeerEvent): Unit = publish(InternalFrameDeactivated(outer))
  })
}

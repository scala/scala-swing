/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.awt.{ Window => AWTWindow, Frame => AWTFrame }
import javax.swing._
import Swing._

object RichWindow {
  /**
   * Mixin this trait if you want an undecorated window.
   */
  trait Undecorated extends RichWindow {
    // we do a mixin here, since setUndecorated is only allowed to be called
    // when the component is not displayable.
    peer.setUndecorated(true)
  }
}

/**
 * A window that adds some functionality to the plain Window class and serves as
 * the common base class for frames and dialogs.
 *
 * Implementation note: this class is sealed since we need to know that a rich
 * window is either a dialog or a frame at some point.
 */
sealed trait RichWindow extends Window {
  def peer: AWTWindow with InterfaceMixin

  trait InterfaceMixin extends super.InterfaceMixin {
    def getJMenuBar(): JMenuBar
    def setJMenuBar(b: JMenuBar): Unit
    def setUndecorated(b: Boolean): Unit
    def setTitle(s: String): Unit
    def getTitle(): String
    def setResizable(b: Boolean): Unit
    def isResizable(): Boolean
  }

  def title: String = peer.getTitle
  def title_=(s: String): Unit = peer.setTitle(s)

  /**
   * The menu bar of this frame or `NoMenuBar` if no menu bar is set.
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
}

/**
 * A window with decoration such as a title, border, and action buttons.
 *
 * An AWT window cannot be wrapped dynamically with this class, i.e., you cannot
 * write something like new Window { def peer = myAWTWindow }
 *
 * @see javax.swing.JFrame
 */
class Frame(gc: java.awt.GraphicsConfiguration = null) extends RichWindow {
  override lazy val peer: JFrame with InterfaceMixin = new JFrame(gc) with InterfaceMixin with SuperMixin

  def iconify(): Unit = { peer.setExtendedState(peer.getExtendedState | AWTFrame.ICONIFIED) }
  def uniconify(): Unit = { peer.setExtendedState(peer.getExtendedState & ~AWTFrame.ICONIFIED) }
  def iconified: Boolean = (peer.getExtendedState & AWTFrame.ICONIFIED) != 0
  def maximize(): Unit = { peer.setExtendedState(peer.getExtendedState | AWTFrame.MAXIMIZED_BOTH) }
  def unmaximize(): Unit = { peer.setExtendedState(peer.getExtendedState & ~AWTFrame.MAXIMIZED_BOTH) }
  def maximized: Boolean = (peer.getExtendedState & AWTFrame.MAXIMIZED_BOTH) != 0

  def iconImage: Image = peer.getIconImage
  def iconImage_=(i: Image): Unit = peer.setIconImage(i)
}

/**
 * Simple predefined dialogs.
 *
 * @see javax.swing.JOptionPane
 */
object Dialog {
  /**
   * The message type of a dialog.
   */
  object Message extends Enumeration {
    import JOptionPane._
    val Error   : Message.Value = Value(ERROR_MESSAGE)
    val Info    : Message.Value = Value(INFORMATION_MESSAGE)
    val Warning : Message.Value = Value(WARNING_MESSAGE)
    val Question: Message.Value = Value(QUESTION_MESSAGE)
    val Plain   : Message.Value = Value(PLAIN_MESSAGE)
  }

  /**
   * The possible answers a user can select.
   */
  object Options extends Enumeration {
    import JOptionPane._
    val Default    : Options.Value = Value(DEFAULT_OPTION)
    val YesNo      : Options.Value = Value(YES_NO_OPTION)
    val YesNoCancel: Options.Value = Value(YES_NO_CANCEL_OPTION)
    val OkCancel   : Options.Value = Value(OK_CANCEL_OPTION)
  }

  /**
   * The selected result of dialog.
   */
  object Result extends Enumeration {
    import JOptionPane._
    val Yes   : Result.Value = Value(YES_OPTION)
    val Ok    : Result.Value = Yes  // N.B. Do not use `Value` because id 0 is already used
    val No    : Result.Value = Value(NO_OPTION)
    val Cancel: Result.Value = Value(CANCEL_OPTION)
    val Closed: Result.Value = Value(CLOSED_OPTION)
  }

  private def uiString(txt: String) = UIManager.getString(txt)

  def showConfirmation(parent: PeerContainer = null,
                       message: Any,
                       title: String = uiString("OptionPane.titleText"),
                       optionType: Options.Value = Options.YesNo,
                       messageType: Message.Value = Message.Question,
                       icon: Icon = EmptyIcon): Result.Value =
    Result(JOptionPane.showConfirmDialog(nullPeer(parent), message, title,
      optionType.id, messageType.id, Swing.wrapIcon(icon)))

  def showOptions(parent: PeerContainer = null,
                  message: Any,
                  title: String = uiString("OptionPane.titleText"),
                  optionType: Options.Value = Options.YesNo,
                  messageType: Message.Value = Message.Question,
                  icon: Icon = EmptyIcon,
                  entries: Seq[Any],
                  initial: Int): Result.Value = {
    val r = JOptionPane.showOptionDialog(nullPeer(parent), message, title,
      optionType.id, messageType.id, Swing.wrapIcon(icon),
      (entries map toAnyRef).toArray, entries(initial))
    Result(r)
  }

  def showInput[A](parent: PeerContainer = null,
                   message: Any,
                   title: String = uiString("OptionPane.inputDialogTitle"),
                   messageType: Message.Value = Message.Question,
                   icon: Icon = EmptyIcon,
                   entries: Seq[A] = Nil,
                   initial: A): Option[A] = {
    val e = if (entries.isEmpty) null
    else (entries map toAnyRef).toArray
    val r = JOptionPane.showInputDialog(nullPeer(parent), message, title,
      messageType.id, Swing.wrapIcon(icon),
      e, initial)

    toOption[A](r)
  }
  def showMessage(parent: PeerContainer = null,
                  message: Any,
                  title: String = uiString("OptionPane.messageDialogTitle"),
                  messageType: Message.Value = Message.Info,
                  icon: Icon = EmptyIcon): Unit = {
    JOptionPane.showMessageDialog(nullPeer(parent), message, title,
      messageType.id, Swing.wrapIcon(icon))
  }
}

/**
 * A dialog window.
 *
 * @see javax.swing.JDialog
 */
class Dialog(owner: Window, gc: java.awt.GraphicsConfiguration = null) extends RichWindow {
  override lazy val peer: JDialog with InterfaceMixin =
    if (owner == null) new JDialog with InterfaceMixin with SuperMixin
    else owner match {
      case f: Frame => new JDialog(f.peer, "", false, gc) with InterfaceMixin with SuperMixin
      case d: Dialog => new JDialog(d.peer, "", false, gc) with InterfaceMixin with SuperMixin
    }

  def this() = this(null)

  def modal_=(b: Boolean): Unit = peer.setModal(b)
  def modal: Boolean = peer.isModal
}


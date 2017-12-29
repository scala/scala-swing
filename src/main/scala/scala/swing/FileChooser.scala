/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.io.File
import javax.swing._
import javax.swing.filechooser._

object FileChooser {
  /**
   * The result of a file dialog. The precise meaning of the `Approve`
   * result depends on the specific dialog type. Could be `"save"` or
   * `"open"` for instance.
   */
  object Result extends Enumeration {
    import JFileChooser._
    val Cancel : Result.Value = Value(CANCEL_OPTION)
    val Approve: Result.Value = Value(APPROVE_OPTION)
    val Error  : Result.Value = Value(ERROR_OPTION)
  }

  /**
   * The kind of elements a user can select in a file dialog.
   */
  object SelectionMode extends Enumeration {
    import JFileChooser._
    val FilesOnly          : SelectionMode.Value = Value(FILES_ONLY)
    val DirectoriesOnly    : SelectionMode.Value = Value(DIRECTORIES_ONLY)
    val FilesAndDirectories: SelectionMode.Value = Value(FILES_AND_DIRECTORIES)
  }
}

/**
 * Used to open file dialogs.
 *
 * @see  [[http://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html javax.swing.JFileChooser]]
 */
class FileChooser(dir: File) {
  import scala.swing.FileChooser._
  lazy val peer: JFileChooser = new JFileChooser(dir)

  def this() = this(null)

  import scala.swing.Swing._

  /**
   * Display a dialog box to select an "Open File" file.
   * @param over Parent container -  [[scala.swing.Component Component]], [[scala.swing.Frame Frame]] or [[scala.swing.Dialog Dialog]]
   * @return a [[scala.swing.FileChooser.Result Result]] value based how dialog was closed.
   */
  def showOpenDialog(over: PeerContainer): Result.Value = Result(peer.showOpenDialog(nullPeer(over)))

  /**
   * Display a dialog box to select a "Save File" file.
   * @param over Parent container -  [[scala.swing.Component Component]], [[scala.swing.Frame Frame]] or [[scala.swing.Dialog Dialog]]
   * @return a [[scala.swing.FileChooser.Result Result]] value based how dialog was closed.
   */
  def showSaveDialog(over: PeerContainer): Result.Value = Result(peer.showSaveDialog(nullPeer(over)))

  /**
   * Display a dialog box to select a file.
   * @param over Parent container -  [[scala.swing.Component Component]], [[scala.swing.Frame Frame]] or [[scala.swing.Dialog Dialog]]
   * @param approveText Text for the 'ok' or 'approve' button.
   * @return a [[scala.swing.FileChooser.Result Result]] value based how dialog was closed.
   */
  def showDialog(over: PeerContainer, approveText: String): Result.Value = Result(peer.showDialog(nullPeer(over), approveText))

  def controlButtonsAreShown: Boolean = peer.getControlButtonsAreShown
  def controlButtonsAreShown_=(b: Boolean): Unit = peer.setControlButtonsAreShown(b)

  def title: String = peer.getDialogTitle
  def title_=(t: String): Unit = peer.setDialogTitle(t)

  def accessory: Component = UIElement.cachedWrapper[Component](peer.getAccessory)
  def accessory_=(c: Component): Unit = peer.setAccessory(c.peer)

  def fileHidingEnabled: Boolean = peer.isFileHidingEnabled
  def fileHidingEnabled_=(b: Boolean): Unit = peer.setFileHidingEnabled(b)
  def fileSelectionMode: SelectionMode.Value = SelectionMode(peer.getFileSelectionMode)
  def fileSelectionMode_=(s: SelectionMode.Value): Unit = peer.setFileSelectionMode(s.id)
  def fileFilter: FileFilter = peer.getFileFilter
  def fileFilter_=(f: FileFilter): Unit = peer setFileFilter f

  def selectedFile: File = peer.getSelectedFile
  def selectedFile_=(file: File): Unit = peer.setSelectedFile(file)
  def selectedFiles: Seq[File] = peer.getSelectedFiles
  def selectedFiles_=(files: File*): Unit = peer.setSelectedFiles(files.toArray)

  def multiSelectionEnabled: Boolean = peer.isMultiSelectionEnabled
  def multiSelectionEnabled_=(b: Boolean): Unit = peer.setMultiSelectionEnabled(b)

  def iconFor(f: File): Icon = peer.getIcon(f)
  def descriptionFor(f: File): String = peer.getDescription(f)
  def nameFor(f: File): String = peer.getName(f)
  def typeDescriptionFor(f: File): String = peer.getTypeDescription(f)
  def traversable(f: File): Boolean = peer.isTraversable(f)

  def acceptAllFileFilter: FileFilter = peer.getAcceptAllFileFilter

  /*peer.addPropertyChangeListener(new java.beans.PropertyChangeListener {
    def propertyChange(e: java.beans.PropertyChangeEvent) {
      import JFileChooser._
      e.getPropertyName match {
        case APPROVE_BUTTON_TEXT_CHANGED_PROPERTY =>
        case ACCESSORY_CHANGED_PROPERTY =>
        case APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY =>
        case APPROVE_BUTTON_TEXT_CHANGED_PROPERTY =>
        case APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY =>
        case CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY =>
        case CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY =>
        case DIALOG_TITLE_CHANGED_PROPERTY =>
        case DIALOG_TYPE_CHANGED_PROPERTY =>
        case DIRECTORY_CHANGED_PROPERTY =>
        case FILE_FILTER_CHANGED_PROPERTY =>
        case FILE_HIDING_CHANGED_PROPERTY =>
        case FILE_SELECTION_MODE_CHANGED_PROPERTY =>
        case FILE_SYSTEM_VIEW_CHANGED_PROPERTY =>
        case FILE_VIEW_CHANGED_PROPERTY =>
        case MULTI_SELECTION_ENABLED_CHANGED_PROPERTY =>
        case SELECTED_FILE_CHANGED_PROPERTY =>
        case SELECTED_FILES_CHANGED_PROPERTY =>
        case _ =>
      }
    }
  })*/
}

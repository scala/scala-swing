/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.components

import scala.collection.mutable.HashMap
import scala.concurrent.{ future, Future }
import scala.swing._
import scala.swing.event.{ActionEvent, CaretUpdate}
import java.awt.{ Color, Dimension, Event, Insets }
import java.awt.event.KeyEvent
import javax.swing.{ InputMap, KeyStroke, SwingUtilities, UIManager }
import javax.swing.event.{ DocumentEvent, DocumentListener, UndoableEditEvent, UndoableEditListener }
import javax.swing.text.{ AbstractDocument, BadLocationException, DefaultEditorKit, Document, SimpleAttributeSet, StyleConstants, StyledDocument, StyledEditorKit }
import javax.swing.undo.UndoManager

/*
 * Tutorials: How to Use Text Fields
 * http://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
 * How to Use Actions
 * http://docs.oracle.com/javase/tutorial/uiswing/misc/action.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/TextComponentDemo.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/DocumentSizeFilter.java
 * 
 * TextComponentDemo.scala requires one additional file:
 *   DocumentSizeFilter.scala
 */
class TextComponentDemo extends MainFrame {
  private val MaxCharacters = 300
  private val newline = "\n"
  title = "TextComponentDemo"
  val undo: UndoManager = new UndoManager()

  //Create the text pane and configure it.
  val textPane: TextPane = new TextPane()
  textPane.caret.position = 0
  textPane.peer.setMargin(new Insets(5, 5, 5, 5))
  val styledDoc: StyledDocument = textPane.styledDocument
  val doc: AbstractDocument = styledDoc.asInstanceOf[AbstractDocument]
  doc.setDocumentFilter(new DocumentSizeFilter(MaxCharacters))

  val scrollPane = new ScrollPane(textPane)
  scrollPane.preferredSize = new Dimension(200, 200)

  val undoAction = new UndoAction()
  val redoAction = new RedoAction()

  //Create the text area for the status log and configure it.
  val changeLog = new TextArea(5, 30)
  changeLog.editable = false
  val scrollPaneForLog = new ScrollPane(changeLog)

  //Create a split pane for the change log and the text area.
  val splitPane = new SplitPane(Orientation.Horizontal, scrollPane, scrollPaneForLog)
  splitPane.oneTouchExpandable = true

  //Create the status area
  val caretListenerLabel = new CaretListenerLabel("Caret Status")
  val statusPane = new GridPanel(1, 1) {
    contents += caretListenerLabel
  }

  contents = new BorderPanel() {
    layout(splitPane) = BorderPanel.Position.Center
    layout(statusPane) = BorderPanel.Position.South
  }

  //Set up the menu bar.
  val actions = createActionTable(textPane)
  val editMenu: Menu = createEditMenu()
  val styleMenu: Menu = createStyleMenu()
  val mb: MenuBar = new MenuBar() {
    contents += editMenu
    contents += styleMenu
  }
  menuBar = mb

  //Add some key bindings.
  addBindings()

  //Put the initial text into the text pane.
  initDocument()
  textPane.caret.position = 0

  //Start watching for undoable edits and caret changes.
  listenTo(textPane.caret)
  doc.addUndoableEditListener(new MyUndoableEditListener())
  reactions += {
    case CaretUpdate(textPane) =>
      caretListenerLabel.displaySelectionInfo(textPane.caret.dot, textPane.caret.mark)
  }
  doc.addDocumentListener(new MyDocumentListener())

  class MyUndoableEditListener extends UndoableEditListener {
    def undoableEditHappened(e: UndoableEditEvent) {
      undo.addEdit(e.getEdit())
      undoAction.updateUndoState()
      redoAction.updateRedoState()
    }
  }

  class MyDocumentListener extends DocumentListener {
    def insertUpdate(e: DocumentEvent): Unit = {
      displayEditInfo(e)
    }
    def removeUpdate(e: DocumentEvent): Unit = {
      displayEditInfo(e)
    }
    def changedUpdate(e: DocumentEvent): Unit = {
      displayEditInfo(e)
    }
    private def displayEditInfo(e: DocumentEvent): Unit = {
      val document: Document = e.getDocument()
      val changeLength = e.getLength()
      changeLog.append(e.getType.toString() + ":" +
        changeLength + " character" +
        (if (changeLength == 1) { ". " } else { "s. " }) +
        " Text length = " + document.getLength() +
        "." + newline)
    }
  }

  def addBindings(): Unit = {
    val inputMap: InputMap = textPane.peer.getInputMap
    //Ctrl-b to go backward one character
    val keyb: KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK)
    inputMap.put(keyb, DefaultEditorKit.backwardAction)
    //Ctrl-f to go forward one character
    val keyf: KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK)
    inputMap.put(keyf, DefaultEditorKit.forwardAction)
    //Ctrl-p to go up one line
    val keyp: KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK)
    inputMap.put(keyp, DefaultEditorKit.upAction)
    //Ctrl-n to go down one line
    val keyn: KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK)
    inputMap.put(keyn, DefaultEditorKit.downAction)
  }

  def createEditMenu(): Menu = {
    val menu: Menu = new Menu("Edit") {
      val undoMenuItem = new MenuItem(undoAction) {
        enabled = false
        reactions += {
          case scala.swing.event.ButtonClicked(_) => {
            import scala.concurrent.ExecutionContext.Implicits._
            val fUndo: Future[Unit] = future {
              Swing.onEDT {
            	val actionEvent = new ActionEvent(this)
              	undoAction.actionPerformed(actionEvent)
              }
            }
            fUndo onComplete {
              case _ => {}
            }
          }
        }
      }
      contents += undoMenuItem
      val redoMenuItem = new MenuItem(redoAction) {
        enabled = false
        reactions += {
          case scala.swing.event.ButtonClicked(_) => {
            import scala.concurrent.ExecutionContext.Implicits._
            val fRedo: Future[Unit] = future {
               Swing.onEDT {
              val actionEvent: ActionEvent = new ActionEvent(this)
              redoAction.actionPerformed(actionEvent)
               }
            }
            fRedo onComplete {
              case _ => {}
            }
          }
        }
      }
      
      contents += redoMenuItem
      contents += new Separator()
      //
      //These actions come from the default editor kit.
      //Get the ones we want and stick them in the menu.
      // cut-to-clipboard
      val oldCutAction = getActionByName(DefaultEditorKit.cutAction).get
      val miCut = new MenuItem(DefaultEditorKit.cutAction)
      miCut.peer.setAction(oldCutAction)
      contents += miCut
      // copy-to-clipboard
      val oldCopyAction = getActionByName(DefaultEditorKit.copyAction).get
      val miCopy = new MenuItem(DefaultEditorKit.copyAction)
      miCopy.peer.setAction(oldCopyAction)
      contents += miCopy
      // paste-from-clipboard
      val oldPasteAction = getActionByName(DefaultEditorKit.pasteAction).get
      val miPaste = new MenuItem(DefaultEditorKit.pasteAction)
      miPaste.peer.setAction(oldPasteAction)
      contents += miPaste
      //
      contents += new Separator()
      // select-all
      val oldSelectAllAction = getActionByName(DefaultEditorKit.selectAllAction).get
      val miSelectAll = new MenuItem(DefaultEditorKit.selectAllAction)
      miSelectAll.peer.setAction(oldSelectAllAction)
      contents += miSelectAll
    }
    menu
  }

  def createStyleMenu(): Menu = {
    val menu: Menu = new Menu("Style") {
      val boldAction = new StyledEditorKit.BoldAction()
      val miBold = new MenuItem("Bold")
      miBold.peer.setAction(boldAction)
      contents += miBold
      //
      val italicAction = new StyledEditorKit.ItalicAction()
      val miItalic = new MenuItem("Italic")
      miItalic.peer.setAction(italicAction)
      contents += miItalic
      //
      val underlineAction = new StyledEditorKit.UnderlineAction()
      val miUnderline = new MenuItem("Underline")
      miUnderline.peer.setAction(underlineAction)
      contents += miUnderline
      //
      contents += new Separator()
      //
      val font12Action = new StyledEditorKit.FontSizeAction("12", 12)
      val miFont12 = new MenuItem("12")
      miFont12.peer.setAction(font12Action)
      contents += miFont12
      //
      val font14Action = new StyledEditorKit.FontSizeAction("14", 14)
      val miFont14 = new MenuItem("14")
      miFont14.peer.setAction(font14Action)
      contents += miFont14
      //
      val font18Action = new StyledEditorKit.FontSizeAction("18", 18)
      val miFont18 = new MenuItem("18")
      miFont18.peer.setAction(font18Action)
      contents += miFont18
      //
      contents += new Separator()
      //
      val fontSerifAction = new StyledEditorKit.FontFamilyAction("Serif", "Serif")
      val miFontSerif = new MenuItem("Serif")
      miFontSerif.peer.setAction(fontSerifAction)
      contents += miFontSerif
      //
      val fontSansSerifAction = new StyledEditorKit.FontFamilyAction("SansSerif", "SansSerif")
      val miFontSansSerif = new MenuItem("SansSerif")
      miFontSansSerif.peer.setAction(fontSansSerifAction)
      contents += miFontSansSerif
      //
      contents += new Separator()
      //
      val fgRedAction = new StyledEditorKit.ForegroundAction("Red", Color.red)
      val miFgRed = new MenuItem("Red")
      miFgRed.peer.setAction(fgRedAction)
      contents += miFgRed
      //
      val fgGreenAction = new StyledEditorKit.ForegroundAction("Green", Color.green)
      val miFgGreen = new MenuItem("Green")
      miFgGreen.peer.setAction(fgGreenAction)
      contents += miFgGreen
      //
      val fgBlueAction = new StyledEditorKit.ForegroundAction("Blue", Color.blue)
      val miFgBlue = new MenuItem("Blue")
      miFgBlue.peer.setAction(fgBlueAction)
      contents += miFgBlue
      //
      val fgBlackAction = new StyledEditorKit.ForegroundAction("Black", Color.black)
      val miFgBlack = new MenuItem("Black")
      miFgBlack.peer.setAction(fgBlackAction)
      contents += miFgBlack
    }
    menu
  }

  def initDocument(): Unit = {
    val initString: Array[String] =
      Array("Use the mouse to place the caret.",
        "Use the edit menu to cut, copy, paste, and select text.",
        "Also to undo and redo changes.",
        "Use the style menu to change the style of the text.",
        "Use the arrow keys on the keyboard or these emacs key bindings to move the caret:",
        "Ctrl-f, Ctrl-b, Ctrl-n, Ctrl-p.")

    val attrs: Array[SimpleAttributeSet] = initAttributes(initString.length);
    for (i <- 0 until initString.length) {
      doc.insertString(doc.getLength(), initString(i) + newline, attrs(i));
    }
  }

  def initAttributes(length: Int): Array[SimpleAttributeSet] = {
    //Hard-code some attributes.
    val attrs = new Array[SimpleAttributeSet](length)
    attrs(0) = new SimpleAttributeSet()
    StyleConstants.setFontFamily(attrs(0), "SansSerif")
    StyleConstants.setFontSize(attrs(0), 16)
    attrs(1) = new SimpleAttributeSet(attrs(0))
    StyleConstants.setBold(attrs(1), true)
    attrs(2) = new SimpleAttributeSet(attrs(0))
    StyleConstants.setItalic(attrs(2), true)
    attrs(3) = new SimpleAttributeSet(attrs(0))
    StyleConstants.setFontSize(attrs(3), 20)
    attrs(4) = new SimpleAttributeSet(attrs(0))
    StyleConstants.setFontSize(attrs(4), 12)
    attrs(5) = new SimpleAttributeSet(attrs(0))
    StyleConstants.setForeground(attrs(5), Color.red)
    attrs
  }

  //The following two methods allow us to find an
  //action provided by the editor kit by its name.
  def createActionTable(textComponent: TextComponent): HashMap[String, javax.swing.Action] = {
    val hm = new HashMap[String, javax.swing.Action]()
    for (a <- textComponent.peer.getActions()) {
      val s = a.getValue(javax.swing.Action.NAME).asInstanceOf[String]
      hm.put(s, a)
    }
    hm
  }

  def getActionByName(name: String): Option[javax.swing.Action] = {
    actions.get(name)
  }

  class UndoAction extends Action("Undo") {
    enabled = false
    def apply() = {}
    def actionPerformed(e: scala.swing.event.ActionEvent): Unit = {
      try {
        undo.undo();
      } catch {
        case ex: javax.swing.undo.CannotUndoException =>
          println("Unable to undo: " + ex)
          ex.printStackTrace()
      }
      updateUndoState()
      redoAction.updateRedoState()
    }

    def updateUndoState(): Unit = {
      if (undo.canUndo()) {
        enabled = true
        peer.putValue(javax.swing.Action.NAME, undo.getUndoPresentationName())
      } else {
        enabled = false
        peer.putValue(javax.swing.Action.NAME, "Undo")
      }
    }
  }

  class RedoAction extends Action("Redo") {
    enabled = false
    def apply() = {}
    def actionPerformed(e: ActionEvent): Unit = {
      try {
        undo.redo()
      } catch {
        case ex: javax.swing.undo.CannotUndoException =>
          println("Unable to undo: " + ex)
          ex.printStackTrace()
      }
      updateRedoState();
      undoAction.updateUndoState()
    }

    def updateRedoState(): Unit = {
      if (undo.canRedo()) {
        enabled = true
        peer.putValue(javax.swing.Action.NAME, undo.getRedoPresentationName())
      } else {
        enabled = false
        peer.putValue(javax.swing.Action.NAME, "Redo")
      }
    }
  }

  class CaretListenerLabel(label: String) extends Label(label) {
    private val newline = "\n"
    text = label
    //This method can be invoked from any thread.  It
    //invokes the setText and modelToView methods, which
    //must run on the event dispatch thread. We use
    //invokeLater to schedule the code for execution
    //on the event dispatch thread.
    def displaySelectionInfo(dot: Int, mark: Int): Unit = {
      SwingUtilities.invokeLater(new Runnable() {
        def run(): Unit = {
          if (dot == mark) { // no selection
            try {
              val caretCoords: Rectangle = textPane.peer.modelToView(dot)
              //Convert it to view coordinates.
              text = "caret: text position: " + dot + ", view location = [" +
                caretCoords.x + ", " + caretCoords.y + "]" + newline
            } catch {
              case ble: BadLocationException =>
                text = "selection from: " + mark + " to " + dot + newline
            }
          } else if (dot < mark) {
            text = "selection from: " + dot + " to " + mark + newline
          } else {
            text = "selection from: " + mark + " to " + dot + newline
          }
        }
      })
    }
  }

}

object TextComponentDemo extends SimpleSwingApplication {
  lazy val top = new TextComponentDemo() {
    javax.swing.UIManager.put("swing.boldMetal", false)
  }
  javax.swing.SwingUtilities.updateComponentTreeUI(top.peer)
}
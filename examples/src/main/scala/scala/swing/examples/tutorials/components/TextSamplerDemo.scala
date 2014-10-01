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

import scala.swing._
import scala.swing.event._
import java.awt.{ Cursor, Font, Toolkit }
import java.net.URL
import javax.swing.text.{ StyleConstants, StyleContext }

/*
 * Tutorials: How to Use Text Components
 * http://docs.oracle.com/javase/tutorial/uiswing/components/text.html
 * How to Use Editor Panes and Text Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextSamplerDemoProject/src/components/TextSamplerDemo.java
 * 
 * TextSamplerDemo.scala requires the following files:
 *   TextSamplerDemoHelp.html (which references images/dukeWaveRed.gif)
 *   /scala/swing/examples/tutorials/images/Pig.gif
 *   /scala/swing/examples/tutorials/images/sound.gif
 */
object TextSamplerDemo extends SimpleSwingApplication {
  val newline = "\n"

  lazy val top = new MainFrame() {
      title = "TextSamplerDemo"

      private val textFieldString = "TextField"
      private val passwordFieldString = "PasswordField"
      private val ftfString = "FormattedTextField"
      private val buttonString = "Button"

      val button = new Button()

      //Create a regular text field.
      val textField = new TextField(10)

      //Create a password field.
      val passwordField = new PasswordField(10)

      //Create a formatted text field.
      val ftf = new FormattedTextField(java.text.DateFormat.getDateInstance())
      // Had to use the peer for this purpose..........................................
      ftf.peer.setValue(java.util.Calendar.getInstance().getTime())

      //Create some labels for the fields.
      val textFieldLabel = new Label(textFieldString + ": ")
      // textFieldLabel.setLabelFor(textField)
      val passwordFieldLabel = new Label(passwordFieldString + ": ")
      val ftfLabel = new Label(ftfString + ": ")
      // ftfLabel.setLabelFor(ftf)

      //Create a label to put messages during an action event.
      val actionLabel = new Label("Type text in a field and press Enter.")
      actionLabel.border = Swing.EmptyBorder(10, 0, 0, 0)

      //Lay out the text controls and the labels.
      val textControlsPane = new GridBagPanel {
        val c: Constraints = new Constraints()

        c.gridwidth = java.awt.GridBagConstraints.RELATIVE
        c.fill = GridBagPanel.Fill.None
        c.weightx = 0.0
        layout(textFieldLabel) = c

        c.gridwidth = java.awt.GridBagConstraints.REMAINDER
        c.fill = GridBagPanel.Fill.Horizontal
        c.weightx = 1.0
        layout(textField) = c

        c.gridwidth = java.awt.GridBagConstraints.RELATIVE
        c.fill = GridBagPanel.Fill.None
        c.weightx = 0.0
        layout(passwordFieldLabel) = c

        c.gridwidth = java.awt.GridBagConstraints.REMAINDER
        c.fill = GridBagPanel.Fill.Horizontal
        c.weightx = 1.0
        layout(passwordField) = c

        c.gridwidth = java.awt.GridBagConstraints.RELATIVE
        c.fill = GridBagPanel.Fill.None
        c.weightx = 0.0
        layout(ftfLabel) = c

        c.gridwidth = java.awt.GridBagConstraints.REMAINDER
        c.fill = GridBagPanel.Fill.Horizontal
        c.weightx = 1.0
        layout(ftf) = c

        c.gridwidth = java.awt.GridBagConstraints.REMAINDER // last
        c.anchor = GridBagPanel.Anchor.West
        c.weightx = 1.0
        layout(actionLabel) = c

        border = Swing.CompoundBorder(
          Swing.TitledBorder(Swing.EmptyBorder, "Text Fields"),
          Swing.EmptyBorder(5, 5, 5, 5))
      }

      //Create a text area.
      val textArea = new TextArea(
        "This is an editable TextArea. " +
          "A text area is a \"plain\" text component, " +
          "which means that although it can display text " +
          "in any font, all of the text is in the same font.")
      textArea.font = new Font("Serif", Font.ITALIC, 16)
      textArea.lineWrap = true
      textArea.wordWrap = true

      //Put the text area in a scrollpane
      val areaScrollPane: ScrollPane = new ScrollPane(textArea)
      areaScrollPane.verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
      areaScrollPane.preferredSize = new Dimension(250, 250)
      areaScrollPane.border =
        Swing.CompoundBorder(
        Swing.CompoundBorder(
        Swing.TitledBorder(Swing.EmptyBorder, "Plain Text"),
        Swing.EmptyBorder(5, 5, 5, 5)), areaScrollPane.border)

      //Create an editor pane.
      val editorPane: EditorPane = new EditorPane()
      editorPane.editable = false
      //To get this to work in Eclipse, add /src/main/resources directory to the run
      //configuration classpath.  The file TextSamplerDemoHelp.html is placed in that
      //directory.
      val helpURL: URL = getClass().getResource("/scala/swing/examples/tutorials/TextSamplerDemoHelp.html")

      if (helpURL != null) {
        editorPane.editorKit = new javax.swing.text.html.HTMLEditorKit
        editorPane.contentType = "text/html"
        // Had to reach into the peer for this...................
        editorPane.peer.setPage(helpURL)
      } else {
        textField.text = "TextSampleDemoHelp.html was not found."
      }

      val editorScrollPane = new ScrollPane(editorPane)
      editorScrollPane.verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
      editorScrollPane.preferredSize = new java.awt.Dimension(250, 145)
      editorScrollPane.minimumSize = new java.awt.Dimension(10, 10)

      //Create a text pane and put it into a scrollpane.
      // val textPane:TextPane = new TextPane()
      val textPane: TextPane = createTextPane(button)

      val paneScrollPane = new ScrollPane(textPane)

      paneScrollPane.verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
      paneScrollPane.preferredSize = new java.awt.Dimension(250, 155)
      paneScrollPane.minimumSize = new java.awt.Dimension(10, 10)

      //Put the editor pane and the text pane in a split pane.
      //Use Orientation.Horizontal instead of JSplitPane.VERTICAL_SPLIT
      val splitPane = new SplitPane(Orientation.Horizontal,
        editorScrollPane, paneScrollPane)
      splitPane.oneTouchExpandable = true
      splitPane.resizeWeight = 0.5

      //Place the splitpane in a panel destined for the right of the screen
      val rightPane = new GridPanel(1, 0) {
        contents += splitPane
        border = Swing.CompoundBorder(
          Swing.TitledBorder(Swing.EmptyBorder, "Styled Text"),
          Swing.EmptyBorder(5, 5, 5, 5))
      }

      //Place the text and area controls in a panel destined for the left
      // of the screen
      val leftPane = new BorderPanel() {
        layout(textControlsPane) = BorderPanel.Position.North
        layout(areaScrollPane) = BorderPanel.Position.Center
      }

      listenTo(textField)
      listenTo(passwordField)
      listenTo(button)
      val prefix = "You typed \""
      reactions += {
        case EditDone(`textField`) =>
          actionLabel.text = prefix + textField.text + "\""
        case EditDone(`passwordField`) =>
          actionLabel.text = prefix + passwordField.password + "\""
        case ButtonClicked(`button`) =>
          Toolkit.getDefaultToolkit().beep() // No sound heard...
          actionLabel.text = "Beep?!"
      }

      contents = new BorderPanel() {
        layout(leftPane) = BorderPanel.Position.West
        layout(rightPane) = BorderPanel.Position.East
      }
    }

  def addStylesToDocument(doc: javax.swing.text.StyledDocument, button: Button): Unit = {
    val `def` = StyleContext.getDefaultStyleContext.getStyle(StyleContext.DEFAULT_STYLE)
    val regular = doc.addStyle("regular", `def`)
    StyleConstants.setFontFamily(`def`, "SansSerif")
    val sItalic = doc.addStyle("italic", regular)
    StyleConstants.setItalic(sItalic, true)
    val sBold = doc.addStyle("bold", regular)
    StyleConstants.setBold(sBold, true)
    val sSmall = doc.addStyle("small", regular)
    StyleConstants.setFontSize(sSmall, 10)
    val sLarge = doc.addStyle("large", regular)
    StyleConstants.setFontSize(sLarge, 16)
    val sIcon = doc.addStyle("icon", regular)
    StyleConstants.setAlignment(sIcon, StyleConstants.ALIGN_CENTER)
    val pigIcon = createImageIcon("/scala/swing/examples/tutorials/images/Pig.gif", "a cute pig")
    if (pigIcon != None) {
      StyleConstants.setIcon(sIcon, pigIcon.get)
    }
    val sButton = doc.addStyle("button", regular)
    StyleConstants.setAlignment(sButton, StyleConstants.ALIGN_CENTER)
    val soundIcon = createImageIcon("/scala/swing/examples/tutorials/images/sound.gif", "sound icon")
    // button = new Button()
    if (soundIcon != None) {
      button.icon = soundIcon.get
    } else {
      button.text = "BEEP"
    }
    button.cursor = Cursor.getDefaultCursor
    button.margin = new Insets(0, 0, 0, 0)
    // button.setActionCommand(buttonString)
    // button.addActionListener(this)
    StyleConstants.setComponent(sButton, button.peer)
  }

  def createTextPane(button: Button): TextPane = {
    val initStrings: Array[String] = Array(
      "This is an editable TextPane, ", //regular
      "another ", //italic
      "styled ", //bold
      "text ", //small
      "component, ", //large
      "which supports embedded components..." + newline, //regular
      " " + newline, //button
      "...and embedded icons..." + newline, //regular
      " ", //icon
      newline + "Its peer, JTextPane, is a subclass of JEditorPane that " +
        "uses a StyledEditorKit and StyledDocument, and provides " +
        "cover methods for interacting with those objects.")
    val initStyles: Array[String] = Array(
      "regular", "italic", "bold", "small", "large",
      "regular", "button", "regular", "icon",
      "regular")
    val textPane = new TextPane()
    val doc: javax.swing.text.StyledDocument = textPane.styledDocument
    addStylesToDocument(doc, button)
    for (i <- 0 until initStrings.length) {
      doc.insertString(doc.getLength(), initStrings(i), doc.getStyle(initStyles(i)))
    }
    textPane
  }

  private def createImageIcon(path: String, description: String): Option[javax.swing.ImageIcon] = {
    val icon = Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))
    if (icon.isDefined) { icon.get.setDescription(description); icon } else None
  }
}





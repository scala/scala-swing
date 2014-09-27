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

import java.io.File
import java.net.URL

/*
 * Tutorial: How to Use File Choosers
 * http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemoProject/src/components/FileChooserDemo.java
 * 
 * FileChooserDemo.scala uses these files:
 *   /scala/swing/examples/tutorials/images/Open16.gif
 *   /scala/swing/examples/tutorials/images/Save16.gif
 */
object FileChooserDemo extends SimpleSwingApplication {
  private val newline = "\n"

  val log = new TextArea(5, 20)
  log.peer.setMargin(new Insets(5, 5, 5, 5))
  log.editable = false
  val logScrollPane = new ScrollPane(log)

  //Create a file chooser
  val fc = new FileChooser()

  //Uncomment one of the following lines to try a different
  //file selection mode.  The first allows just directories
  //to be selected (and, at least in the Java look and feel,
  //shown).  The second allows both files and directories
  //to be selected.  If you leave these lines commented out,
  //then the default mode (FILES_ONLY) will be used.
  //
  //fc.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
  //fc.fileSelectionMode = FileChooser.SelectionMode.FilesAndDirectories
  //fc.fileSelectionMode = FileChooser.SelectionMode.FilesOnly

  //Create the open button.  We use the image from the JLF
  //Graphics Repository (but we extracted it from the jar).
  val openButton = new Button("Open a File...")
  val openIconURL: URL = getClass().getResource("/scala/swing/examples/tutorials/images/Open16.gif")
  if (openIconURL != null) {
    openButton.icon = Swing.Icon(openIconURL)
  }
  
  //Create the save button.  We use the image from the JLF
  //Graphics Repository (but we extracted it from the jar).
  val saveButton = new Button("Save a File...")
  val saveIconURL: URL = getClass().getResource("/scala/swing/examples/tutorials/images/Save16.gif")
  if (saveIconURL != null) {
    saveButton.icon = Swing.Icon(saveIconURL)
  }
  
  //For layout purposes, put the buttons in a separate panel
  val buttonPanel = new FlowPanel() {
    contents += openButton
    contents += saveButton
  }
  
  lazy val top = new MainFrame {
    title = "FileChooserDemo"
    val bp = new BorderPanel {
      layout(buttonPanel) = BorderPanel.Position.North
      layout(logScrollPane) = BorderPanel.Position.Center
    }
    contents = bp
    listenTo(openButton)
    listenTo(saveButton)
    val prefix = "You typed \""
    reactions += {
      case ButtonClicked(`openButton`) =>
        if (fc.showOpenDialog(buttonPanel) == FileChooser.Result.Approve) {
          val file: File = fc.selectedFile
          //This is where a real application would open the file.
          log.append("Opening: " + file.getName() + "." + newline)
        }
        else {
          log.append("Open command cancelled by user." + newline)
        }
      case ButtonClicked(`saveButton`) =>
        if (fc.showOpenDialog(buttonPanel) == FileChooser.Result.Approve) {
          val file: File = fc.selectedFile
          //This is where a real application would save the file.
          log.append("Saving: " + file.getName() + "." + newline)
        }
        else {
          log.append("Save command cancelled by user." + newline)
        }
    }
  }

}
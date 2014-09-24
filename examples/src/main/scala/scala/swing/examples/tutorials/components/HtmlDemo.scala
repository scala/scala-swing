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
import scala.swing.event.ButtonClicked
import java.awt.{ Component, Dimension }
import scala.swing.event.Key

/*
 * Tutorial: How to Use HTML in Swing Components
 * http://docs.oracle.com/javase/tutorial/uiswing/components/html.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/HtmlDemoProject/src/components/HtmlDemo.java
 *
 * HtmlDemo.java needs no other files.
 */
class HtmlDemo extends BoxPanel(Orientation.Horizontal) {
  val initialText = "<html>\n" +
                "Color and font test:\n" +
                "<ul>\n" +
                "<li><font color=red>red</font>\n" +
                "<li><font color=blue>blue</font>\n" +
                "<li><font color=green>green</font>\n" +
                "<li><font size=-2>small</font>\n" +
                "<li><font size=+2>large</font>\n" +
                "<li><i>italic</i>\n" +
                "<li><b>bold</b>\n" +
                "</ul>\n"
  val htmlTextArea = new TextArea(10, 20) {
    text = initialText
  }
  val scrollPane = new ScrollPane(htmlTextArea)
  val changeTheLabel = new Button("Change the label") {
    mnemonic = Key.C
    // xAlignment = Alignment.Center
    peer.setAlignmentX(Component.CENTER_ALIGNMENT)
  }
  val theLabel = new Label(initialText) {
    minimumSize = new Dimension(200, 200)
    preferredSize = new Dimension(200, 200)
    maximumSize = new Dimension(200, 200)
    verticalAlignment = Alignment.Center
    horizontalAlignment = Alignment.Center
  }
  
  val leftPanel = new BoxPanel(Orientation.Vertical) {
    border = Swing.CompoundBorder(
        Swing.TitledBorder(Swing.EmptyBorder, "Edit the HTML, then click the button"),
        Swing.EmptyBorder(10, 10, 10, 10))
    contents ++= Array( scrollPane, Swing.RigidBox(new Dimension(0, 10)), changeTheLabel )
  }
  
  val rightPanel = new BoxPanel(Orientation.Vertical) {
    border = Swing.CompoundBorder(
        Swing.TitledBorder(Swing.EmptyBorder, "A label with HTML"),
        Swing.EmptyBorder(10, 10, 10, 10))
    contents += theLabel
  }
  
  border = Swing.EmptyBorder(10, 10, 10, 10)
  contents += leftPanel
  contents += rightPanel
  
  listenTo(changeTheLabel)
  reactions += {
    case ButtonClicked(`changeTheLabel`) => theLabel.text = htmlTextArea.text
  }
}

object HtmlDemo {
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  def createAndShowGUI(): Unit = {
    val frame = new Frame() {
      title = "HtmlDemo"
      //Create and set up the content pane.
      val newContentPane = new HtmlDemo();
      newContentPane.opaque = true //content panes must be opaque
      contents = newContentPane
      // Display the window
      pack()
      visible = true
      override def closeOperation() = {
        sys.exit(0)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = {
        //Turn off metal's use of bold fonts
        javax.swing.UIManager.put("swing.boldMetal", false)
        createAndShowGUI()
      }
    })
  }
}
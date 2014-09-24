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
import scala.swing.event.EditDone

/* 
 * Tutorials: How to Use Text Fields
 * http://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
 * How to Use Text Areas
 * http://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextDemoProject/src/components/TextDemo.java
 * 
 * TextDemo.scala requires no other files.
 */
class TextDemo extends GridBagPanel {
  private val newline = "\n"
  val textField = new TextField(20)
  val textArea = new TextArea(5, 20) {
    editable = false
  }
  val scrollPane = new ScrollPane(textArea)
  
  //Add Components to this panel.
  val c: Constraints = new Constraints()

  c.gridwidth = java.awt.GridBagConstraints.REMAINDER
  c.fill = GridBagPanel.Fill.Horizontal
  layout(textField) = c
  c.fill = GridBagPanel.Fill.Both
  c.weightx = 1.0
  c.weighty = 1.0
  layout(scrollPane) = c

  listenTo(textField)
  reactions += {
    case EditDone(`textField`) =>
      textArea.text += textField.text + newline
      textField.selectAll()
      //Make sure the new text is visible, even if there
      //was a selection in the text area.
      textArea.caret.position = textArea.peer.getDocument().getLength()
  }
}

object TextDemo {
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event dispatch thread.
   */
  def createAndShowGUI(): Unit = {
    val frame = new Frame() {
      title = "TextDemo"
      //Create and set up the content pane.
      val newContentPane = new TextDemo()
      contents = newContentPane
      // Display the window
      pack()
      visible = true
      override def closeOperation() = {
        sys.exit(0)
      }
    }
  }

  //The standard main method.
  def main(args: Array[String]): Unit = {
    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = {
        createAndShowGUI()
      }
    });
  }
}
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
import scala.swing.event.SelectionChanged
import java.awt.{ Dimension, Font }
import javax.swing.BorderFactory

/*
 * Tutorial: How to Use Combo Boxes
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html]]
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ComboBoxDemoProject/src/components/ComboBoxDemo.java
 */
class ComboBoxDemo extends BorderPanel {

  val petStrings: Array[String] = Array("Bird", "Cat", "Dog", "Rabbit", "Pig")

  //Create the combo box, select the item at index 4.
  //Indices start at 0, so 4 specifies the pig.
  val petList = new ComboBox[String](petStrings) {
    selection.item = petStrings(4)
  }

  //Set up the picture label.
  val imgIcon = ComboBoxDemo.createImageIcon(s"/scala/swing/examples/tutorials/images/${petList.selection.item}.gif")

  val picture = new Label() {
    icon = imgIcon.getOrElse( Swing.EmptyIcon )
    font = font.deriveFont(Font.ITALIC)
    horizontalAlignment = Alignment.Center
    //The preferred size is hard-coded to be the width of the
    //widest image and the height of the tallest image + the border.
    //A real program would compute this.
    preferredSize = new Dimension(177, 122 + 10)
  }
  updateLabel(petList.selection.item)

  layout(petList) = BorderPanel.Position.East
  layout(picture) = BorderPanel.Position.Center
  border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

  listenTo(petList.selection)
  reactions += {
    case SelectionChanged(`petList`) => updateLabel(petList.selection.item)
  }

  def updateLabel(s: String): Unit = {
    val ic = ComboBoxDemo.createImageIcon(s"/scala/swing/examples/tutorials/images/$s.gif")
    picture.icon = imgIcon.getOrElse( Swing.EmptyIcon )
  }
}

object ComboBoxDemo extends SimpleSwingApplication {

  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String ): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "ComboBoxDemo"
    //Create and set up the content pane.
    contents = new ComboBoxDemo()
  }
}
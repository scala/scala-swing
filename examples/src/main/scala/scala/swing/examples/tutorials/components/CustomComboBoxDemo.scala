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

import java.awt.{Dimension, Font}
import javax.swing.ImageIcon

import scala.swing._

/**
 * Tutorial: How to Use Combo Boxes
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html]]
 *
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/CustomComboBoxDemoProject/src/components/CustomComboBoxDemo.java]]
 *
 * CustomComboBoxDemo.scala uses the following files:
 *   /scala/swing/examples/tutorials/images/Bird.gif
 *   /scala/swing/examples/tutorials/images/Cat.gif
 *   /scala/swing/examples/tutorials/images/Dog.gif
 *   /scala/swing/examples/tutorials/images/Rabbit.gif
 *   /scala/swing/examples/tutorials/images/Pig.gif
 */
class CustomComboBoxDemo extends BorderPanel {
  val petStrings: Array[String] = Array("Bird", "Cat", "Dog", "Rabbit", "Pig")
  //  val images: Array[Option[ImageIcon]] = new Array[Option[ImageIcon]](petStrings.length)
  val intArray: Array[Int] = (0 until petStrings.length ).toArray
  /*
   * Despite its use of EmptyBorder, this panel makes a fine content
   * pane because the empty border just increases the panel's size
   * and is "painted" on top of the panel's normal background.  In
   * other words, the JPanel fills its entire background if it's
   * opaque (which it is by default); adding a border doesn't change
   * that.
   */

  val images:Array[Option[ImageIcon]]  = petStrings.map( pet => {
    val oImage = CustomComboBoxDemo.createImageIcon(s"/scala/swing/examples/tutorials/images/$pet.gif")
    oImage.map( img =>  {img.setDescription(pet); img} )
  })

  //Create the combo box.
  val petList = new ComboBox[Int](intArray) {
    renderer = new ComboBoxRenderer()
    preferredSize = new Dimension(200, 130)
    maximumRowCount = 3
  }

  //Lay out the demo.
  layout(petList) = BorderPanel.Position.Center
  border = Swing.EmptyBorder(20, 20, 20, 20)

  class ComboBoxRenderer extends ListView.AbstractRenderer[Int, Label](new Label("")) {
    var uhOhFont: Option[Font] = None
    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    def configure( listMe: ListView[_], isSelected: Boolean, cellHasFocus: Boolean, a: Int, index: Int): Unit = {
      //Set the icon and text.  If icon was null, say so.
      images(a) match {
        case Some( icon ) =>
          component.icon = icon
          component.text = petStrings(a)
          component.font = listMe.font
        case None =>  setUhOhText( s"${petStrings(a)} (no image available)",  listMe.font)
      }
    }

    //Set the font and text when no image was found.
    def setUhOhText(uhOhText: String, normalFont: Font): Unit = {
      if (!uhOhFont.isDefined) { //lazily create this font
        uhOhFont = Some(normalFont.deriveFont(Font.ITALIC))
      }
      component.font = uhOhFont.getOrElse( normalFont )
      component.text = uhOhText
    }
  }
}

object CustomComboBoxDemo extends SimpleSwingApplication {

  def createImageIcon(path: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "CustomComboBoxDemo"

    //Create and set up the content pane.
    val newContentPane = new CustomComboBoxDemo()
    newContentPane.opaque = true
    contents = newContentPane
  }

}
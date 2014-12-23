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
import scala.swing.event.Key
import java.awt.Font


/**
 * Tutorial: How to Use Buttons, Check Boxes, and Radio Buttons
 * http://docs.oracle.com/javase/tutorial/uiswing/components/button.html
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/CheckBoxDemoProject/src/components/CheckBoxDemo.java]]
 *
 * CheckBoxDemo.scala requires 16 image files in the /scala/swing/examples/tutorials/images/geek
 * directory: 
 * geek-----.gif, geek-c---.gif, geek--g--.gif, geek---h-.gif, geek----t.gif,
 * geek-cg--.gif, ..., geek-cght.gif.
 */
class CheckboxDemo extends BorderPanel {
     /**
     * Four accessory choices provide for 16 different
     * combinations. The image for each combination is
     * contained in a separate image file whose name indicates
     * the accessories. The filenames are "geek-XXXX.gif"
     * where XXXX can be one of the following 16 choices.
     * The "choices" StringBuffer contains the string that
     * indicates the current selection and is used to generate
     * the file name of the image to display.
 
       ----             //zero accessories
 
       c---             //one accessory
       -g--
       --h-
       ---t
 
       cg--             //two accessories
       c-h-
       c--t
       -gh-
       -g-t
       --ht
 
       -ght             //three accessories
       c-ht
       cg-t
       cgh-
 
       cght             //all accessories
     */

  //Create the check boxes.
  val chinButton = new CheckBox("Chin") {
    mnemonic = Key.C // .VK_C
    selected = true
  }

  val glassesButton = new CheckBox("Glasses") {
    mnemonic = Key.G
    selected = true
  }

  val hairButton = new CheckBox("Hair") {
    mnemonic = Key.H
    selected = true
  }

  val teethButton = new CheckBox("Teeth") {
    mnemonic = Key.T
    selected = true
  }

  //Indicates what's on the geek.
  val choices: StringBuffer = new StringBuffer("cght")

  //Set up the picture label
  val pictureLabel = new Label()
  pictureLabel.font = pictureLabel.font.deriveFont(Font.ITALIC)
  updatePicture()

  //Put the check boxes in a column in a panel
  val checkPanel: GridPanel = new GridPanel(0, 1) {
    contents += chinButton
    contents += glassesButton
    contents += hairButton
    contents += teethButton
  }

  layout(checkPanel) = BorderPanel.Position.East
  layout(pictureLabel) = BorderPanel.Position.Center
  border = Swing.EmptyBorder(20, 20, 20, 20)

  //Register a listener for the check boxes.
  listenTo(chinButton)
  listenTo(glassesButton)
  listenTo(hairButton)
  listenTo(teethButton)


  def changeState( idx:Int, checkBox: CheckBox, label:Char ): Unit = {
    choices.setCharAt(idx, if (checkBox.selected) label else '-' )
    updatePicture()
  }

  reactions += {
    case ButtonClicked(`chinButton`) => changeState(0, chinButton, 'c')
    case ButtonClicked(`glassesButton`) => changeState(1, glassesButton, 'g')
    case ButtonClicked(`hairButton`) => changeState(2, hairButton, 'h')
    case ButtonClicked(`teethButton`) => changeState(3, teethButton, 't')
  }

  def updatePicture(): Unit = {
    pictureLabel.tooltip = choices.toString

    //Get the icon corresponding to the image.
    CheckboxDemo.createImageIcon( s"/scala/swing/examples/tutorials/images/geek/geek-$choices.gif") match {
      case Some( icon ) =>
        pictureLabel.icon = icon
        pictureLabel.text = null
      case None =>
        pictureLabel.icon = Swing.EmptyIcon
        pictureLabel.text = "Missing Image"

    }
  }
}

object CheckboxDemo extends SimpleSwingApplication {
  /** Returns an ImageIcon, or None if the path was invalid. */
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] = {
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))
  }
  lazy val top = new MainFrame() {
    title = "CheckboxDemo"
    //Create and set up the content pane.
    contents = new CheckboxDemo()
  }
}
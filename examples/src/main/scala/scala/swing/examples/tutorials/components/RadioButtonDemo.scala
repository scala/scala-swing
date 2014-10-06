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

import java.awt.Dimension
import scala.swing._
import scala.swing.event.{ ButtonClicked, Key }
import javax.swing.ImageIcon

/**
 * Tutorial: How to Use Buttons, Check Boxes, and Radio Buttons
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/button.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/RadioButtonDemoProject/src/components/RadioButtonDemo.java]]
 *
 * RadioButtonDemo.scala requires these files:
 *   /scala/swing/examples/tutorials/images/Bird.gif
 *   /scala/swing/examples/tutorials/images/Cat.gif
 *   /scala/swing/examples/tutorials/images/Dog.gif
 *   /scala/swing/examples/tutorials/images/Rabbit.gif
 *   /scala/swing/examples/tutorials/images/Pig.gif
 */
class RadioButtonDemo extends BorderPanel {
  val birdString = "Bird"
  val catString = "Cat"
  val dogString = "Dog"
  val rabbitString = "Rabbit"
  val pigString = "Pig"

  //Create the radio buttons.
  val birdButton = new RadioButton(birdString) {
    mnemonic = Key.B
    selected = true
  }
  val catButton = new RadioButton(catString) {
    mnemonic = Key.C
    selected = false
  }
  val dogButton = new RadioButton(dogString) {
    mnemonic = Key.D
    selected = false
  }
  val rabbitButton = new RadioButton(rabbitString) {
    mnemonic = Key.R
    selected = false
  }
  val pigButton = new RadioButton(pigString) {
    mnemonic = Key.P
    selected = false
  }

  //Group the radio buttons.
  val group = new ButtonGroup() {
    buttons ++= Seq(birdButton, catButton, dogButton, rabbitButton, pigButton)
  }

  //Set up the picture label.
  val imgIcon = RadioButtonDemo.createImageIcon(s"/scala/swing/examples/tutorials/images/$birdString.gif")
  val picture = new Label() {
    icon = imgIcon.get
    //The preferred size is hard-coded to be the width of the
    //widest image and the height of the tallest image.
    //A real program would compute this.
    preferredSize = new Dimension(177, 122)
  }

  //Put the radio buttons in a column in a panel.
  val radioPanel = new GridPanel(0, 1) {
    contents ++= Seq(birdButton, catButton, dogButton, rabbitButton, pigButton)
  }

  layout(radioPanel) = BorderPanel.Position.East
  layout(picture) = BorderPanel.Position.Center
  border = Swing.EmptyBorder(20, 20, 20, 20)

  listenTo(birdButton)
  listenTo(catButton)
  listenTo(dogButton)
  listenTo(rabbitButton)
  listenTo(pigButton)

  reactions += {
    case ButtonClicked(button) => picture.icon = getPictureIcon(button.text)
  }

  def getPictureIcon(gifName: String): ImageIcon = {
    RadioButtonDemo.createImageIcon("/scala/swing/examples/tutorials/images/" + gifName + ".gif").get
  }
}

object RadioButtonDemo extends SimpleSwingApplication {

  def createImageIcon(path: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "RadioButtonDemo"
    contents = new RadioButtonDemo()
  }
}
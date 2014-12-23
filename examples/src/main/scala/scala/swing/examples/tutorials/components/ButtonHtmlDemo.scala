/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
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
import java.awt.Font

/*
 * Tutorial: How to Use Buttons, Check Boxes, and Radio Buttons
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/button.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ButtonHtmlDemoProject/src/components/ButtonHtmlDemo.java]]
 *
 * ButtonHtmlDemo.scala requires the following files:
 *   /scala/swing/examples/tutorials/images/right.gif
 *   /scala/swing/examples/tutorials/images/middle.gif
 *   /scala/swing/examples/tutorials/images/left.gif
 */
class ButtonHtmlDemo extends FlowPanel {
  val leftButtonIcon = createImageIcon("/scala/swing/examples/tutorials/images/right.gif").get
  val middleButtonIcon = createImageIcon("/scala/swing/examples/tutorials/images/middle.gif").get
  val rightButtonIcon = createImageIcon("/scala/swing/examples/tutorials/images/left.gif").get
  //
  val disable: Button = new Button("<html><center><b><u>D</u>isable</b><br>" +
                         "<font color=#ffffdd>middle button</font>") {
    icon = leftButtonIcon
    verticalTextPosition = Alignment.Center
    horizontalTextPosition = Alignment.Leading
    mnemonic = event.Key.D
    tooltip = "Click this button to disable the middle button."
    font = font.deriveFont(Font.PLAIN)
  }
  disable.font = font
  //
  val middle: Button = new Button("Middle button") {
    icon = middleButtonIcon
    verticalTextPosition = Alignment.Bottom
    horizontalTextPosition = Alignment.Center
    mnemonic = event.Key.M
    tooltip = "This middle button does nothing when you click it."
    font = disable.font
  }
  //
  val enable: Button = new Button("<html><center><b><u>E</u>nable</b><br>" +
                         "<font color=#ffffdd>middle button</font>") {
    icon = rightButtonIcon
    enabled = false
    mnemonic = event.Key.E
    tooltip = "Click this button to enable the middle button."
    font = disable.font
  }

  contents += disable
  contents += middle
  contents += enable

  listenTo(disable)
  listenTo(enable)

  reactions += {
    case ButtonClicked(`enable`) => enableMiddle
    case ButtonClicked(`disable`) => disableMiddle
  }

  def enableMiddle(): Unit = {
    enable.enabled = false
    middle.enabled = true
    disable.enabled = true
  }
  def disableMiddle(): Unit = {
    enable.enabled = true
    middle.enabled = false
    disable.enabled = false
  }
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] = {
    val imgURL: java.net.URL = getClass().getResource(path)
    val img: Option[javax.swing.ImageIcon] = if (imgURL != null) {
      Some(Swing.Icon(imgURL))
    } else {
      None
    }
    img
  }

}

object ButtonHtmlDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "ButtonHtmlDemo"
    //Create and set up the content pane.
    contents = new ButtonHtmlDemo()
  }
}
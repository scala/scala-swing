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
import javax.swing.{ Box, BoxLayout, JDialog, JFrame, ImageIcon, UIManager }
import java.awt.{ BorderLayout, Color, Component, Dimension, Graphics, Image, Point, Toolkit }
import java.awt.image.BufferedImage
import java.net.URL

/*
 * Tutorial: How to Make Frames (Main Windows)
 * http://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemo2Project/src/components/FrameDemo2.java
 *
 * FrameDemo2.scala shows off the window decoration features added in
 * 1.4, plus some window positioning code and (optionally)
 * setIconImage. It uses the file /scala/swing/examples/tutorials/images/FD.jpg.
 */
class FrameDemo2 {
  private var lastLocation: Point = null
  private var defaultButton: Button = null
  //  private val maxX = 500
  //  private val maxY = 500
  //constants for action commands
  val NO_DECORATIONS = "no_dec";
  val LF_DECORATIONS = "laf_dec";
  val WS_DECORATIONS = "ws_dec";
  val CREATE_WINDOW = "new_win";
  val DEFAULT_ICON = "def_icon";
  val FILE_ICON = "file_icon";
  val PAINT_ICON = "paint_icon";

  //true if the next frame created should have no window decorations
  var noDecorations = false;

  //true if the next frame created should have setIconImage called
  var specifyIcon = false;

  //true if the next frame created should have a custom painted icon
  var createIcon = false;

  val screenSize: Dimension = Toolkit.getDefaultToolkit().getScreenSize()
  val maxX = screenSize.width - 50
  val maxY = screenSize.height - 50

  //Create a new MyFrame object and show it.
  def showNewWindow(): Unit = {
    //Take care of the no window decorations case.
    //NOTE: Unless you really need the functionality
    //provided by JFrame, you would usually use a
    //Window or JWindow instead of an undecorated JFrame.
    val frame: Option[Frame] = if (noDecorations) Some(new MyFrameUndecorated()) else Some(new MyFrame())
    //Set window location.
    if (frame.isDefined) {
      val f = frame.get
      if (lastLocation != null) {
        //Move the window over and down 40 pixels.
        lastLocation.translate(40, 40);
        if ((lastLocation.x > maxX) || (lastLocation.y > maxY)) {
          lastLocation.setLocation(0, 0);
        }
        f.location = lastLocation
      } else {
        lastLocation = f.location
      }

      //Calling setIconImage sets the icon displayed when the window
      //is minimized.  Most window systems (or look and feels, if
      //decorations are provided by the look and feel) also use this
      //icon in the window decorations.
      if (specifyIcon) {
        if (createIcon) {
          //create an icon from scratch
          f.iconImage = FrameDemo2.createFDImage()
        } else {
          //get the icon from a file
          f.iconImage = FrameDemo2.getFDImage().get
        }
      }
    }
  }

  // Create the window-creation controls that go in the main window.
  def createOptionControls(frame: Frame): BoxPanel = {
    val label1 = new Label("Decoration options for subsequently created frames:")
    val bg1 = new ButtonGroup()
    val label2 = new Label("Icon options:")
    val bg2 = new ButtonGroup()

    //Create the buttons
    val rb1 = new RadioButton() {
      text = "Look and feel decorated"
      selected = true
    }
    bg1.buttons += rb1
    //
    val rb2 = new RadioButton() {
      text = "Window system decorated"
    }
    bg1.buttons += rb2
    //
    val rb3 = new RadioButton() {
      text = "No decorations"
    }
    bg1.buttons += rb3
    //
    val rb4 = new RadioButton() {
      text = "Default icon"
      selected = true
    }
    bg2.buttons += rb4
    //
    val rb5 = new RadioButton() {
      text = "Icon from a JPEG file"
    }
    bg2.buttons += rb5
    //
    val rb6 = new RadioButton() {
      text = "Painted icon"
    }
    bg2.buttons += rb6

    //Add everything to a container.
    val box = new BoxPanel(Orientation.Vertical) {
      border = Swing.EmptyBorder(10, 10, 10, 10)
      contents += label1
      contents += Swing.VStrut(5) // spacer
      contents += rb1
      contents += rb2
      contents += rb3
      contents += Swing.VStrut(15)
      contents += label2
      contents += Swing.VStrut(5)
      contents += rb4
      contents += rb5
      contents += rb6
    }

    frame.listenTo(rb1)
    frame.listenTo(rb2)
    frame.listenTo(rb3)
    frame.listenTo(rb4)
    frame.listenTo(rb5)
    frame.listenTo(rb6)
    frame.reactions += {
      case ButtonClicked(`rb1`) =>
        noDecorations = false
        JFrame.setDefaultLookAndFeelDecorated(true)
      case ButtonClicked(`rb2`) =>
        noDecorations = false
        JFrame.setDefaultLookAndFeelDecorated(false)
      case ButtonClicked(`rb3`) =>
        noDecorations = true
        // No need to set the default look and feel decorated property to false.
        // JFrame.setDefaultLookAndFeelDecorated(false)
      case ButtonClicked(`rb4`) => specifyIcon = false
      case ButtonClicked(`rb5`) =>
        specifyIcon = true
        createIcon = false
      case ButtonClicked(`rb6`) =>
        specifyIcon = true
        createIcon = true
    }

    box
  }

  //Create the button that goes in the main window.
  def createButtonPane(frame: Frame): FlowPanel = {
    val button = new Button("New window")
    defaultButton = button

    frame.listenTo(button)
    frame.reactions += {
      case ButtonClicked(`button`) => showNewWindow()
    }

    //Center the button in a panel with some space around it.
    val pane = new FlowPanel() {
      border = Swing.EmptyBorder(5, 5, 5, 5)
      contents += button
    }
    pane
  }

}

class MyFrame extends Frame {
  title = "A window"

  //This button lets you close even an undecorated window.
  val button = new Button("Close window") {
    xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
  }

  //Place the button near the bottom of the window.
  contents = new BoxPanel(Orientation.Vertical) {
    contents += Swing.VGlue
    contents += button
    contents += Swing.VStrut(5)
  }
  //
  listenTo(button)
  reactions += {
    case ButtonClicked(`button`) =>
      visible = false
      dispose()
  }
  preferredSize = new Dimension(150, 150)
  pack()
  visible = true
  override def closeOperation() = {
    close
  }
}

class MyFrameUndecorated extends Frame with RichWindow.Undecorated {
  visible = false
    //This button lets you close even an undecorated window.
  val button = new Button("Close window") {
    xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
  }

  //Place the button near the bottom of the window.
  //Undecorated windows are not supported in scala swing.
  contents = new BoxPanel(Orientation.Vertical) {
    contents += Swing.VGlue
    contents += button
    contents += Swing.VStrut(5)
  }
  //
  listenTo(button)
  reactions += {
    case ButtonClicked(`button`) =>
      visible = false
      dispose()
  }
  preferredSize = new Dimension(150, 150)
  pack()
  visible = true
  override def closeOperation() = {
    close
  }
}

object FrameDemo2 extends SimpleSwingApplication {
  //Creates an icon-worthy Image from scratch.
  def createFDImage(): Image = {
    //Create a 16x16 pixel image.
    val bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB)
    //Draw into it.
    val g: Graphics = bi.getGraphics()
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 15, 15)
    g.setColor(Color.RED)
    g.fillOval(5, 3, 6, 6)

    //Clean up.
    g.dispose()

    //Return it.
    bi
  }

  // Returns an Image Option or None.
  def getFDImage(): Option[Image] = {
    val imgURL: URL = getClass().getResource("/scala/swing/examples/tutorials/images/FD.jpg");
    if (imgURL != null) {
      return Some(new ImageIcon(imgURL).getImage)
    } else {
      return None
    }
  }

  lazy val top: Frame { val demo: FrameDemo2 } = new Frame() {
    title = "FrameDemo2"
    //Use the Java look and feel.  This needs to be done before the frame is created
    //so the companion object FrameDemo2 cannot simply extend SimpleSwingApplcation.
    try {
      UIManager.setLookAndFeel(
        UIManager.getCrossPlatformLookAndFeelClassName());
    } catch {
      case e: Exception => ;
    }
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    //Create and set up the content pane.
    val demo = new FrameDemo2();
  }
  val bp: BorderPanel = new BorderPanel() {
      layout(top.demo.createOptionControls(top)) = BorderPanel.Position.Center
      layout(top.demo.createButtonPane(top)) = BorderPanel.Position.South
  }
  top.contents = bp
}

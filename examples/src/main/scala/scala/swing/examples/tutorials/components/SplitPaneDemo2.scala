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

import java.awt.Font

import scala.swing._
import scala.swing.event.SelectionChanged
import javax.swing.ImageIcon

/**
 * How to Use Split Panes
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemo2Project/src/components/SplitPaneDemo2.java]]
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java]]
 *
 * SplitPaneDemo2.scala uses
 *   SplitPaneDemo.scala
 */
class SplitPaneDemo2 extends MainFrame {
  title = "SplitPaneDemo2"

  //Create an instance of SplitPaneDemo.
  val splitPaneDemo = new SplitPaneDemo()
  val top: SplitPane = splitPaneDemo.splitPane
  val listMe: ListView[String] = splitPaneDemo.imageList
  
  //XXXX: Bug #4131528, borders on nested split panes accumulate.
  //Workaround: Set the border on any split pane within
  //another split pane to null. Components within nested split
  //panes need to have their own border for this to work well.
  top.border = null
  
  //Create a regular old label
  val label = new Label("Click on an image name in the list.") {
    horizontalAlignment = Alignment.Center
  }
  
  //Create a split pane and put "top" (a split pane)
  //and JLabel instance in it.
  //Use Orientation.Horizontal to get a top/bottom split pane.
  val splitPane = new SplitPane(Orientation.Horizontal, top, label) {
    oneTouchExpandable = true
    dividerLocation = 180
  }
  
  //Provide minimum sizes for the two components in the split pane
  top.minimumSize = new Dimension(100, 50)
  label.minimumSize = new Dimension(100, 30)
  
  //Add the split pane to this frame
  contents = splitPane
  
  listenTo(splitPaneDemo.imageList.selection)
  
  reactions += {
    case (e: SelectionChanged) => 
      val theList: ListView[String] = e.source.asInstanceOf[ListView[String]]
      if (!theList.selection.adjusting) {
        if (theList.selection.leadIndex < 0)
          label.text = "Nothing selected."
      } else {
          val index = theList.selection.leadIndex
          label.text = s"Selected image number $index"
      }
  }

}

object SplitPaneDemo2 extends SimpleSwingApplication {
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new SplitPaneDemo2()
}



class SplitPaneDemo extends FlowPanel {
  val imageNames: Array[String] = Array("Bird", "Cat", "Dog", "Rabbit", "Pig",
    "dukeWaveRed", "kathyCosmo", "left", "middle", "right", "stickerface")

  //Create the list of images and put it in a scroll pane.
  val imageList = new ListView[String](imageNames)
  imageList.selection.intervalMode = ListView.IntervalMode.Single
  imageList.selectIndices(0)

  val listScrollPane = new ScrollPane(imageList) {
    minimumSize = new Dimension(100, 50)
  }

  val picture = new Label() {
    horizontalAlignment = Alignment.Center
  }
  picture.font = font.deriveFont(Font.ITALIC)
  val pictureScrollPane = new ScrollPane(picture) {
    minimumSize = new Dimension(400, 200)
  }

  //Create a split pane with the two scroll panes in it.
  //Use Orientation.Vertical to get a left/right split pane
  val splitPane = new SplitPane(Orientation.Vertical, listScrollPane, pictureScrollPane) {
    oneTouchExpandable = true
    dividerLocation = 150
  }

  updateLabel(imageNames(imageList.selection.leadIndex))

  listenTo(imageList.selection)

  reactions += {
    case SelectionChanged(`imageList`) =>
      updateLabel(imageNames(imageList.selection.leadIndex))
  }

  //Renders the selected image
  def updateLabel (name: String): Unit = {
    val icon: Option[ImageIcon] = SplitPaneDemo2.createImageIcon("/scala/swing/examples/tutorials/images/" + name + ".gif")
    if  (icon.isDefined) {
      picture.text = null
      picture.icon = icon.get
    } else {
      picture.text = "Image not found"
      picture.icon = null
    }
  }

}

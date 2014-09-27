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
import java.awt.Font
import java.net.URL
import javax.swing.ImageIcon

/*
 * Tutorial: How to Use Split Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java
 */
//SplitPaneDemo itself is not a visible component.
class SplitPaneDemo extends FlowPanel {
  val imageNames: Array[String] = Array("Bird", "Cat", "Dog", "Rabbit", "Pig",
    "dukeWaveRed", "kathyCosmo", "left", "middle", "right", "stickerface")
  //Create the list of images and put it in a scroll pane.
  val list = new ListView[String](imageNames)
  list.selection.intervalMode = ListView.IntervalMode.Single
  list.selectIndices(0)
  //
  val listScrollPane = new ScrollPane(list)
  val picture = new Label() {
    horizontalAlignment = Alignment.Center
  }
  picture.font = font.deriveFont(Font.ITALIC)
  val pictureScrollPane = new ScrollPane(picture)

  //Create a split pane with the two scroll panes in it.
  val splitPane = new SplitPane(Orientation.Horizontal, listScrollPane, pictureScrollPane) {
    oneTouchExpandable = true
    dividerLocation = 150
  }
  
  // Provide minimum sizes for the two components in the split pane.
  listScrollPane.minimumSize = new Dimension(100, 50)
  pictureScrollPane.minimumSize = new Dimension(400, 200)
  updateLabel(imageNames(list.selection.leadIndex))
  listenTo(list.selection)
  
  reactions += {
    case SelectionChanged(`list`) => 
      updateLabel(imageNames(list.selection.leadIndex))
  }
  
  //Renders the selected image
   def updateLabel (name: String): Unit = {
        val icon: ImageIcon = SplitPaneDemo.createImageIcon("/scala/swing/examples/tutorials/images/" + name + ".gif");
        picture.icon = icon
        if  (icon != null) {
            picture.text = null
        } else {
            picture.text = "Image not found"
        }
    }
   
   //Used by SplitPaneDemo2
    def  getImageList(): ListView[String] = {
        list;
    }
 
    def getSplitPane(): SplitPane = {
        splitPane;
    }
}

object SplitPaneDemo extends SimpleSwingApplication {
  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String): ImageIcon = {
    val imgURL: URL = getClass().getResource(path)
    if (imgURL != null) {
      // scala swing has no mechanism for setting the description.
      new javax.swing.ImageIcon(imgURL)
    } else {
      null
    }
  }

  lazy val top = new MainFrame() {
    title = "SplitPaneDemo"
    contents = new SplitPaneDemo()
  }
}

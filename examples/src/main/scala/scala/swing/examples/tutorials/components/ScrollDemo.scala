
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
import javax.swing.{ImageIcon, ScrollPaneConstants}
import java.awt.{ Color, Dimension, Font }
import java.net.URL
import scala.swing.event.ButtonClicked

/* 
 * Tutorial: How to Use Scroll Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemoProject/src/components/ScrollDemo.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemoProject/src/components/Rule.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemoProject/src/components/Corner.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemoProject/src/components/ScrollablePicture.java
 *
 * ScrollDemo.scala requires these files:
 *   Rule.scala
 *   Corner.scala
 *   ScrollablePicture.scala
 *   /scala/swing/examples/tutorials/images/flyingBee.jpg
 */
class ScrollDemo extends BoxPanel(Orientation.Horizontal) {
  //Get the image to use.
  val bee: ImageIcon = ScrollDemo.createImageIcon("/scala/swing/examples/tutorials/images/flyingBee.jpg")

  //Create the row and column headers.
  val columnView = new Rule(Rule.Horizontal, true);
  val rowView = new Rule(Rule.Vertical, true);
  if (bee != null) {
    columnView.setPreferredWidth(bee.getIconWidth())
    rowView.setPreferredHeight(bee.getIconHeight())
  } else {
    columnView.setPreferredWidth(320)
    rowView.setPreferredHeight(480)
  }

  //Create the corners.
  val isMetric = new ToggleButton("cm") {
    selected = true
    font = new Font("SansSerif", Font.PLAIN, 11)
    margin = new Insets(2, 2, 2, 2)
  }
  val buttonCorner = new FlowPanel() {
    contents += isMetric
  }

  //Set up the scroll pane.
  val picture = new ScrollablePicture(bee, columnView.increment)
  val pictureScrollPane = new ScrollPane(picture) {
    preferredSize = new Dimension(300, 250)
    columnHeaderView = columnView
    rowHeaderView = rowView
  }
  pictureScrollPane.peer.setViewportBorder(Swing.LineBorder(Color.black))
  
  //Set the corners.
        //In theory, to support internationalization you would change
        //UPPER_LEFT_CORNER to UPPER_LEADING_CORNER,
        //LOWER_LEFT_CORNER to LOWER_LEADING_CORNER, and
        //UPPER_RIGHT_CORNER to UPPER_TRAILING_CORNER.  In practice,
        //bug #4467063 makes that impossible (in 1.4, at least).
  pictureScrollPane.peer.setCorner(ScrollPaneConstants.UPPER_LEADING_CORNER, buttonCorner.peer)
  pictureScrollPane.peer.setCorner(ScrollPaneConstants.LOWER_LEADING_CORNER, new Corner())
  pictureScrollPane.peer.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new Corner())
  
  contents += pictureScrollPane
  border = Swing.EmptyBorder(20, 20, 20, 20)
  
  listenTo(isMetric)
  // Original had an itemListener.  Scala Swing makes no provision
  // for item changed events, so use button clicked instead.
  reactions += {
    case ButtonClicked(`isMetric`) => if (isMetric.selected) {
      // Turn it to metric..
      rowView.setIsMetric(true)
      columnView.setIsMetric(true)
    } else {
      // Turn it to inches.
      rowView.setIsMetric(false)
      columnView.setIsMetric(false)
    }
    picture.setMaxUnitIncrement(rowView.increment)
  }
}

object ScrollDemo extends SimpleSwingApplication {
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
    title = "ScrollDemo"
    contents = new ScrollDemo()
  }
}
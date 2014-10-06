
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

//import java.awt._

import java.awt.{Dimension, Toolkit, Color, Font}

import scala.swing._
import javax.swing.{SwingConstants, ImageIcon, ScrollPaneConstants}
import scala.swing.Component
import scala.swing.event.{MouseDragged, Key, MouseMoved, ButtonClicked}

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
  val beeOp: Option[ImageIcon] = ScrollDemo.createImageIcon("/scala/swing/examples/tutorials/images/scala-stairs.jpg")

  //Create the row and column headers.
  val columnView = new Rule(Rule.Horizontal, true)
  val rowView = new Rule(Rule.Vertical, true)

  beeOp match {
    case Some( bee ) =>
      columnView.preferredWidth = bee.getIconWidth
      rowView.preferredHeight = bee.getIconHeight
    case None =>
      columnView.preferredWidth = 320
      rowView.preferredWidth = 480
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
  val picture = new ScrollablePicture(beeOp, columnView.increment)
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
  def createImageIcon(path: String ): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "ScrollDemo"
    contents = new ScrollDemo()
  }
}





class ScrollablePicture(img: Option[ImageIcon], var maxUnitIncrement: Int) extends ScrollPane( new Label("", img.orNull, Alignment.Center))  {

  private var missingPicture = false

  if( img == None) {
    missingPicture = true
//    text = "No picture found."
//    horizontalAlignment = Alignment.Center
    opaque = true
    background = Color.white
  }

  //Let the user scroll by dragging to outside the window.
  peer.setAutoscrolls(true)
  listenTo(mouse.moves)
  reactions += {
    case MouseMoved(source: Component, point: Point, modifiers: Key.Modifiers) =>
      peer.scrollRectToVisible(new Rectangle(point.x, point.y))
    case MouseDragged(source: Component, point: Point, modifiers: Key.Modifiers) =>
      peer.scrollRectToVisible(new Rectangle(point.x, point.y))
  }

  def getPreferredScrollableViewportSize: Dimension =
    if (missingPicture)  new Dimension(320, 480)
    else preferredSize


  def getScrollableUnitIncrement(visibleRect: Rectangle, orientation: Int, direction: Int): Int = {
    //Get the current position.
    val currentPosition =
      if (orientation == SwingConstants.HORIZONTAL) visibleRect.x
      else visibleRect.y


    //Return the number of pixels between currentPosition
    //and the nearest tick mark in the indicated direction.
    if (direction < 0) {
      val newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement
      if (newPosition == 0) maxUnitIncrement else newPosition
    } else {
      ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement -currentPosition
    }
  }

  def getScrollableBlockIncrement(visibleRect: Rectangle, orientation: Int, direction: Int): Int = {
    if (orientation == SwingConstants.HORIZONTAL)
      visibleRect.width - maxUnitIncrement
    else
      visibleRect.height - maxUnitIncrement
  }

  def scrollableTracksViewportWidth: Boolean =  false

  def scrollableTracksViewportHeight: Boolean =  false

  def setMaxUnitIncrement(pixels: Int): Unit = {
    maxUnitIncrement = pixels
  }
}



class Rule(val orientation: Int, var isMetric: Boolean) extends Component {
  val Inch: Int = Toolkit.getDefaultToolkit.getScreenResolution
  val Size = 35

  def units =
    if (isMetric) (Inch.toDouble / 2.54).toInt    // dots per centimeter
    else Inch

  def increment = if (isMetric)  units  else  units / 2

  def setIsMetric(isMetricArg: Boolean): Unit = {
    isMetric = isMetricArg
    repaint()
  }


  //  def setPreferredHeight(ph: Int): Unit = {
  //    preferredSize = new Dimension(Size, ph)
  //  }
  //  def setPreferredWidth(pw: Int): Unit = {
  //    preferredSize = new Dimension(pw, Size)
  //  }


  def preferredHeight = preferredSize.height
  def preferredHeight_=(ph: Int): Unit = preferredSize = new Dimension(Size, ph)

  def preferredWidth = preferredSize.width
  def preferredWidth_=(pw: Int): Unit = preferredSize = new Dimension(pw, Size)


  override def paintComponent(g: Graphics2D): Unit = {
    val drawHere: Rectangle = g.getClipBounds

    // Fill clipping area with dirty brown/orange.
    g.setColor(new Color(230, 163, 4))
    g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height)

    // Do the ruler labels in a small font that's black.
    g.setFont(new Font("SansSerif", Font.PLAIN, 10))
    g.setColor(Color.black)

    // Some vars we need.
    var tickLength = 0
    var text: String = null

    // Use clipping bounds to calculate first and last tick locations.
    var (start, end) =
      if (orientation == Rule.Horizontal)
        ( (drawHere.x / increment) * increment,  (((drawHere.x + drawHere.width) / increment) + 1) * increment )
      else
        ( (drawHere.y / increment) * increment,  (((drawHere.y + drawHere.height) / increment) + 1) * increment )

    // Make a special case of 0 to display the number
    // within the rule and draw a units label.
    if (start == 0) {
      val u = if (isMetric) " cm" else " in"
      text = Integer.toString(0) + u
      tickLength = 10

      if (orientation == Rule.Horizontal) {
        drawText(0, Some(text), (0, Size - 1), (0, Size - tickLength - 1), (2, 21))
      } else {
        drawText(0, Some(text), (Size - 1, 0), (Size - tickLength - 1, 0), (9, 10))
      }
      text = null
      start = increment
    }

    // ticks and labels
    for (i <- start until end by increment) {

      val(tickLength, text ) =
        if(i % units == 0 ) (10, Some(Integer.toString(i / units) ))
        else (7, None)


      if (tickLength != 0) {
        if (orientation == Rule.Horizontal)  drawText(i, text, (i, Size - 1), (i, Size - tickLength - 1), (i - 3, 21))
        else drawText(i, text, (Size - 1, i), (Size - tickLength - 1, i), (9, i + 3))
      }
    }


    def drawText(i:Int, text:Option[String], lineFrom:(Int, Int), lineTo:(Int, Int),  txtLoc:(Int, Int) ): Unit = {
      g.drawLine(lineFrom._1, lineFrom._2, lineTo._1, lineTo._2)
      text.foreach( txt => g.drawString(txt,  txtLoc._1, txtLoc._2))

    }


  }

}

object Rule {
  val Horizontal = 0
  val Vertical = 1
}
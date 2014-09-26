
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
import scala.swing.event.MouseReleased
import javax.swing.SwingUtilities
import java.awt.{Color, Dimension, Graphics2D, Rectangle}

/* 
 * Tutorial: How to Use Scroll Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemo2Project/src/components/ScrollDemo2.java
 *
 * ScrollDemo2.scala requires no other files.
 */
class ScrollDemo2 extends BorderPanel {
  val colors: Array[Color] = Array(
    Color.red, Color.blue, Color.green, Color.orange,
    Color.cyan, Color.magenta, Color.darkGray, Color.yellow)
  var circles = Seq[Rectangle]()

  //Set up the instructions
  val instructionsLeft = new Label("Click left mouse button to place a circle.")
  val instructionsRight = new Label("Click right mouse button to clear drawing area.")
  val instructionPanel = new GridPanel(0, 1) {
    focusable = true
    contents += instructionsLeft
    contents += instructionsRight
  }

  //Set up the drawing area.
  val drawingPane = new DrawingPane() {
    background = Color.white
  }

  //Put the drawing area in a scroll pane.
  val scroller = new ScrollPane(drawingPane) {
    preferredSize = new Dimension(200, 200)
  }
  layout(instructionPanel) = BorderPanel.Position.North
  layout(scroller) = BorderPanel.Position.Center

  /** The component inside the scroll pane. */
  class DrawingPane extends Panel {
//    background = Color.white Definitely not.
    preferredSize = new Dimension(200, 200)
    focusable = true
    val area = new Dimension()
    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g);
      for (i <- 0 until circles.length) {
        val rect: Rectangle = circles(i)
        g.setColor(colors((i % colors.length)))
        g.fillOval(rect.x, rect.y, rect.width, rect.height);
      }
    }
    listenTo(mouse.clicks)
    listenTo(mouse.moves)
    listenTo(keys)
    reactions += {
      case e: MouseReleased =>
        circles = Seq[Rectangle]()
        val W = 100
        val H = 100
        var changed = false
        if (SwingUtilities.isRightMouseButton(e.peer)) {
          //This will clear the graphic objects.
          circles = Seq[Rectangle]()
          area.width = 0;
          area.height = 0;
          changed = true;
        } else {
          val x = if (e.point.x >= W / 2) e.point.x - W / 2 else 0
          val y = if (e.point.y >= H / 2) e.point.y - H / 2 else 0
          val rect = new Rectangle(x, y, W, H)
          circles = circles :+ rect
          drawingPane.peer.scrollRectToVisible(rect)
          changed = x + W + 2 > area.width
          if (changed) {
            area.width = x + W + 2
          }
          if (y + H + 2 > area.height) {
            area.width = y + H + 2
            changed = true
          }
          if (changed) {
            //Update client's preferred size because
            //the area taken up by the graphics has
            //gotten larger or smaller (if cleared).
            preferredSize = area

            //Let the scroll pane know to update itself
            //and its scrollbars.
            revalidate();
          }
        }
        repaint()
    }
  }

}

object ScrollDemo2 extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "ScrollDemo2"
    contents = new ScrollDemo2()
  }
}
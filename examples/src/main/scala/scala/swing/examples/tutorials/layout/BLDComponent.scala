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
package scala.swing.examples.tutorials.layout

import scala.swing._
import scala.swing.event.MousePressed
import java.awt.{ Color, Dimension, Graphics2D }

/**
 * A rectangle that has a fixed maximum size.
 * 
 * Used by BoxLayoutDemo.scala.
 */
class BLDComponent(alignmentX: Float, hue: Float, shortSideSize: Int,
  var restrictMaximumSize: Boolean, val printSize: Boolean, nameArg: String) extends Panel {
  name = nameArg
  xLayoutAlignment = alignmentX
  opaque = true
  val normalHue = Color.getHSBColor(hue, 0.4f, 0.85f)
  preferredSize = new Dimension(shortSideSize * 2, shortSideSize)
  listenTo(mouse.clicks)

  reactions += {
    case MousePressed(_, point: Point, _, _, _) =>
      val alignment = point.getX().toFloat / size.getWidth().toFloat
      // Round to the nearest 1/10th.
      val tmp = Math.round(alignment * 10.0f)
      xLayoutAlignment = tmp.toFloat / 10.0f
      revalidate() /// this GUI needs relayout
      repaint()
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    val width: Int = size.getWidth().toInt
    val height: Int = size.getHeight().toInt
    val alignmentX: Float = xLayoutAlignment.toFloat

    g.setColor(normalHue);
    g.fill3DRect(0, 0, width, height, true);

    /* Draw a vertical white line at the alignment point.*/
    // XXX: This code is probably not the best.
    g.setColor(Color.white);
    val x: Int = (alignmentX * width.toFloat).toInt - 1;
    g.drawLine(x, 0, x, height - 1);

    /* Say what the alignment point is. */
    g.setColor(Color.black);
    g.drawString(alignmentX.toString, 3, height - 3);

    if (printSize) {
      System.out.println("BLDComponent " + name + ": size is "
        + width + "x" + height
        + "; preferred size is "
        + preferredSize.getWidth() + "x"
        + preferredSize.getHeight());
    }
  }

  def getMaximumSize(): Dimension = {
    if (restrictMaximumSize) preferredSize else super.maximumSize
  }

  def setSizeRestriction(restrictSize: Boolean): Unit = {
    restrictMaximumSize = restrictSize
  }
}
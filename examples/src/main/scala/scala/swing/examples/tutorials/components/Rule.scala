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
import java.awt.{ Color, Font, Graphics, Toolkit }

class Rule(val orientation: Int, var isMetric: Boolean) extends Component {
  val Inch: Int = Toolkit.getDefaultToolkit().getScreenResolution()
  val Size = 35
  var units = if (isMetric) {
    // dots per centimeter
    (Inch.toDouble / 2.54).toInt
  } else {
    Inch
  }
  var increment = if (isMetric) { units } else { units / 2 }
  def setIsMetric(isMetricArg: Boolean): Unit = {
    isMetric = isMetricArg
    setIncrementAndUnits()
    repaint()
  }
  def setIncrementAndUnits() {
    if (isMetric) {
      units = (Inch.toDouble / 2.54).toInt
      increment = units
    }
    else {
      units = Inch
      increment = units / 2
    }
  }
  def setPreferredHeight(ph: Int): Unit = {
    preferredSize = new Dimension(Size, ph)
  }
  def setPreferredWidth(pw: Int): Unit = {
    preferredSize = new Dimension(pw, Size)
  }
  def paintComponent(g: Graphics): Unit = {
    val drawHere: Rectangle = g.getClipBounds()

    // Fill clipping area with dirty brown/orange.
    g.setColor(new Color(230, 163, 4))
    g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height)

    // Do the ruler labels in a small font that's black.
    g.setFont(new Font("SansSerif", Font.PLAIN, 10))
    g.setColor(Color.black)

    // Some vars we need.
    var end = 0
    var start = 0
    var tickLength = 0
    var text: String = null

    // Use clipping bounds to calculate first and last tick locations.
    if (orientation == Rule.Horizontal) {
      start = (drawHere.x / increment) * increment;
      end = (((drawHere.x + drawHere.width) / increment) + 1) * increment
    } else {
      start = (drawHere.y / increment) * increment;
      end = (((drawHere.y + drawHere.height) / increment) + 1) * increment
    }

    // Make a special case of 0 to display the number
    // within the rule and draw a units label.
    if (start == 0) {
      val u = if (isMetric) " cm" else " in"
      text = Integer.toString(0) + u
      tickLength = 10
      if (orientation == Rule.Horizontal) {
        g.drawLine(0, Size - 1, 0, Size - tickLength - 1)
        g.drawString(text, 2, 21)
      } else {
        g.drawLine(Size - 1, 0, Size - tickLength - 1, 0)
        g.drawString(text, 9, 10)
      }
      text = null
      start = increment
    }

    // ticks and labels
    for (i <- start until end by increment) {
      if (i % units == 0) {
        tickLength = 10
        text = Integer.toString(i / units)
      } else {
        tickLength = 7
        text = null
      }

      if (tickLength != 0) {
        if (orientation == Rule.Horizontal) {
          g.drawLine(i, Size - 1, i, Size - tickLength - 1)
          if (text != null)
            g.drawString(text, i - 3, 21)
        } else {
          g.drawLine(Size - 1, i, Size - tickLength - 1, i)
          if (text != null)
            g.drawString(text, 9, i + 3)
        }
      }
    }
  }
}

object Rule {
  val Horizontal = 0
  val Vertical = 1
}
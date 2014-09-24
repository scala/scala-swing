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
import scala.swing.event.{ Key, MouseDragged, MouseMoved }
import javax.swing.{ ImageIcon, Scrollable, SwingConstants }
import java.awt.{ Color, Dimension, Rectangle }
import scala.swing.event.MouseMoved

class ScrollablePicture(i: ImageIcon, var maxUnitIncrement: Int)
  extends Label("", i, Alignment.Center) with Scrollable {
  private var missingPicture = false
  if (i == null) {
    missingPicture = true
    text = "No picture found."
    horizontalAlignment = Alignment.Center
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

  def getPreferredScrollableViewportSize(): Dimension = {
    if (missingPicture) {
      new Dimension(320, 480)
    } else {
      preferredSize
    }
  }

  def getScrollableUnitIncrement(visibleRect: Rectangle, orientation: Int, direction: Int): Int = {
    //Get the current position.
    val currentPosition = if (orientation == SwingConstants.HORIZONTAL) {
      visibleRect.x;
    } else {
      visibleRect.y;
    }

    //Return the number of pixels between currentPosition
    //and the nearest tick mark in the indicated direction.
    if (direction < 0) {
      val newPosition = currentPosition -
        (currentPosition / maxUnitIncrement) * maxUnitIncrement
      if (newPosition == 0) maxUnitIncrement else newPosition
    } else {
      ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement
      -currentPosition
    }
  }

  def getScrollableBlockIncrement(visibleRect: Rectangle, orientation: Int, direction: Int): Int = {
    if (orientation == SwingConstants.HORIZONTAL)
      visibleRect.width - maxUnitIncrement
    else
      visibleRect.height - maxUnitIncrement
  }

  def getScrollableTracksViewportWidth(): Boolean = {
    false
  }

  def getScrollableTracksViewportHeight(): Boolean = {
    false
  }

  def setMaxUnitIncrement(pixels: Int): Unit = {
    maxUnitIncrement = pixels
  }
}
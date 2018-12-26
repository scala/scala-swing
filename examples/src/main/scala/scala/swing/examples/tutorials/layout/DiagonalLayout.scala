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

/*
 * 1.2+ version.  Used by CustomLayoutDemo.scala.
 */

import java.awt._

class DiagonalLayout(private var vgap: Int) extends LayoutManager {

  private var minWidth: Int = 0

  private var minHeight: Int = 0

  private var preferredWidth: Int = 0

  private var preferredHeight: Int = 0

  private var sizeUnknown: Boolean = true

  def this() {
    this(5)
  }

  /* Required by LayoutManager. */
  def addLayoutComponent(name: String, comp: Component): Unit = ()

  def removeLayoutComponent(comp: Component): Unit = ()

  private def setSizes(parent: Container): Unit = {
    val nComps = parent.getComponentCount
    var d: Dimension = null
    //Reset preferred/minimum width and height.
    preferredWidth = 0
    preferredHeight = 0
    minWidth = 0
    minHeight = 0
    for (i <- 0 until nComps) {
      val c = parent.getComponent(i)
      if (c.isVisible) {
        d = c.getPreferredSize
        if (i > 0) {
          preferredWidth += d.width / 2
          preferredHeight += vgap
        } else {
          preferredWidth = d.width
        }
        preferredHeight += d.height
        minWidth = Math.max(c.getMinimumSize.width, minWidth)
        minHeight = preferredHeight
      }
    }
  }

  /* Required by LayoutManager. */
  def preferredLayoutSize(parent: Container): Dimension = {
    val dim = new Dimension(0, 0)
    setSizes(parent)
    val insets  = parent.getInsets
    dim.width   = preferredWidth  + insets.left + insets.right
    dim.height  = preferredHeight + insets.top  + insets.bottom
    sizeUnknown = false
    dim
  }

  /* Required by LayoutManager. */
  def minimumLayoutSize(parent: Container): Dimension = {
    val dim = new Dimension(0, 0)
    val insets  = parent.getInsets
    dim.width   = minWidth  + insets.left + insets.right
    dim.height  = minHeight + insets.top  + insets.bottom
    sizeUnknown = false
    dim
  }

  /* Required by LayoutManager. */
    /*
     * This is called when the panel is first displayed,
     * and every time its size changes.
     * Note: You CAN'T assume preferredLayoutSize or
     * minimumLayoutSize will be called -- in the case
     * of applets, at least, they probably won't be.
     */
  def layoutContainer(parent: Container): Unit = {
    val insets          = parent.getInsets
    val maxWidth        = parent.getWidth - (insets.left + insets.right)
    val maxHeight       = parent.getHeight - (insets.top + insets.bottom)
    val nComps          = parent.getComponentCount
    var previousWidth   = 0
    var previousHeight  = 0
    var x               = 0
    var y               = insets.top
    var xFudge          = 0
    var yFudge          = 0
    var oneColumn       = false
    // Go through the components' sizes, if neither
    // preferredLayoutSize nor minimumLayoutSize has
    // been called.
    if (sizeUnknown) {
      setSizes(parent)
    }
    if (maxWidth <= minWidth) {
      oneColumn = true
    }
    if (maxWidth != preferredWidth) {
      xFudge = (maxWidth - preferredWidth) / (nComps - 1)
    }
    if (maxHeight > preferredHeight) {
      yFudge = (maxHeight - preferredHeight) / (nComps - 1)
    }
    for (i <- 0 until nComps) {
      val c = parent.getComponent(i)
      if (c.isVisible) {
        val d = c.getPreferredSize
        // increase x and y, if appropriate
        if (i > 0) {
          if (!oneColumn) {
            x += previousWidth / 2 + xFudge
          }
          y += previousHeight + vgap + yFudge
        }
        // If x is too large,
        if ((!oneColumn) && (x + d.width) > (parent.getWidth - insets.right)) {
          x = parent.getWidth - insets.bottom - d.width
        }
        // If y is too large,
        if ((y + d.height) > (parent.getHeight - insets.bottom)) {
          // do nothing.
          // Another choice would be to do what we do to x.
        }
        // Set the component's size and position.
        c.setBounds(x, y, d.width, d.height)
        previousWidth = d.width
        previousHeight = d.height
      }
    }
  }

  override def toString: String = {
    val str = ""
    getClass.getName + "[vgap=" + vgap + str + "]"
  }
}

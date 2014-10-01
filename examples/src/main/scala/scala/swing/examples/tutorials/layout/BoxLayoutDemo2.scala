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
import scala.swing.event.ButtonClicked
import java.awt.Color

/**
 * Tutorials: How to Use BoxLayout
 * [[http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html]]
 * 
 * Source code references:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BoxLayoutDemo2Project/src/layout/BoxLayoutDemo2.java]]
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BoxLayoutDemo2Project/src/layout/BLDComponent.java]]
 *
 * BoxLayoutDemo2.scala requires BLDComponent.scala.
 */
class BoxLayoutDemo2 extends BorderPanel {
  private val NumComponents = 3
  // Component.LEFT_ALIGNMENT, Component.CENTER_ALIGNMENT, Component.RIGHT_ALIGNMENT
  private val xAlignment = Array(0.0f, 0.5f, 1.0f)
  private val hue = Array(0.0f, 0.33f, 0.67f)
  private var restrictSize = true
  private val sizeIsRandom = false
  private val bldComponent = new Array[BLDComponent](NumComponents)
  val panel = new BoxPanel(Orientation.Vertical)
  //Create the rectangles.
  var shortSideSize = 15
  for (i <- 0 until NumComponents) {
    if (sizeIsRandom) {
      shortSideSize = (30.0 * Math.random()).toInt + 30
    }
    else {
      shortSideSize += 10
    }
    bldComponent(i) = new BLDComponent(xAlignment(i), hue(i),
        shortSideSize, restrictSize, sizeIsRandom, i.toString)
    panel.contents += bldComponent(i).asInstanceOf[Component]
  }
  
  //Create the instructions.
  val label = new Label("Click a rectangle to change its X alignment.")
  val cb = new CheckBox("Restrict maximum rectangle size.") {
    selected = restrictSize
  }
  
  border = Swing.LineBorder(Color.red)
  
  // Use a BoxPanel instead of javax.swing.Box
  val box = new BoxPanel(Orientation.Vertical) {
    contents += label
    contents += cb
  }
  
  layout(panel) = BorderPanel.Position.Center
  layout(box) = BorderPanel.Position.South
  
  listenTo(cb)
  
  reactions += {
    case ButtonClicked(`cb`) => 
      restrictSize = cb.selected
      notifyBldComponents()
  }
  
  def notifyBldComponents(): Unit = {
    for (i <- 0 until NumComponents)
      bldComponent(i).setSizeRestriction(restrictSize)
    bldComponent(0).revalidate
  }
}

object BoxLayoutDemo2 extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "BoxLayoutDemo2"
    contents = new BoxLayoutDemo2()
  }
}
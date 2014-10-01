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
import javax.swing.UIManager
import java.awt.Dimension

/**
 * Tutorials: How to Use GridLayout
 * [[http://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html]]
 *
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridLayoutDemoProject/src/layout/GridLayoutDemo.java]]
 */
class GridLayoutDemo extends BorderPanel {
  private val gapList = Array("0", "10", "15", "20")
  private val maxGap = 20
  val horGapComboBox = new ComboBox(gapList)
  val verGapComboBox = new ComboBox(gapList)
  val applyButton = new Button("Apply gaps")
  val compsToExperiment = new GridPanel(0, 2)
  val controls = new GridPanel(2, 3)
  //Set up components preferred size
  val b = new Button("Just fake button")
  val buttonSize = b.preferredSize
  compsToExperiment.preferredSize =
    new Dimension((buttonSize.getWidth() * 2.5).toInt + maxGap,
      (buttonSize.getHeight() * 3.5).toInt + maxGap * 2)

  //Add buttons to experiment with Grid Layout
  compsToExperiment.contents += new Label("Horizontal gap:")
  compsToExperiment.contents += new Label("Vertical gap:")
  compsToExperiment.contents += new Label(" ")
  compsToExperiment.contents += horGapComboBox
  compsToExperiment.contents += verGapComboBox
  compsToExperiment.contents += applyButton
  layout(compsToExperiment) = BorderPanel.Position.North
  layout(controls) = BorderPanel.Position.South

  listenTo(applyButton)
  reactions += {
    case ButtonClicked(`applyButton`) =>
      //Get the horizontal gap value
      val horGap = horGapComboBox.selection.item
      //Get the vertical gap value
      val verGap = verGapComboBox.selection.item
      //Set up the horizontal gap value
      compsToExperiment.hGap = horGap.toInt
      //Set up the vertical gap value
      compsToExperiment.vGap = verGap.toInt
      //Set up the layout of the buttons
      compsToExperiment.peer.getLayout().layoutContainer(compsToExperiment.peer)
  }
}

object GridLayoutDemo extends SimpleSwingApplication {
  /* Use an appropriate Look and Feel */
  UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel")
  /* Turn off metal's use bold fonts */
  UIManager.put("swing.boldMetal", false)
  //Create and set up the window.
  lazy val top = new MainFrame {
    title = "GridLayoutDemo"
    resizable = false
    contents = new GridLayoutDemo()
  }
}
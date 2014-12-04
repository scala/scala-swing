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
import java.awt.ComponentOrientation
import javax.swing.UIManager

/**
 * Tutorials: How to Use FlowLayout
 * [[http://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/FlowLayoutDemoProject/src/layout/FlowLayoutDemo.java]]
 * 
 * FlowLayoutDemo.scala requires no other files
 */
class FlowLayoutDemo extends BorderPanel {
  val RtoL = "Right to left"
  val LtoR = "Left to right"
  val controls: FlowPanel = new FlowPanel()
  val applyButton = new Button("Apply component orientation")
  val compsToExperiment = new FlowPanel(FlowPanel.Alignment.Trailing)()
  val LtoRbutton = new RadioButton(LtoR) { selected = true }
  val RtoLbutton = new RadioButton(RtoL)
  
  //Add buttons to the experiment layout
  val buttonSeq = Seq[Button](
      new Button("Button 1"),
      new Button("Button 2"),
      new Button("Button 3"),
      new Button("Long Named Button 4"),
      new Button("5") )
  compsToExperiment.contents ++= buttonSeq
  
  //Left to right component orientation is selected by default
  compsToExperiment.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
  
  //Add controls to set up the component orientation in the experiment layout
  val group = new ButtonGroup() {
    buttons += LtoRbutton
    buttons += RtoLbutton
  }
  controls.contents += LtoRbutton
  controls.contents += RtoLbutton
  controls.contents += applyButton
  
  layout(compsToExperiment) = BorderPanel.Position.Center
  layout(controls) = BorderPanel.Position.South
  
  listenTo(applyButton)
  reactions += {
    case ButtonClicked(`applyButton`) =>
      compsToExperiment.componentOrientation = 
        if (LtoRbutton.selected) ComponentOrientation.LEFT_TO_RIGHT
        else ComponentOrientation.RIGHT_TO_LEFT
      compsToExperiment.validate()
      compsToExperiment.repaint()
  }
}

object FlowLayoutDemo extends SimpleSwingApplication {
  /* Use an appropriate Look and Feel */
  //TD UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel")
  /* Turn off metal's use of bold fonts */
  //TD UIManager.put("swing.boldMetal", false)
  lazy val top = new MainFrame() {
    title = "FlowLayoutDemo"
    contents = new FlowLayoutDemo()
  }
}
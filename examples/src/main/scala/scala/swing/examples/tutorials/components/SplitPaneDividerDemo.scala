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
import scala.swing.event.ButtonClicked
import java.awt.Font
import java.net.URL
import javax.swing.ImageIcon

/*
 * How to Use Split Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDividerDemoProject/src/components/SplitPaneDividerDemo.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDividerDemoProject/src/components/SizeDisplayer.java
 * 
 * SplitPaneDividerDemo.scala requires the following files:
 *   SizeDisplayer.scala
 *   /scala/swing/examples/tutorials/images/Cat.gif
 *   /scala/swing/examples/tutorials/images/Dog.gif
 */
class SplitPaneDividerDemo extends BorderPanel {
  font = new Font("Serif", Font.ITALIC, 24)
  val icon1 = SplitPaneDividerDemo.createImageIcon("/scala/swing/examples/tutorials/images/Cat.gif")
  val sd1 = new SizeDisplayer("left", icon1) {
    font = font
  }
  sd1.setMinimumSize(new Dimension(30,30))
  
  val icon2 = SplitPaneDividerDemo.createImageIcon("/scala/swing/examples/tutorials/images/Dog.gif")
  val sd2 = new SizeDisplayer("right", icon2) {
    font = font
  }
  sd2.setMinimumSize(new Dimension(30,30))
  
  val splitPane = new SplitPane(Orientation.Horizontal, sd1, sd2) {
    resizeWeight = 0.5
    oneTouchExpandable = true
    continuousLayout = true
  }

  layout(splitPane) = BorderPanel.Position.Center
  layout(createControlPanel) = BorderPanel.Position.South

  private def createControlPanel: Component = { 
    val panel = new FlowPanel()
    val reset = new Button("Reset")
    panel.contents += reset
    
    listenTo(reset)
    reactions += {
      case ButtonClicked(`reset`) => splitPane.resetToPreferredSizes()
    }
    return panel
  }
}

object SplitPaneDividerDemo extends SimpleSwingApplication {
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
    title = "SplitPaneDividerDemo"
    contents = new SplitPaneDividerDemo()
  }
}


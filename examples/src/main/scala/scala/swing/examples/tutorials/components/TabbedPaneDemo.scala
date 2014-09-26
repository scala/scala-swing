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
import java.net.URL
import javax.swing.ImageIcon

/*
 * How to Use Split Panes
 * http://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TabbedPaneDemoProject/src/components/TabbedPaneDemo.java
 * 
 * TabbedPaneDemo.scala requires one additional file:
 *   /scala/swing/examples/tutorials/images/middle.gif.
 */
class TabbedPaneDemo extends GridPanel(1, 1) {
  val tabbedPane = new TabbedPane()
  val icon1: ImageIcon = TabbedPaneDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif")
  val panel1: Panel = makeTextPanel("Panel #1")
  tabbedPane.pages += new TabbedPane.Page("Tab 1", panel1, "Does nothing")
  tabbedPane.pages(0).mnemonic = 1
  val panel2: Panel = makeTextPanel("Panel #2")
  tabbedPane.pages += new TabbedPane.Page("Tab 2", panel2, "Does twice as much nothing")
  tabbedPane.pages(1).mnemonic = 2
  val panel3: Panel = makeTextPanel("Panel #3")
  tabbedPane.pages += new TabbedPane.Page("Tab 3", panel3, "Still does nothing")
  tabbedPane.pages(2).mnemonic = 3
  val panel4: Panel = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).")
  panel4.preferredSize = new Dimension(410, 50)
  tabbedPane.pages += new TabbedPane.Page("Tab 4", panel4, "Does nothing at all")
  tabbedPane.pages(3).mnemonic = 4
  
  //Add the tabbed pane to this panel.
  contents += tabbedPane
  
  //The following line enables to use scrolling tabs.
  tabbedPane.tabLayoutPolicy = TabbedPane.Layout.Scroll
  
  def makeTextPanel(text: String): Panel = {
    val panel = new FlowPanel()
    panel.peer.setDoubleBuffered(false)
    val filler = new Label(text) {
      horizontalAlignment = Alignment.Center
      }
    panel.contents += filler
    panel
  }
}

object TabbedPaneDemo extends SimpleSwingApplication  {
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
    title = "TabbedPaneDemo"
    contents = new TabbedPaneDemo()
  }
}


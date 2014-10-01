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
import javax.swing.{ ImageIcon }
import java.awt.{ Color, Dimension }
import java.net.URL

/**
 * Tutorials: How to Use BoxAlignment
 * [[http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html]]
 *
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BoxAlignmentDemoProject/src/layout/BoxAlignmentDemo.java]]
 *
 * BoxAlignmentDemo.scala requires the following files:
 *   scala/swing/examples/tutorials/images/middle.gif
 *   scala/swing/examples/tutorials/images/geek-cght.gif
 *
 * This demo shows how to specify alignments when you're using
 * a BoxLayout for components with maximum sizes and different
 * default alignments.
 */
class BoxAlignmentDemo extends BorderPanel {

  val buttonRow = new FlowPanel() {
    contents += createButtonRow(false)
    contents += createButtonRow(true)
  }
  val labelAndComponent = new FlowPanel() {
    contents += createLabelAndComponent(false)
    contents += createLabelAndComponent(true)
  }
  val buttonAndComponent = new FlowPanel() {
    contents += createYAlignmentExample(false)
    contents += createYAlignmentExample(true)
  }
  val tabbedPane = new TabbedPane()
  tabbedPane.pages += new TabbedPane.Page("Altering alignments", buttonRow)
  tabbedPane.pages += new TabbedPane.Page("X alignment mismatch", labelAndComponent)
  tabbedPane.pages += new TabbedPane.Page("Y alignment mismatch", buttonAndComponent)
  //Add tabbedPane to this panel
  layout(tabbedPane) = BorderPanel.Position.Center

  def createButtonRow(changeAlignment: Boolean): BoxPanel = {
    val button1 = new Button("A Button") {
      icon = BoxAlignmentDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif").get
      verticalTextPosition = Alignment.Bottom
      horizontalTextPosition = Alignment.Center
    }
    val button2 = new Button("Another Button") {
      icon = BoxAlignmentDemo.createImageIcon("/scala/swing/examples/tutorials/images/geek-cght.gif").get
      verticalTextPosition = Alignment.Bottom
      horizontalTextPosition = Alignment.Center
    }
    val title = if (changeAlignment) "Desired" else "Default"
    if (changeAlignment) {
      button1.yLayoutAlignment = 1.0f // Bottom
      button2.yLayoutAlignment = 1.0f // Bottom
    }
    val panel = new BoxPanel(Orientation.Horizontal) {
      border = Swing.TitledBorder(Swing.EmptyBorder, title)
      contents += button1
      contents += button2
    }
    panel
  }

  def createLabelAndComponent(doItRight: Boolean): BoxPanel = {
    val dSize = new Dimension(150, 100)
    val component = new FlowPanel() {
      maximumSize = dSize
      preferredSize = dSize
      minimumSize = dSize
      border = Swing.TitledBorder(Swing.LineBorder(Color.black), "A Panel")
    }
    val title = if (doItRight) "Matched" else "Mismatched"
    val paneBorder = Swing.TitledBorder(Swing.EmptyBorder, title)
    paneBorder.setTitleJustification(javax.swing.border.TitledBorder.CENTER)
    paneBorder.setTitlePosition(javax.swing.border.TitledBorder.BELOW_TOP)
    paneBorder.setTitleColor(Color.black)
    val label = new Label("This is a Label")
    if (doItRight) label.xLayoutAlignment = 0.5f // Center
    val pane = new BoxPanel(Orientation.Vertical) {
      border = paneBorder
      contents += label
      contents += component
    }
    pane
  }

  def createYAlignmentExample(doItRight: Boolean): BoxPanel = {
    val dSize1 = new Dimension(150, 100)
    val title = if (doItRight) "Matched" else "Mismatched"
    val componentBorder = Swing.TitledBorder(Swing.LineBorder(Color.black), "A Panel")
    componentBorder.setTitleJustification(javax.swing.border.TitledBorder.CENTER)
    componentBorder.setTitlePosition(javax.swing.border.TitledBorder.BELOW_TOP)
    componentBorder.setTitleColor(Color.black)
    val component1 = new FlowPanel() {
      maximumSize = dSize1
      preferredSize = dSize1
      minimumSize = dSize1
      border = componentBorder
    }
    if (!doItRight) component1.yLayoutAlignment = 0.0f // TOP_ALIGNMENT

    val dSize2 = new Dimension(100, 50)
    val component2 = new FlowPanel() {
      maximumSize = dSize2
      preferredSize = dSize2
      minimumSize = dSize2
      border = componentBorder
    }
    val paneBorder = Swing.TitledBorder(Swing.EmptyBorder, title)
    val pane = new BoxPanel(Orientation.Horizontal) {
      border = paneBorder
      contents += component1
      contents += component2
    }
    pane
  }
}

object BoxAlignmentDemo extends SimpleSwingApplication {
  /** Returns an ImageIcon option, or None if the path was invalid. */
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] = {
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))
  }

  //Create and set up the window.
  lazy val top = new MainFrame {
    title = "BoxAlignmentDemo"
    contents = new BoxAlignmentDemo()
  }
}
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
import javax.swing.ImageIcon
import java.awt.{ Color, Dimension }
import java.net.URL

/*
 * Tutorial: How to Use Borders
 * http://docs.oracle.com/javase/tutorial/uiswing/components/border.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/BorderDemoProject/src/components/BorderDemo.java
 *
 * BorderDemo.scala requires the following file:
 *    scala/swing/examples/tutorials/images/wavy.gif
 */
class BorderDemo extends GridPanel(1, 0) {

  //A border that puts 10 extra pixels at the sides and
  //bottom of each pane.
  val paneEdge = Swing.EmptyBorder(0, 10, 10, 10)
  val blackline = Swing.LineBorder(Color.black);
  val raisedetched = Swing.EtchedBorder(Swing.Raised)
  val loweredetched = Swing.EtchedBorder(Swing.Lowered)
  val raisedbevel = Swing.BeveledBorder(Swing.Raised)
  val loweredbevel = Swing.BeveledBorder(Swing.Lowered)
  val empty = Swing.EmptyBorder

  //First pane: simple borders
  val simpleBorders = new BoxPanel(Orientation.Vertical)
  //The method addCompForBorder in the original Java did not have a useful Scala translation
  //because adding to Panel contents is not the same as adding to the contents of, say,
  //a BoxPanel.
  //AddCompForBorder can't take a third argument that is a Panel because Panel.contents
  //cannot be appended to.
  val label1: Label = new Label("line border", Swing.EmptyIcon, Alignment.Center)
  val comp1: GridPanel = new GridPanel(1, 1) {
    contents += label1
    border = blackline
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp1
  // addCompForBorder(raisedetched, "raised etched border", simpleBorders)
  val label2 = new Label("raised etched border", Swing.EmptyIcon, Alignment.Center)
  val comp2 = new GridPanel(1, 1) {
    contents += label2
    border = raisedetched
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp2
  // addCompForBorder(loweredetched, "lowered etched border", simpleBorders)
  val label3 = new Label("lowered etched border", Swing.EmptyIcon, Alignment.Center)
  val comp3 = new GridPanel(1, 1) {
    contents += label3
    border = loweredetched
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp3
  // addCompForBorder(raisedbevel, "raised bevel border", simpleBorders)
  val label4 = new Label("raised bevel border", Swing.EmptyIcon, Alignment.Center)
  val comp4 = new GridPanel(1, 1) {
    contents += label4
    border = raisedbevel
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp4
  // addCompForBorder(loweredbevel, "lowered bevel border", simpleBorders)
  val label5 = new Label("lowered bevel border", Swing.EmptyIcon, Alignment.Center)
  val comp5 = new GridPanel(1, 1) {
    contents += label5
    border = loweredbevel
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp5
  // addCompForBorder(empty, "empty border", simpleBorders)
  val label6 = new Label("empty border", Swing.EmptyIcon, Alignment.Center)
  val comp6 = new GridPanel(1, 1) {
    contents += label6
    border = empty
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp6

  //Second pane: matte borders
  val matteBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val icon: ImageIcon = BorderDemo.createImageIcon("/scala/swing/examples/tutorials/images/wavy.gif",
    "wavy-line border icon"); //20x22
  val border0 = Swing.MatteBorder(-1, -1, -1, -1, icon)
  if (icon != null) {
    // addCompForBorder(border, "matte border (-1,-1,-1,-1,icon)", matteBorders)
    val label7 = new Label("matte border (-1,-1,-1,-1,icon)", Swing.EmptyIcon, Alignment.Center)
    val comp7 = new GridPanel(1, 1) {
      contents += label7
      border = border0
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp7
  } else {
    // addCompForBorder(border, "matte border (-1,-1,-1,-1,<null-icon>)", matteBorders)
    val label8 = new Label("matte border (-1,-1,-1,-1,<null-icon>)", Swing.EmptyIcon, Alignment.Center)
    val comp8 = new GridPanel(1, 1) {
      contents += label8
      border = border0
    }
  }
  val border1 = Swing.MatteBorder(1, 5, 1, 1, Color.red)
  // addCompForBorder(border1, "matte border (1,5,1,1,Color.red)", matteBorders)
  val label9 = new Label("matte border (1,5,1,1,Color.red)", Swing.EmptyIcon, Alignment.Center)
  val comp9 = new GridPanel(1, 1) {
    contents += label9
    border = border1
  }
  val border2 = Swing.MatteBorder(0, 20, 0, 0, icon)
  if (icon != null) {
    // addCompForBorder(border, "matte border (0,20,0,0,icon)", matteBorders)
    val label10 = new Label("matte border (0,20,0,0,icon)", Swing.EmptyIcon, Alignment.Center)
    val comp10 = new GridPanel(1, 1) {
      contents += label10
      border = border2
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp10
  } else {
    // addCompForBorder(border, "matte border (0,20,0,0,<null-icon>)")
    val label11 = new Label("matte border (0,20,0,0,<null-icon>)", Swing.EmptyIcon, Alignment.Center)
    val comp11 = new GridPanel(1, 1) {
      contents += label11
      border = border2
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp11
  }

  //Third pane: titled borders
  val titledBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val titled1 = Swing.TitledBorder(blackline, "title")
  // addCompForTitledBorder(titled1, "titled line border" + " (centered, default pos.)",
  //  TitledBorder.CENTER,    TitledBorder.DEFAULT_POSITION, titledBorders)
  titled1.setTitleJustification(javax.swing.border.TitledBorder.CENTER)
  titled1.setTitlePosition(javax.swing.border.TitledBorder.DEFAULT_POSITION)
  val label12 = new Label("titled line border (centered, default pos.)", Swing.EmptyIcon, Alignment.Center)
  val comp12 = new GridPanel(1, 1) {
    contents += label12
    border = titled1
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp12
  val titled2 = Swing.TitledBorder(loweredetched, "title")
  // addCompForTitledBorder(titled2, "titled lowered etched border" + " (right just., default pos.)",
  //  TitledBorder.RIGHT, TitledBorder.DEFAULT_POSITION, titledBorders)
  titled2.setTitleJustification(javax.swing.border.TitledBorder.RIGHT)
  titled2.setTitlePosition(javax.swing.border.TitledBorder.DEFAULT_POSITION)
  val label13 = new Label("titled lowered etched border (right just., default pos.)", Swing.EmptyIcon, Alignment.Center)
  val comp13 = new GridPanel(1, 1) {
    contents += label13
    border = titled2
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp13
  val titled3 = Swing.TitledBorder(loweredbevel, "title")
  // addCompForTitledBorder(titled3, "titled lowered bevel border" + " (default just., above top)",
  //  TitledBorder.DEFAULT_JUSTIFICATION,  TitledBorder.ABOVE_TOP, titledBorders)
  titled3.setTitleJustification(javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION)
  titled3.setTitlePosition(javax.swing.border.TitledBorder.ABOVE_TOP)
  val label14 = new Label("titled lowered bevel border (default just., above top)", Swing.EmptyIcon, Alignment.Center)
  val comp14 = new GridPanel(1, 1) {
    contents += label14
    border = titled3
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp14
  val titled4 = Swing.TitledBorder(empty, "title")
  // addCompForTitledBorder(titled4, "titled empty border" + " (default just., bottom)",
  //  TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BOTTOM, titledBorders)
  titled4.setTitleJustification(javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION)
  titled4.setTitlePosition(javax.swing.border.TitledBorder.BOTTOM)
  val label15 = new Label("titled empty border (default just., bottom)", Swing.EmptyIcon, Alignment.Center)
  val comp15 = new GridPanel(1, 1) {
    contents += label15
    border = titled3
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp15

  //Fourth pane: compound borders
  val compoundBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val redline = Swing.LineBorder(Color.red)
  val compound1 = Swing.CompoundBorder(raisedbevel, loweredbevel)
  // addCompForBorder(compound1, "compound border (two bevels)", compoundBorders)
  val label16 = new Label("compound border (two bevels)", Swing.EmptyIcon, Alignment.Center)
  val comp16 = new GridPanel(1, 1) {
    contents += label16
    border = compound1
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp16
  val compound2 = Swing.CompoundBorder(redline, compound1)
  // addCompForBorder(compound2, "compound border (add a red outline)", compoundBorders)
  val label17 = new Label("compound border (add a red outline)", Swing.EmptyIcon, Alignment.Center)
  val comp17 = new GridPanel(1, 1) {
    contents += label17
    border = compound2
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp17
  val titledCompound = Swing.TitledBorder(compound2, "title")
  titledCompound.setTitleJustification(javax.swing.border.TitledBorder.CENTER)
  titledCompound.setTitlePosition(javax.swing.border.TitledBorder.BELOW_BOTTOM)
  // addCompForBorder(titledn, "titled compound border" + " (centered, below bottom)")
  val label18 = new Label("titled compound border (centered, below bottom)", Swing.EmptyIcon, Alignment.Center)
  val comp18 = new GridPanel(1, 1) {
    contents += label18
    border = titledCompound
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp18
  val tabbedPane = new TabbedPane()
  tabbedPane.pages += new TabbedPane.Page("Simple", simpleBorders)
  tabbedPane.pages += new TabbedPane.Page("Matte", matteBorders)
  tabbedPane.pages += new TabbedPane.Page("Titled", titledBorders)
  tabbedPane.pages += new TabbedPane.Page("Compound", compoundBorders)
  tabbedPane.selection.index = 0
  val toolTipText = "<html>Blue Wavy Line border art crew:<br>&nbsp;&nbsp;&nbsp;Bill Pauley<br>&nbsp;&nbsp;&nbsp;Cris St. Aubyn<br>&nbsp;&nbsp;&nbsp;Ben Wronsky<br>&nbsp;&nbsp;&nbsp;Nathan Walrath<br>&nbsp;&nbsp;&nbsp;Tommy Adams, special consultant</html>"
  tabbedPane.peer.setToolTipTextAt(1, toolTipText)
  contents += tabbedPane

  //  def addCompForTitledBorder(border: TitledBorder,
  //    description: String,
  //    justification: Int,
  //    position: Int, container: Panel): Unit = {
  //    border.setTitleJustification(justification)
  //    border.setTitlePosition(position)
  //    addCompForBorder(border, description, container)
  //  }
  //
  //  def addCompForBorder(border: Border,
  //    description: String, container: Panel): (Component, Component) = {
  //    val label: Label = new Label(description, Swing.EmptyIcon, Alignment.Center)
  //    val comp: GridPanel = new GridPanel(1, 1) {
  //      contents += label
  //      border = border
  //    }
  //    container.contents += label
  //    container.contents += comp
  //  }

}

object BorderDemo extends SimpleSwingApplication {

  def createImageIcon(path: String, description: String): ImageIcon = {
    val imgURL: URL = getClass().getResource(path)
    if (imgURL != null) {
      new ImageIcon(imgURL, description)
    } else {
      System.err.println("Couldn't find file: " + path);
      null
    }
  }
  
  lazy val top = new MainFrame() {
    title = "BorderDemo"
    //Create and set up the content pane.
    contents = new BorderDemo();
  }
}
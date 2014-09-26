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
import javax.swing.border.TitledBorder
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
  var label: Label = new Label("line border", Swing.EmptyIcon, Alignment.Center)
  var comp: GridPanel = new GridPanel(1, 1) {
    contents += label
    border = blackline
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp
  // addCompForBorder(raisedetched, "raised etched border", simpleBorders)
  label = new Label("raised etched border", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = raisedetched
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp
  // addCompForBorder(loweredetched, "lowered etched border", simpleBorders)
  label = new Label("lowered etched border", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = loweredetched
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp
  // addCompForBorder(raisedbevel, "raised bevel border", simpleBorders)
  label = new Label("raised bevel border", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = raisedbevel
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp
  // addCompForBorder(loweredbevel, "lowered bevel border", simpleBorders)
  label = new Label("lowered bevel border", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = loweredbevel
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp
  // addCompForBorder(empty, "empty border", simpleBorders)
  label = new Label("empty border", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = empty
  }
  simpleBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  simpleBorders.contents += comp

  //Second pane: matte borders
  val matteBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val icon: ImageIcon = BorderDemo.createImageIcon("/scala/swing/examples/tutorials/images/wavy.gif",
    "wavy-line border icon"); //20x22
  val border0 = Swing.MatteBorder(-1, -1, -1, -1, icon)
  if (icon != null) {
    // addCompForBorder(border, "matte border (-1,-1,-1,-1,icon)", matteBorders)
    label = new Label("matte border (-1,-1,-1,-1,icon)", Swing.EmptyIcon, Alignment.Center)
    comp = new GridPanel(1, 1) {
      contents += label
      border = border0
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp
  } else {
    // addCompForBorder(border, "matte border (-1,-1,-1,-1,<null-icon>)", matteBorders)
    label = new Label("matte border (-1,-1,-1,-1,<null-icon>)", Swing.EmptyIcon, Alignment.Center)
    comp = new GridPanel(1, 1) {
      contents += label
      border = border0
    }
  }
  val border1 = Swing.MatteBorder(1, 5, 1, 1, Color.red)
  // addCompForBorder(border1, "matte border (1,5,1,1,Color.red)", matteBorders)
  label = new Label("matte border (1,5,1,1,Color.red)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = border1
  }
  val border2 = Swing.MatteBorder(0, 20, 0, 0, icon)
  if (icon != null) {
    // addCompForBorder(border, "matte border (0,20,0,0,icon)", matteBorders)
    label = new Label("matte border (0,20,0,0,icon)", Swing.EmptyIcon, Alignment.Center)
    comp = new GridPanel(1, 1) {
      contents += label
      border = border2
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp
  } else {
    // addCompForBorder(border, "matte border (0,20,0,0,<null-icon>)")
    label = new Label("matte border (0,20,0,0,<null-icon>)", Swing.EmptyIcon, Alignment.Center)
    comp = new GridPanel(1, 1) {
      contents += label
      border = border2
    }
    matteBorders.contents += Swing.RigidBox(new Dimension(0, 10))
    matteBorders.contents += comp
  }

  //Third pane: titled borders
  val titledBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val titled1 = Swing.TitledBorder(blackline, "title")
  // addCompForTitledBorder(titled1, "titled line border" + " (centered, default pos.)",
  //  TitledBorder.CENTER,    TitledBorder.DEFAULT_POSITION, titledBorders)
  titled1.setTitleJustification(TitledBorder.CENTER)
  titled1.setTitlePosition(TitledBorder.DEFAULT_POSITION)
  label = new Label("titled line border (centered, default pos.)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = titled1
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp
  val titled2 = Swing.TitledBorder(loweredetched, "title")
  // addCompForTitledBorder(titled2, "titled lowered etched border" + " (right just., default pos.)",
  //  TitledBorder.RIGHT, TitledBorder.DEFAULT_POSITION, titledBorders)
  titled2.setTitleJustification(TitledBorder.RIGHT)
  titled2.setTitlePosition(TitledBorder.DEFAULT_POSITION)
  label = new Label("titled lowered etched border (right just., default pos.)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = titled2
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp
  val titled3 = Swing.TitledBorder(loweredbevel, "title")
  // addCompForTitledBorder(titled3, "titled lowered bevel border" + " (default just., above top)",
  //  TitledBorder.DEFAULT_JUSTIFICATION,  TitledBorder.ABOVE_TOP, titledBorders)
  titled3.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION)
  titled3.setTitlePosition(TitledBorder.ABOVE_TOP)
  label = new Label("titled lowered bevel border (default just., above top)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = titled3
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp
  val titled4 = Swing.TitledBorder(empty, "title")
  // addCompForTitledBorder(titled4, "titled empty border" + " (default just., bottom)",
  //  TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.BOTTOM, titledBorders)
  titled4.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION)
  titled4.setTitlePosition(TitledBorder.BOTTOM)
  label = new Label("titled empty border (default just., bottom)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = titled3
  }
  titledBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  titledBorders.contents += comp

  //Fourth pane: compound borders
  val compoundBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge
  }
  val redline = Swing.LineBorder(Color.red)
  val compound1 = Swing.CompoundBorder(raisedbevel, loweredbevel)
  // addCompForBorder(compound1, "compound border (two bevels)", compoundBorders)
  label = new Label("compound border (two bevels)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = compound1
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp
  val compound2 = Swing.CompoundBorder(redline, compound1)
  // addCompForBorder(compound2, "compound border (add a red outline)", compoundBorders)
  label = new Label("compound border (add a red outline)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = compound2
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp
  val titledCompound = Swing.TitledBorder(compound2, "title")
  titledCompound.setTitleJustification(TitledBorder.CENTER)
  titledCompound.setTitlePosition(TitledBorder.BELOW_BOTTOM)
  // addCompForBorder(titledn, "titled compound border" + " (centered, below bottom)")
  label = new Label("titled compound border (centered, below bottom)", Swing.EmptyIcon, Alignment.Center)
  comp = new GridPanel(1, 1) {
    contents += label
    border = titledCompound
  }
  compoundBorders.contents += Swing.RigidBox(new Dimension(0, 10))
  compoundBorders.contents += comp
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
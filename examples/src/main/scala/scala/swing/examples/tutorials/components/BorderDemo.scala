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
import java.awt.Color

/**
 * Tutorial: How to Use Borders
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/border.html]]
 *
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/BorderDemoProject/src/components/BorderDemo.java]]
 *
 * BorderDemo.scala requires the following file:
 *    scala/swing/examples/tutorials/images/wavy.gif
 */
class BorderDemo extends GridPanel(1, 0) {

  val paneEdge = Swing.EmptyBorder(0, 10, 10, 10)
  val blackline = Swing.LineBorder(Color.black)
  val raisedetched = Swing.EtchedBorder(Swing.Raised)
  val loweredetched = Swing.EtchedBorder(Swing.Lowered)
  val raisedbevel = Swing.BeveledBorder(Swing.Raised)
  val loweredbevel = Swing.BeveledBorder(Swing.Lowered)
  val empty = Swing.EmptyBorder


  def addCompForBorder(bord: javax.swing.border.Border, description: String): Seq[Component] =
    List(Swing.RigidBox(new Dimension(0, 10)), new GridPanel(1, 1) {
      contents += new Label(description, Swing.EmptyIcon, Alignment.Center)
      border = bord
    })

  //First pane: simple borders
  val simpleBorders = new BoxPanel(Orientation.Vertical) {
    contents ++=
      addCompForBorder(blackline, "line border") ++
        addCompForBorder(raisedetched, "raised etched border") ++
        addCompForBorder(loweredetched, "lowered etched border") ++
        addCompForBorder(raisedbevel, "raised bevel border") ++
        addCompForBorder(loweredbevel, "lowered bevel border") ++
        addCompForBorder(empty, "empty border")
  }

  //Second pane: matte borders
  val matteBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge

    val icon = BorderDemo.createImageIcon("/scala/swing/examples/tutorials/images/wavy.gif", "wavy-line border icon")

    contents ++=
      addCompForBorder(
        Swing.MatteBorder(-1, -1, -1, -1, icon.getOrElse(null.asInstanceOf[javax.swing.Icon])),
        "matte border (-1,-1,-1,-1,icon)") ++
        addCompForBorder(
          Swing.MatteBorder(1, 5, 1, 1, Color.red),
          "matte border (1,5,1,1,Color.red)") ++
        addCompForBorder(
          Swing.MatteBorder(0, 20, 0, 0, icon.getOrElse(null.asInstanceOf[javax.swing.Icon])),
          "matte border (0,20,0,0,icon)")
  }

  //Third pane: titled borders
  val titledBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge

    def titleBorder(border: javax.swing.border.Border, justification: Int, position: Int): javax.swing.border.Border = {
      val titled = Swing.TitledBorder(border, "title")
      titled.setTitleJustification(justification)
      titled.setTitlePosition(position)
      titled
    }

    contents ++=
      addCompForBorder(
        titleBorder(blackline, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION),
        "titled line border (centered, default pos.)") ++
        addCompForBorder(
          titleBorder(loweredetched, javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION),
          "titled lowered etched border (right just., default pos.)") ++
        addCompForBorder(
          titleBorder(loweredbevel, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP),
          "titled lowered bevel border (default just., above top)") ++
        addCompForBorder(
          titleBorder(empty, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.BOTTOM),
          "titled empty border (default just., bottom)")
  }

  //Fourth pane: compound borders
  val compoundBorders = new BoxPanel(Orientation.Vertical) {
    border = paneEdge

    val titledCompound = Swing.TitledBorder(Swing.CompoundBorder(Swing.LineBorder(Color.red), Swing.CompoundBorder(Swing.BeveledBorder(Swing.Raised), Swing.BeveledBorder(Swing.Lowered))), "title")
    titledCompound.setTitleJustification(javax.swing.border.TitledBorder.CENTER)
    titledCompound.setTitlePosition(javax.swing.border.TitledBorder.BELOW_BOTTOM)

    contents ++=
      addCompForBorder(
        Swing.CompoundBorder(raisedbevel, loweredbevel),
        "compound border (two bevels)") ++
        addCompForBorder(
          Swing.CompoundBorder(Swing.LineBorder(Color.red), Swing.CompoundBorder(raisedbevel, loweredbevel)),
          "compound border (add a red outline)") ++
        addCompForBorder(
          titledCompound,
          "titled compound border (centered, below bottom)")
  }


  contents += new TabbedPane() {
    tooltip = "<html>Blue Wavy Line border art crew:<br>&nbsp;&nbsp;&nbsp;Bill Pauley<br>&nbsp;&nbsp;&nbsp;Cris St. Aubyn<br>&nbsp;&nbsp;&nbsp;Ben Wronsky<br>&nbsp;&nbsp;&nbsp;Nathan Walrath<br>&nbsp;&nbsp;&nbsp;Tommy Adams, special consultant</html>"

    pages += new TabbedPane.Page("Simple", simpleBorders)
    pages += new TabbedPane.Page("Matte", matteBorders)
    pages += new TabbedPane.Page("Titled", titledBorders)
    pages += new TabbedPane.Page("Compound", compoundBorders)

    selection.index = 0
  }
}

object BorderDemo extends SimpleSwingApplication {
  def createImageIcon(path: String, description: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "BorderDemo"
    //Create and set up the content pane.
    contents = new BorderDemo()
  }
}
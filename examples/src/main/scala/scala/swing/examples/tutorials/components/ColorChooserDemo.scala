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
import scala.swing.Swing.EmptyIcon
import scala.swing.event.ColorChanged
import java.awt.{ Color, Dimension, Font }

/**
 * Tutorial: How to Use Color Choosers
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/colorchooser.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ColorChooserDemoProject/src/components/ColorChooserDemo.java]]
 */
class ColorChooserDemo extends BorderPanel {
  //Set up the banner at the top of the window
  val banner = new Label("Welcome to the Scala-Swing Zone!", EmptyIcon, Alignment.Center) {
    foreground = Color.yellow
    background = Color.blue
    opaque = true
    font = new Font("SansSerif", Font.BOLD, 24)
    preferredSize = new Dimension(100, 65)
  }

  val bannerPanel = new BorderPanel() {
    layout(banner) = BorderPanel.Position.Center
    border = Swing.TitledBorder(Swing.EmptyBorder, "Banner")
  }

  //Set up color chooser for setting text color
  val tcc: ColorChooser = new ColorChooser(banner.foreground) {
    border = Swing.TitledBorder(Swing.EmptyBorder, "Choose Text Color")
    reactions += {
      case ColorChanged(_, c) => banner.foreground = c
    }
  }

  layout(bannerPanel) = BorderPanel.Position.Center
  layout(tcc) = BorderPanel.Position.South
}

object ColorChooserDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "ColorChooserDemo"
    //Create and set up the content pane.
    contents = new ColorChooserDemo()
  }
}

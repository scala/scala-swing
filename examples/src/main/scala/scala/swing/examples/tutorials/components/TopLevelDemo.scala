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

/**
 * Tutorial: Using Top-Level Containers
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TopLevelDemoProject/src/components/TopLevelDemo.java]]
 * 
 * TopLevelDemo.scala requires no other files.
 */
object TopLevelDemo extends SimpleSwingApplication {
    lazy val top = new MainFrame() {
      title = "TopLevelDemo"
        
      //Create the menu bar.  Make it have a green background.
      val greenMenuBar: MenuBar = new MenuBar() {
        opaque = true
        background = new Color(154, 165, 127)
        preferredSize = new Dimension(200, 20)
      }
      
      //Create a yellow label to put in the content pane.
      val yellowlabel = new Label("Hello World") {
        opaque = true
        background = new Color(248, 213, 131)
        preferredSize = new Dimension(200, 180)
      }
      
      //Set the menu bar and add the label to the content pane.
      menuBar = greenMenuBar
      contents = new BorderPanel {
        layout(yellowlabel) = BorderPanel.Position.Center
      }
    }
}
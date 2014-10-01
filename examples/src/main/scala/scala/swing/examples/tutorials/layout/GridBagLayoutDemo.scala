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

/**
 * Tutorials: How to Use GridBagLayout
 * [[http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html]]
 *
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridBagLayoutDemoProject/src/layout/GridBagLayoutDemo.java]]
 * 
 * GridBagLayoutDemo.scala requires no other files.
 */
class GridBagLayoutDemo extends GridBagPanel {
  val shouldFill = true
  val shouldWeightX = true
  val RightToLeft = false
  val c: Constraints = new Constraints()
  //natural height, maximum width
  if (shouldFill) c.fill = GridBagPanel.Fill.Horizontal
  if (shouldWeightX) c.weightx = 0.5
  c.gridx = 0
  c.gridy = 0
  layout(new Button("Button 1")) = c
  
  c.fill = GridBagPanel.Fill.Horizontal
  c.weightx = 0.5
  c.gridx = 1
  c.gridy = 0
  layout(new Button("Button 2")) = c
  
  c.fill = GridBagPanel.Fill.Horizontal
  c.weightx = 0.5
  c.gridx = 2
  c.gridy = 0
  layout(new Button("Button 3")) = c
  
  c.fill = GridBagPanel.Fill.Horizontal
  c.ipady = 40 // make this component tall
  c.weightx = 0.5
  c.gridwidth = 3
  c.gridx = 0
  c.gridy = 1
  layout(new Button("Long-Named Button 4")) = c
  
  c.fill = GridBagPanel.Fill.Horizontal
  c.ipady = 0       //reset to default
  c.weightx = 1.0   //request any extra vertical space
  c.anchor = GridBagPanel.Anchor.PageEnd //bottom of space
  c.insets = new Insets(10, 0, 0, 0) // top padding
  c.gridx = 1
  c.gridwidth = 2
  c.gridy = 22
  layout(new Button("5")) = c
}

object GridBagLayoutDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "GridBagLayoutDemo"
    contents = new GridBagLayoutDemo()
  }
} 
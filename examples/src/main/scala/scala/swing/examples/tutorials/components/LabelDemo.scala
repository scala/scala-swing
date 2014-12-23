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
import javax.swing.UIManager

/**
 * Tutorial: How to Use Labels
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/label.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/LabelDemoProject/src/components/LabelDemo.java]]
 * 
 * LabelDemo.scala needs one other file:
 *   /scala/swing/examples/tutorials/images/middle.gif
 */
class LabelDemo extends GridPanel(3, 1) {
  val icon: Option[ImageIcon] = LabelDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif",
        "a pretty but meaningless splat")
  //Create the first label.
  val label1: Label = new Label("Image and Text", icon.get, Alignment.Center) {
    //Set the position of its text, relative to its icon:
    verticalTextPosition = Alignment.Bottom
    horizontalTextPosition = Alignment.Center
  }
  
  //Create the other labels.
  val label2 = new Label("Text-Only Label")
  val label3 = new Label("", icon.get, Alignment.Center)
  
  //Create tool tips, for the heck of it.
  label1.tooltip = "A label containing both image and text"
  label2.tooltip = "A label containing only text"
  label3.tooltip = "A label containing only an image"
    
  contents += label1
  contents += label2
  contents += label3
}

object LabelDemo extends SimpleSwingApplication {
  //TD UIManager.put("swing.boldMetal", false)
  /** Returns an ImageIcon option, or None if the path was invalid. */
  def createImageIcon(path: String, desc:String ): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "LabelDemo"
    //Create and set up the content pane.
    //TD javax.swing.UIManager.put("swing.boldMetal", false)
    contents = new LabelDemo()
  }
}
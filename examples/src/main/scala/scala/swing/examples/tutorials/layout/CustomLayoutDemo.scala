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

/*
 * Tutorials: Creating a Custom Layout Manager
 * http://docs.oracle.com/javase/tutorial/uiswing/layout/custom.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/CustomLayoutDemoProject/src/layout/CustomLayoutDemo.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/CustomLayoutDemoProject/src/layout/DiagonalLayout.java
 * 
 * CustomLayoutDemo.java requires one other file:
 *   DiagonalLayout.java
 */
class CustomLayoutDemo() extends Panel with SequentialContainer.Wrapper {
  override lazy val peer: javax.swing.JPanel =
    new javax.swing.JPanel(new DiagonalLayout()) with SuperMixin

  private def layoutManager = peer.getLayout.asInstanceOf[DiagonalLayout]

  val b1 = new Button("Button 1")
  val b2 = new Button("Button 2")
  val b3 = new Button("Button 3")
  val b4 = new Button("Button 4")
  val b5 = new Button("Button 5")
  val buttons: Seq[Component] = Seq(b1, b2, b3, b4, b5)
  contents ++= buttons
}

object CustomLayoutDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "CustomLayoutDemo"
    contents = new CustomLayoutDemo()
  }
}

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
 * Tutorial: How to Use Menus
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuGlueDemoProject/src/components/MenuGlueDemo.java]]
 *
 * MenuGlueDemo.scala requires no other files.
 *
 * @author ges
 * @author kwalrath
 */
class MenuGlueDemo extends MainFrame {
  def createMenuBar(): MenuBar = {
    new MenuBar() {
      contents += createMenu("Menu 1")
      contents += createMenu("Menu 2")
      contents += Swing.HGlue
      contents += createMenu("Menu 3")
    }
  }

  def createMenu(title: String): Menu = {
    new Menu(title) {
      contents += new MenuItem(s"Menu item #1 in $title")
      contents += new MenuItem(s"Menu item #2 in $title")
      contents += new MenuItem(s"Menu item #3 in $title")
    }
  }
}

object MenuGlueDemo extends SimpleSwingApplication {
  lazy val top = new MenuGlueDemo() {
    title = "MenuGlueDemo"
    contents = new FlowPanel() {
      contents += createMenuBar()
    }
    size = new Dimension(300, 100)
  }
}

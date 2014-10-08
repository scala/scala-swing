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
package scala.swing.examples.tutorials.misc

import scala.swing._
import scala.swing.event.{ ActionEvent, ButtonClicked, KeyEvent }
import java.awt.Dimension
//import java.awt.event.{ ActionEvent, ActionListener, KeyEvent }
import java.net.URL
import javax.swing.{ ImageIcon, JToolBar }

/** 
 * Tutorials: How to Use Actions
 * [[http://docs.oracle.com/javase/tutorial/uiswing/misc/action.html]]
 * 
 * The graphics in this application require a download of the Java Look and Feel Graphics
 * Repository from Oracle.
 * [[http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-client-419417.html#7520-jlf-1.0-oth-JPR]]
 * Accept the user agreement from the site, unzip, and place the jar file contents in your classpath.
 * 
 * Source code references:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/ActionDemoProject/src/misc/ActionDemo.java]]
 * 
 * ActionDemo.scala requires the Java Look and Feel jar file jlfgr-1_0.jar.
 */
class ActionDemo extends BorderPanel {
  //Create a scrolled text area.
  val textArea = new TextArea(5, 30) {
    editable = false
  }
  val scrollPane = new ScrollPane(textArea)
  preferredSize = new Dimension(450, 150)

  layout(scrollPane) = BorderPanel.Position.Center

  //Create the actions shared by the toolbar and menu.
  val leftAction = new LeftAction()
  class LeftAction extends Action("Go left") {
    override def apply(): Unit = {}
    icon = ActionDemo.createNavigationIcon("Back24").get
    mnemonic = new Integer(java.awt.event.KeyEvent.VK_L)
    toolTip = "This is the left button."
  }
  val middleAction = new Action("Do something") {
    override def apply(): Unit = {}
    icon = ActionDemo.createNavigationIcon("Up24").get
    mnemonic = new Integer(java.awt.event.KeyEvent.VK_M)
    toolTip = "This is the middle button."
  }
  val rightAction = new Action("Go right")  {
    override def apply(): Unit = {}
    icon = ActionDemo.createNavigationIcon("Forward24").get
    mnemonic = new Integer(java.awt.event.KeyEvent.VK_R)
    toolTip = "This is the right button."
  }
  
  def displayResult(actionDescription: String, source: String): Unit = {
    val newline = "\n"
    val s: String = "Action event detected: " +
      actionDescription +
      newline +
      "    Event source: " + source +
      newline;
    textArea.append(s);
  }

  def createMenuBar(): MenuBar = {
    //Create the menu bar.
    val menuBar = new MenuBar()

    //Create the first menu.
    val mainMenu = new Menu("Menu")
    val actions = new Array[Action](3)
    actions(0) = leftAction
    actions(1) = middleAction
    actions(2) = rightAction
    val mi0 =  new MenuItem(actions(0)) { icon = Swing.EmptyIcon }
    val mi1 =  new MenuItem(actions(1)) { icon = Swing.EmptyIcon }
    val mi2 =  new MenuItem(actions(2)) { icon = Swing.EmptyIcon }
    mainMenu.contents += mi0
    mainMenu.contents += mi1
    mainMenu.contents += mi2
    listenTo(mi0)
    listenTo(mi1)
    listenTo(mi2)
    reactions += {
      case ButtonClicked(`mi0`) => displayResult("Action for first button/menu item", "Menu Item 1")
      case ButtonClicked(`mi1`) => displayResult("Action for second button/menu item", "Menu Item 2")
      case ButtonClicked(`mi2`) => displayResult("Action for third button/menu item", "Menu Item 3")
    }
    //Set up the  menu bar.
    menuBar.contents += mainMenu
    menuBar.contents += createAbleMenu()
    menuBar
  }

  def createToolBar(): Unit = {
    val toolBar = new ToolBar()
    //first button
    val button1 = new Button(leftAction) {
      if (icon != null) text = "" // an icon-only button
    }
    val button2 = new Button(middleAction) {
      if (icon != null) text = "" // an icon-only button
    }
    val button3 = new Button(rightAction) {
      if (icon != null) text = "" // an icon-only button
    }
    toolBar.contents += button1
    toolBar.contents += button2
    toolBar.contents += button3
    layout(toolBar) = BorderPanel.Position.North
    listenTo(button1)
    listenTo(button2)
    listenTo(button3)
    reactions += {
      case ButtonClicked(`button1`) => displayResult("Action for first button/menu item", "Toolbar button 1")
      case ButtonClicked(`button2`) => displayResult("Action for second button/menu item", "Toolbar button 2")
      case ButtonClicked(`button3`) => displayResult("Action for third button/menu item", "Toolbar button 3")
    }
  }

  def createAbleMenu(): Menu = {
    val ableMenu = new Menu("Action State")
    val cmi1 = new CheckMenuItem("First action enabled") { selected = true }
    val cmi2 = new CheckMenuItem("Second action enabled") { selected = true }
    val cmi3 = new CheckMenuItem("Third action enabled") { selected = true }

    ableMenu.contents += cmi1
    ableMenu.contents += cmi2
    ableMenu.contents += cmi3
    listenTo(cmi1)
    listenTo(cmi2)
    listenTo(cmi3)
    reactions += {
      case ButtonClicked(`cmi1`) => leftAction.enabled = cmi1.selected
      case ButtonClicked(`cmi2`) => middleAction.enabled = cmi2.selected
      case ButtonClicked(`cmi3`) => rightAction.enabled = cmi3.selected
    }
    ableMenu
  }

}

object ActionDemo extends SimpleSwingApplication {
  /** Returns an ImageIcon option, or None if the path was invalid. */
  def createNavigationIcon(imageName: String): Option[ImageIcon] = {
    val imgLocation = "/toolbarButtonGraphics/navigation/" + imageName + ".gif"
    val imageURL: URL = getClass().getResource(imgLocation)
    if (imageURL == null) {
      System.err.println("Resource not found: " + imgLocation)
      return None
    } else {
      return Some(new ImageIcon(imageURL))
    }
  }

  lazy val top = new MainFrame() {
    title = "ActionDemo"
    //Create and set up the content pane.
    val demo = new ActionDemo()
    menuBar = demo.createMenuBar()
    demo.createToolBar()
    contents = demo
  }
}

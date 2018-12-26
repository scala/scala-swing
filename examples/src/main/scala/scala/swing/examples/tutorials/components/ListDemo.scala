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
import scala.swing.event.{ ButtonClicked, EditDone, SelectionChanged, ValueChanged }
import java.awt.Toolkit

/**
 * Tutorial: How to Use Lists
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/list.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java]]
 *
 * ListDemo.scala requires no other files.
 */
class ListDemo extends BorderPanel {

  private val listModel: Array[String] = Array("Jane Doe", "John Smith", "Kathy Green")

  //Create the list and put it in a scroll pane.
  private val initiallySelected = 0
  private val listMe: ListView[String] = new ListView[String](listModel) {
    selection.intervalMode = ListView.IntervalMode.Single
    selectIndices(initiallySelected)
    visibleRowCount = 5
  }

  val listScrollPane: ScrollPane = new ScrollPane(listMe)

  val hireButton: Button = new Button("Hire") {
    enabled = false
    reactions += {
      case ButtonClicked(_) => hireListenerMethod()
    }
  }

  val fireButton: Button = new Button("Fire") {
    reactions += {
      case ButtonClicked(_) => fireListenerMethod()
    }
  }

  val employeeName: TextField = new TextField(10)
  // employeeName.peer.getDocument().addDocumentListener(hireListener);
  val nameSelected: String = listModel(initiallySelected)

  //Create a panel that uses BoxLayout.
  val buttonPane: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(5, 5, 5, 5)
    contents += fireButton
    contents += Swing.HStrut(5)
    contents += fireButton
    contents += Swing.HStrut(5)
    contents += new Separator()
    contents += Swing.HStrut(5)
    contents += employeeName
    contents += hireButton
  }

  layout(listScrollPane) = BorderPanel.Position.Center
  layout(buttonPane) = BorderPanel.Position.South

  listenTo(listMe.selection)
  listenTo(employeeName)

  reactions += {
    case EditDone(`employeeName`) =>
      hireListenerMethod()
    case SelectionChanged(`listMe`) =>
      fireButton.enabled = listMe.selection.leadIndex >= 0
    case ValueChanged(`employeeName`) =>
      hireButton.enabled = employeeName.text.trim().length > 0
  }

  //This method tests for string equality. You could certainly
  //get more sophisticated about the algorithm.  For example,
  //you might want to ignore white space and capitalization.
  def alreadyInList(name: String): Boolean = listMe.listData.contains(name)

  def fireListenerMethod(): Unit = {
    //This method can be called only if
    //there's a valid selection
    //so go ahead and remove whatever's selected.
    var index: Int = listMe.selection.leadIndex
    val (x, y) = listMe.listData.splitAt(index)
    listMe.listData = x ++ y.tail

    val size: Int = listMe.listData.size

    if (size == 0) { //Nobody's left, disable firing.
      fireButton.enabled = false
    } else { //Select an index.
      if (index == size) {
        //removed item in last position
        index -= 1
      }
      listMe.selectIndices(index)
      listMe.ensureIndexIsVisible(index)
    }
  }
  def hireListenerMethod(): Unit = {
    val nameSelected: String = employeeName.text
    //User didn't type in a unique name...
    if (nameSelected.equals("") || alreadyInList(nameSelected)) {
      Toolkit.getDefaultToolkit.beep()
      employeeName.requestFocusInWindow()
      employeeName.selectAll()
    } else {
      val index: Int = listMe.selection.leadIndex + 1 //get selected index

      val (x: Seq[String], y: Seq[String]) = listMe.listData.splitAt(index)
      val z = employeeName.text +: y
      listMe.listData = x ++ z
      //If we just wanted to add to the end, we'd do this:
      //listModel.listData += employeeName.text;

      //Reset the text field.
      employeeName.requestFocusInWindow()
      employeeName.text = ""

      //Select the new item and make it visible.
      listMe.selectIndices(index)
      listMe.ensureIndexIsVisible(index)
    }
  }
}

object ListDemo extends SimpleSwingApplication {
  lazy val top: Frame = new MainFrame() {
    title = "ListDemo"
    //Create and set up the content pane.
    contents = new ListDemo()
  }
}
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
package scala.swing.examples.tutorials.events

import scala.swing._
import scala.swing.event.{ButtonClicked, SelectionChanged}
import java.awt.Dimension

/**
 * Tutorial: How to Write a List Selection Listener
 * [[http://docs.oracle.com/javase/tutorial/uiswing/events/listselectionlistener.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/events/ListSelectionDemoProject/src/events/ListSelectionDemo.java]]
 * 
 * ListSelectionDemo.scala requires no other files.
 */
class ListSelectionDemo extends BorderPanel {
  val listData = Array( "one", "two", "three", "four",
                              "five", "six", "seven" )
  val columnNames = Array( "French", "Spanish", "Italian" )
  val list = new ListView(listData)
  val listPane = new ScrollPane(list)
  
  val modes = Array( "SINGLE_SELECTION", "SINGLE_INTERVAL_SELECTION",
      "MULTIPLE_INTERVAL_SELECTION")
  val comboBox = new ComboBox(modes) {
    selection.index = 2
  }
  val controlPane = new FlowPanel() {
    contents += new Label("Selection mode:")
    contents += comboBox
  }
  
  //Build output area.
  val output = new TextArea(1, 10) {
    editable = false
  }
  val outputPane = new ScrollPane(output) {
    verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
    horizontalScrollBarPolicy = ScrollPane.BarPolicy.AsNeeded
  }
  val listContainer = new GridPanel(1, 1) {
    contents += listPane
    border = Swing.TitledBorder(Swing.EmptyBorder, "List")
  }
  val topHalf = new BoxPanel(Orientation.Horizontal) {
    contents += listContainer
    border = Swing.EmptyBorder(5, 5, 0, 5)
    minimumSize = new Dimension(100, 50)
    preferredSize = new Dimension(100, 110)
  }
  
  val bottomHalf = new BorderPanel() {
    layout(controlPane) = BorderPanel.Position.North
    layout(outputPane) = BorderPanel.Position.Center
    preferredSize = new Dimension(450, 135)
  }

  //Do the layout.
  val splitPane = new SplitPane(Orientation.Horizontal, topHalf, bottomHalf)
  
  layout(splitPane) = BorderPanel.Position.Center
  
  listenTo(comboBox.selection)
  listenTo(list.selection)
  
  reactions += {
    case SelectionChanged(`comboBox`) =>
      val newMode = comboBox.selection.item
      newMode match {
        case "SINGLE_SELECTION" =>
          list.selection.intervalMode = ListView.IntervalMode.Single
        case "SINGLE_INTERVAL_SELECTION" =>
          list.selection.intervalMode = ListView.IntervalMode.SingleInterval
        case "MULTIPLE_INTERVAL_SELECTION" =>
          list.selection.intervalMode = ListView.IntervalMode.MultiInterval
      } 
      output.append("----------" + "Mode: " + newMode + "----------" + "\n")
    case SelectionChanged(`list`) =>
      val firstIndex = list.selection.leadIndex
      val lastIndex = list.selection.anchorIndex // anchor = last?
      val isAdjusting = list.selection.adjusting
      output.append("Event for indexes " + firstIndex + " - " + lastIndex +
          "; isAdjusting is " + isAdjusting + "; selected indexes:")
      if (list.selection.indices.isEmpty) {
        output.append(" <none>")
      }
      else {
        // Find out which indexes are selected.
        for (i <- list.selection.indices) {
          output.append(" " + i)
        }
      }
      output.append("\n")
      output.caret.position = output.peer.getDocument().getLength()
  }
}

object ListSelection extends SimpleSwingApplication {
  //Create and set up the window.
  lazy val top = new MainFrame {
    title = "ListSelectionDemo"
    contents = new ListSelectionDemo()
  }
}
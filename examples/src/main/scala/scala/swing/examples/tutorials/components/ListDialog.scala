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
import java.awt.{Dimension, GraphicsConfiguration, Point}
import scala.swing._
import scala.swing.event.{ButtonClicked, Key, MouseClicked}

/**
 * Tutorial: How to Use Buttons, Check Boxes, and Radio Buttons
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/button.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialogRunner.java]]
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialog.java]]
 */
/**
 * Use this modal dialog to let the user choose one string from a long
 * list.  See ListDialogRunner.scala for an example of using ListDialog.
 * The basics:
 * <pre>
 * String[] choices = {"A", "long", "array", "of", "strings"};
 * String selectedName = ListDialog.showDialog(
 * componentInControllingFrame,
 * locatorComponent,
 * "A description of the list:",
 * "Dialog Title",
 * choices,
 * choices[0]);
 * </pre>
 */
class ListDialog(frame: Frame, locationComp: Component,
  labelText: String, title: String, data: Array[String],
  initialValue: String, longValue: String) extends Dialog(
  frame: Window,
  frame.peer.getGraphicsConfiguration: GraphicsConfiguration) {
  // Must be a modal dialog.  Otherwise the showDialog call will return
  // immediately and the value returned will not reflect the user's
  // selections.
  peer.setModal(true)
  val cancelButton = new Button("Cancel")
  val setButton = new Button("Set")
  val list = new ListView[String](data) {
    if (longValue != null) {
      prototypeCellValue = longValue
      visibleRowCount = -1
    }
    selection.intervalMode = ListView.IntervalMode.SingleInterval
  }
  list.peer.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP)
  val lSelect = list.selection
  defaultButton = setButton
  //
  val listScroller = new ScrollPane(list) {
    preferredSize = new Dimension(250, 80)
    xLayoutAlignment = java.awt.Component.LEFT_ALIGNMENT
  }
  //
  //Create a container so that we can add a title around
  //the scroll pane.  Can't add a title directly to the
  //scroll pane because its background would be white.
  //Lay out the label and scroll pane from top to bottom.
  val label = new Label(labelText)
  val listPane = new BoxPanel(Orientation.Vertical) {
   border = Swing.EmptyBorder(10, 10, 10, 10)
   contents += label
   contents += Swing.RigidBox(new Dimension(0, 5))
   contents += listScroller
  }
  
  val buttonPane = new BoxPanel(Orientation.Horizontal) {
    border = Swing.EmptyBorder(0, 10, 10, 10)
    contents += Swing.HGlue
    contents += cancelButton
    contents += Swing.HGlue
    contents += setButton
  }

  contents = new BorderPanel {
    add(listPane, BorderPanel.Position.Center)
    add(buttonPane, BorderPanel.Position.South)
  }

  //Initialize values.
  setValue(initialValue)
  // Events
  listenTo(setButton)
  listenTo(cancelButton)
  listenTo(list.mouse.clicks)
//  listenTo(list.selection)
  reactions += {
    case ButtonClicked(`setButton`) => 
      setValue(list.listData(list.selection.leadIndex))
      visible = false
    case ButtonClicked(`cancelButton`) => 
      setValue(initialValue)
      visible = false
//    case SelectionChanged(`list`) =>
//      println("SelectionChanged")
//      println(list.selection.leadIndex)
    case MouseClicked(source: Component, point: Point, modifiers: Key.Modifiers,
      clicks: Int, triggersPopup: Boolean) => if (clicks == 2) setButton.doClick
  }
  //
  pack()
  setLocationRelativeTo(locationComp)

  private def setValue(newValue: String): Unit = {
    ListDialog.value = newValue
  }
}

object ListDialog {

  var value: String = ""

  /**
   * Set up and show the dialog.  The first Component argument
   * determines which frame the dialog depends on; it should be
   * a component in the dialog's controlling frame. The second
   * Component argument should be null if you want the dialog
   * to come up with its left corner in the center of the screen;
   * otherwise, it should be the component on top of which the
   * dialog should appear.
   */
  def showDialog(frameComp: Frame, locationComp: Component,
    labelText: String,
    title: String,
    possibleValues: Array[String],
    initialValue: String,
    longValue: String): String = {
    val dialog = new ListDialog(frameComp, locationComp,
      labelText, title, possibleValues, initialValue, longValue)
    dialog.visible = true
    value
  }
}

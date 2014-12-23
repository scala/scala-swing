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
import scala.swing.event.SelectionChanged
import java.awt.Color
import java.util.Date
import java.text.SimpleDateFormat


import scala.util.{Try, Failure, Success}

/**
 * Tutorial: How to Use Combo Boxes
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ComboBoxDemo2Project/src/components/ComboBoxDemo2.java]]
 */
class ComboBoxDemo2 extends BoxPanel(Orientation.Vertical) {
  val patternExamples = Array[String](
    "dd MMMMM yyyy",
    "dd.MM.yy",
    "MM/dd/yy",
    "yyyy.MM.dd G 'at' hh:mm:ss z",
    "EEE, MMM d, ''yy",
    "h:mm a",
    "H:mm:ss:SSS",
    "K:mm a,z",
    "yyyy.MMMMM.dd GGG hh:mm aaa")


  //Set up the UI for selecting a pattern.
  val patternLabel1 = new Label("Enter the pattern string or")
  val patternLabel2 = new Label("select one from the list:")
  val patternList = new ComboBox[String](patternExamples) {
    makeEditable()
    selection.item = patternExamples(0)
  }
  
  //Create the UI for displaying result.
  val resultLabel = new Label("Current Date/Time", Swing.EmptyIcon, Alignment.Left ) {
    foreground = Color.black
    border = Swing.EmptyBorder(5, 5, 5, 5)
  }
  
  val result = new Label(" ") {
    foreground = Color.black
    border = Swing.CompoundBorder(
             Swing.LineBorder(Color.black),
             Swing.EmptyBorder(5,5,5,5)
        )
  }
  
  val patternPanel = new BoxPanel(Orientation.Vertical) {
    contents += patternLabel1
    contents += patternLabel2
    xLayoutAlignment =  java.awt.Component.LEFT_ALIGNMENT
    contents += patternList
  }

  val resultPanel = new GridPanel(0,1) {
    contents += resultLabel
    contents += result
    xLayoutAlignment = java.awt.Component.LEFT_ALIGNMENT
  }
  
  contents += patternPanel
  contents += resultPanel
  border = Swing.EmptyBorder(10,10,10,10)
  reformat()
  
  listenTo(patternList.selection)
  reactions += {
    case SelectionChanged(`patternList`) => reformat()
  }
  
  def reformat(): Unit = {
    Try {
      val today = new Date()
      val formatter = new SimpleDateFormat(patternList.selection.item)
      formatter.format(today)
    } match {
      case Success( dateString) =>
        result.foreground = Color.black
        result.text = dateString
      case Failure( err ) =>
        result.text = s"Error: ${err.getMessage}"
    }
  }
}

object ComboBoxDemo2 extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "ComboBoxDemo2"
    //Create and set up the content pane.
    contents = new ComboBoxDemo2()
  }
}
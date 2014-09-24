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
import scala.collection.immutable.Range
import scala.swing.event.{ MouseClicked, TableColumnsSelected, TableRowsSelected }
/*
 * Tutorial: How to Use Tables
 * http://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 * 
 * Source code references:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SimpleTableSelectionDemoProject/src/components/SimpleTableSelectionDemo.java
 * 
 * SimpleTableSelectionDemo.scala requires no other files.
 */
class SimpleTableSelectionDemo extends GridPanel(1, 0) {
  private val Debug = true
  private val AllowColumnSelection = false
  private val AllowRowSelection = true
  val columnNames: Array[String] = Array("First Name",
    "Last Name",
    "Sport",
    "# of Years",
    "Vegetarian")
  val data: Array[Array[Any]] = new Array[Array[Any]](5)
  data(0) = Array("Kathy", "Smith", "Snowboarding", new Integer(5), false)
  data(1) = Array("John", "Doe", "Rowing", new Integer(3), true)
  data(2) = Array("Sue", "Black", "Knitting", new Integer(2), false)
  data(3) = Array("Jane", "White", "Speed reading", new Integer(20), true)
  data(4) = Array("Joe", "Brown", "Pool", new Integer(10), false)

  val table = new Table(data, columnNames) {
    preferredViewportSize = new Dimension(500, 70)
    selection.intervalMode = Table.IntervalMode.Single
  }
  table.peer.setFillsViewportHeight(true)

  if (AllowRowSelection) {
    listenTo(table.selection)
    reactions += {
      case TableRowsSelected(`table`, range: Range, adjusting: Boolean) =>
        //Ignore extra messages.
        if (!adjusting) {
          if (range.isEmpty) {
            println("No rows are selected.")
          } else {
            val selectedRow = range.head
            println("Row " + selectedRow + " is now selected.")
          }
        }
    }
  } else {
    if (table.selection.elementMode == Table.ElementMode.Cell) {
      table.selection.elementMode = Table.ElementMode.Column
    } else if (table.selection.elementMode == Table.ElementMode.Row) {
      table.selection.elementMode = Table.ElementMode.None
    }
  }

  if (AllowColumnSelection) { // false by default
    if (AllowRowSelection ||
        table.selection.elementMode == Table.ElementMode.Row) {
      //We allow both row and column selection, which
      //implies that we *really* want to allow individual
      //cell selection.
      table.selection.elementMode = Table.ElementMode.Cell
    }
    else if (table.selection.elementMode == Table.ElementMode.None) {
      table.selection.elementMode = Table.ElementMode.Column
    }
    listenTo(table.selection)
    reactions += {
      case TableColumnsSelected(`table`, range: Range, adjusting: Boolean) =>
        //Ignore extra messages.
        if (!adjusting) {
          if (range.isEmpty) {
            println("No rows are selected.")
          } else {
            val selectedColumn = range.head
            println("Column " + selectedColumn + " is now selected.")
          }
        }
    }
  }
  if (Debug) {
    listenTo(table)
    reactions += {
      case e: MouseClicked => printDebugData(table)
    }
  }

  //Create the scroll pane and add the table to it.
  val scrollPane = new ScrollPane(table)

  //Add the scroll pane to this panel.
  contents += scrollPane

  private def printDebugData(table: Table): Unit = {
    val numRows = table.rowCount
    val numCols = table.peer.getColumnCount()
    val model: javax.swing.table.TableModel = table.model

    println("Value of data: ")
    for (i <- 0 until numRows) {
      print("    row " + i + ":")
      for (j <- 0 until numCols) {
        print("  " + model.getValueAt(i, j))
      }
      println()
    }
    println("--------------------------")
  }
}

object SimpleTableSelectionDemo {
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  def createAndShowGUI(): Unit = {
    val frame = new Frame() {
      title = "SimpleTableSelectionDemo"
      //Create and set up the content pane.
      val newContentPane = new SimpleTableSelectionDemo()
      newContentPane.opaque = true //content panes must be opaque
      contents = newContentPane
      // Display the window
      pack()
      visible = true
      override def closeOperation() = {
        sys.exit(0)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      def run(): Unit = {
        createAndShowGUI()
      }
    })
  }
}
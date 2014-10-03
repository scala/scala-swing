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
import scala.swing.event.ButtonClicked
import javax.swing.table.DefaultTableModel
import java.text.MessageFormat

/**
 * Tutorial: How to Use Tables
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/table.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TablePrintDemoProject/src/components/TablePrintDemo.java]]
 * 
 * TablePrintDemo.scala requires no other files.
 */
class TablePrintDemo extends BoxPanel(Orientation.Vertical) {
  private val Debug = false

  val table = new Table(5, 5) {
    model = new MyTableModel()
    preferredViewportSize = new Dimension(500, 70)
  }
  table.peer.setFillsViewportHeight(true)
  //  if (Debug) {
  //    listenTo(table)
  //    reactions += {
  //      case e: MouseClicked => printDebugData(table)
  //    }
  //  }

  //Create the scroll pane and add the table to it.
  val scrollPane = new ScrollPane(table)

  //Add a print button.
  val printButton = new Button("Print") {
    // peer.setAlignmentX(0.5f)
    xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
  }

  //Add the scroll pane to this panel.
  contents += scrollPane
  contents += printButton

  listenTo(printButton)
  reactions += {
    case ButtonClicked(`printButton`) =>
      val header: MessageFormat = new MessageFormat("Page {0, number, integer}")
      try {
        table.peer.print(javax.swing.JTable.PrintMode.FIT_WIDTH, header, null)
      } catch {
        case e: java.awt.print.PrinterException =>
          //sys.err does not have a print method
          System.err.format("Cannot print %s%n", e.getMessage())
      }
  }

  class MyTableModel extends DefaultTableModel {
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

    override def getColumnCount(): Int = { columnNames.length }
    override def getRowCount(): Int = { if (data == null) 0 else data.length }
    override def getColumnName(col: Int): String = { columnNames(col) }
    override def getValueAt(row: Int, col: Int): Object = {
      col match {
        case 0 => data(row)(col).asInstanceOf[String]
        case 1 => data(row)(col).asInstanceOf[String]
        case 2 => data(row)(col).asInstanceOf[String]
        case 3 => data(row)(col).asInstanceOf[java.lang.Integer]
        case 4 => data(row)(col).asInstanceOf[java.lang.Boolean]
      }
    }
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    override def setValueAt(value: Object, row: Int, col: Int): Unit = {
      if (Debug) {
        println("Setting value at " + row + "," + col +
          " to " + value +
          " (an instance of " +
          value.getClass() + ")")
      }
      data(row)(col) = value
      fireTableCellUpdated(row, col)
      if (Debug) {
        println("New value of data:")
        printDebugData(table)
      }
    }
    def printDebugData(table: Table): Unit = {
      val numRows = table.rowCount
      val numCols = table.peer.getColumnCount()
      val model: javax.swing.table.TableModel = table.model

      println("Value of data: ")
      for (i <- 0 until numRows) {
        System.out.print("    row " + i + ":")
        for (j <- 0 until numCols) {
          print("  " + model.getValueAt(i, j))
        }
        println()
      }
      println("--------------------------")
    }
  }

}

object TablePrintDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "TablePrintDemo"
    contents = new TablePrintDemo()
  }
}
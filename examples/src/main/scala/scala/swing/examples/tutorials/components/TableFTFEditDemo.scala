
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
import javax.swing.table.DefaultTableModel

/**
 * Tutorial: How to Use Tables
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/table.html]]
 * 
 * Source code references:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableFTFEditDemoProject/src/components/TableFTFEditDemo.java]]
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableFTFEditDemoProject/src/components/IntegerEditor.java]]
 * 
 * TableFTFEditDemo.scala requires one other file:
 *   IntegerEditor.scala
 *   
 * This is exactly like TableDemo, except that it uses a
 * custom cell editor to validate integer input.
 */
class TableFTFEditDemo extends GridPanel(1, 0) {
  private val Debug = false
  val table = new Table(5, 5) {
    model = new MyTableModel()
    preferredViewportSize = new Dimension(500, 70)
  }
  table.fillsViewportHeight = true

  //Create the scroll pane and add the table to it.
  val scrollPane = new ScrollPane(table)

  //Set up stricter input validation for the integer column.
  val cl = Class.forName("java.lang.Integer")
  table.defaultEditor_= (cl, new IntegerEditor(0, 100))

  //If we didn't want this editor to be used for other
  //Integer columns, we'd do this:
  // table.columnModel.getColumn(3).setCellEditor(
  //   new IntegerEditor(0, 100));

  //Add the scroll pane to this panel.
  contents += scrollPane
  class MyTableModel extends DefaultTableModel {
    val columnNames: Array[String] = Array("First Name",
      "Last Name",
      "Sport",
      "# of Years",
      "Vegetarian")
    val data: Array[Array[Any]] = new Array[Array[Any]](5)
    data(0) = Array("Kathy", "Smith", "Snowboarding", 5, false)
    data(1) = Array("John", "Doe", "Rowing", 3, true)
    data(2) = Array("Sue", "Black", "Knitting", 2, false)
    data(3) = Array("Jane", "White", "Speed reading", 20, true)
    data(4) = Array("Joe", "Brown", "Pool", 10, false)

    override def getColumnCount(): Int = { columnNames.length }
    override def getRowCount(): Int = { if (data == null) 0 else data.length }
    override def getColumnName(col: Int): String = { columnNames(col) }
    override def getColumnClass(c: Int): Class[_] = {
      getValueAt(0, c).getClass();
    }
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
      val numCols = table.columnCount
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

object TableFTFEditDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "TableFTFEditDemo"
    contents = new TableFTFEditDemo()
  }
}
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
import javax.swing.event.{ListSelectionEvent, ListSelectionListener}
import javax.swing.table.DefaultTableModel

/**
 * Tutorial: How to Use Tables
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/table.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableSelectionDemoProject/src/components/TableSelectionDemo.java]]
 * 
 * TableSelectionDemo.scala requires no other files.
 */
class TableSelectionDemo extends BoxPanel(Orientation.Vertical) {

  val table = new Table(5, 5) {
    model = new MyTableModel()
    preferredViewportSize = new Dimension(500, 70)
  }
  table.fillsViewportHeight = true
  table.selectionModel.addListSelectionListener(new RowListener())
  table.columnModel.getSelectionModel().
    addListSelectionListener(new ColumnListener())
  //Create the scroll pane and add the table to it.
  val scrollPane = new ScrollPane(table)

  contents += new Label("Selection Mode")

  val multipleInterval = new RadioButton("Multiple Interval Selection") {
    selected = true
  }
  val single = new RadioButton("Single Selection")
  val singleInterval = new RadioButton("Single Interval Selection")
  val buttonGroup = new ButtonGroup() {
    buttons += multipleInterval
    buttons += single
    buttons += singleInterval
  }
  contents += multipleInterval
  contents += single
  contents += singleInterval

  contents += new Label("Selection Options")
  val rowCheck = new CheckBox("Row Selection") {
    selected = true
  }
  val columnCheck = new CheckBox("Column Selection")
  val cellCheck = new CheckBox("Cell Selection") {
    enabled = false
  }
  contents += rowCheck
  contents += columnCheck
  contents += cellCheck

  //Add the scroll pane to this panel.
  contents += scrollPane

  val output = new TextArea(5, 40) {
    editable = false
  }

  contents += new ScrollPane(output)

  listenTo(multipleInterval)
  listenTo(single)
  listenTo(singleInterval)
  listenTo(rowCheck)
  listenTo(columnCheck)
  listenTo(cellCheck)

  reactions += {
    case ButtonClicked(`rowCheck`) =>
      //Scala swing does not allow cell selection to be set independently.
      if (rowCheck.selected) {
        table.selection.elementMode = Table.ElementMode.Row
        columnCheck.selected = false
        cellCheck.selected = false
      }
      else {
        table.selection.elementMode = Table.ElementMode.None
      }
    case ButtonClicked(`columnCheck`) =>
      //Scala swing does not allow cell selection to be set independently.
      if (columnCheck.selected) {
        table.selection.elementMode = Table.ElementMode.Column
        rowCheck.selected = false
        cellCheck.selected = false
      }
      else {
        table.selection.elementMode = Table.ElementMode.None
      }
    case ButtonClicked(`cellCheck`) =>
      //Scala swing does not allow cell selection to be set independently.
      if (cellCheck.selected) {
        table.selection.elementMode = Table.ElementMode.Cell
        rowCheck.selected = false
        columnCheck.selected = false
      }
      else {
        table.selection.elementMode = Table.ElementMode.None
      }
    case ButtonClicked(`multipleInterval`) =>
      table.selection.intervalMode = Table.IntervalMode.MultiInterval
      //If cell selection is on, turn it off.
      if (cellCheck.selected) {
        cellCheck.selected = false
        cellCheck.enabled = false
        table.selection.elementMode = Table.ElementMode.None
      }
    case ButtonClicked(`singleInterval`) =>
      table.selection.intervalMode = Table.IntervalMode.SingleInterval
      cellCheck.enabled = true
    case ButtonClicked(`single`) =>
      table.selection.intervalMode = Table.IntervalMode.Single
      cellCheck.enabled = true
  }

  def outputSelection(): Unit = {
    output.append("Lead: %d, %d. ".format(
      table.selectionModel.getLeadSelectionIndex(),
      table.columnModel.getSelectionModel().
        getLeadSelectionIndex()))
    output.append("Rows:")
    for (c: Int <- table.selection.rows) {
      output.append(" " + c.asInstanceOf[Int])
    }
    output.append(". Columns:")
    for (c: Int <- table.selection.columns) {
      output.append(" " + c.asInstanceOf[Int])
    }
    output.append(".\n")
  }

  class RowListener extends ListSelectionListener {
    def valueChanged(event: ListSelectionEvent): Unit = {
      if (!event.getValueIsAdjusting()) {
        output.append("ROW SELECTION EVENT. ")
        outputSelection()
      }
    }
  }

  class ColumnListener extends ListSelectionListener {
    def valueChanged(event: ListSelectionEvent): Unit = {
      if (!event.getValueIsAdjusting()) {
        output.append("COLUMN SELECTION EVENT. ")
        outputSelection()
      }
    }
  }

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
      getValueAt(0, c).getClass()
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
     * editable.
     */
    override def isCellEditable(row: Int, col: Int): Boolean = {
      //Note that the data/cell address is constant,
      //no matter where the cell appears onscreen.
      if (col < 2) {
        false
      } else {
        true
      }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    override def setValueAt(value: Object, row: Int, col: Int): Unit = {
      data(row)(col) = value
      fireTableCellUpdated(row, col)
    }
  }
}

object TableSelectionDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "TableSelectionDemo"
    contents = new TableSelectionDemo()
  }
}

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.components

import javax.swing.AbstractAction
import javax.swing.DefaultCellEditor
import javax.swing.JFormattedTextField
import javax.swing.JOptionPane
import javax.swing.JTable
import javax.swing.KeyStroke
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.Component
import java.awt.Toolkit
import java.text.NumberFormat
import java.text.ParseException
import javax.swing.text.DefaultFormatterFactory
import javax.swing.text.NumberFormatter

@SerialVersionUID(1L)
class IntegerEditor(min: Int, max: Int) extends DefaultCellEditor(new JFormattedTextField()) {

  var ftf: JFormattedTextField = getComponent.asInstanceOf[JFormattedTextField]

  var integerFormat: NumberFormat = NumberFormat.getIntegerInstance

  private var minimum: java.lang.Integer = new java.lang.Integer(min)

  private var maximum: java.lang.Integer = new java.lang.Integer(max)

  private var DEBUG: Boolean = false

  val intFormatter = new NumberFormatter(integerFormat)

  intFormatter.setFormat(integerFormat)

  intFormatter.setMinimum(minimum)

  intFormatter.setMaximum(maximum)

  ftf.setFormatterFactory(new DefaultFormatterFactory(intFormatter))

  ftf.setValue(minimum)

  ftf.setHorizontalAlignment(SwingConstants.TRAILING)

  ftf.setFocusLostBehavior(JFormattedTextField.PERSIST)

  ftf.getInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check")

  ftf.getActionMap.put("check", new AbstractAction() {

    def actionPerformed(e: ActionEvent): Unit = {
      if (!ftf.isEditValid) {
        if (userSaysRevert()) {
          ftf.postActionEvent()
        }
      } else try {
        ftf.commitEdit()
        ftf.postActionEvent()
      } catch {
        case exc: java.text.ParseException => 
      }
    }
  })

  override def getTableCellEditorComponent(table: JTable, value: AnyRef, isSelected: Boolean, row: Int, column: Int): Component = {
    val ftf = super.getTableCellEditorComponent(table, value, isSelected, row, column).asInstanceOf[JFormattedTextField]
    ftf.setValue(value)
    ftf
  }

  override def getCellEditorValue(): AnyRef = {
    val ftf = getComponent.asInstanceOf[JFormattedTextField]
    val o = ftf.getValue
    if (o.isInstanceOf[java.lang.Integer]) {
      o
    } else if (o.isInstanceOf[Number]) {
      new java.lang.Integer(o.asInstanceOf[Number].intValue())
    } else {
      if (DEBUG) {
        println("getCellEditorValue: o isn't a Number")
      }
      try {
        integerFormat.parseObject(o.toString)
      } catch {
        case exc: ParseException => {
          System.err.println("getCellEditorValue: can't parse o: " + o)
          null
        }
      }
    }
  }

  override def stopCellEditing(): Boolean = {
    val ftf = getComponent.asInstanceOf[JFormattedTextField]
    if (ftf.isEditValid) {
      try {
        ftf.commitEdit()
      } catch {
        case exc: java.text.ParseException => 
      }
    } else {
      if (!userSaysRevert()) {
        return false
      }
    }
    super.stopCellEditing()
  }

  protected def userSaysRevert(): Boolean = {
    Toolkit.getDefaultToolkit.beep()
    ftf.selectAll()
    val options = Array[Object]("Edit", "Revert")
    val answer = JOptionPane.showOptionDialog(SwingUtilities.getWindowAncestor(ftf),
        "The value must be an integer between " + minimum + " and " + maximum + ".\n" + 
        "You can either continue editing " + "or revert to the last valid value.",
        "Invalid Text Entered", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE,
        null,
        options,
        options(1))
    if (answer == 1) {
      ftf.setValue(ftf.getValue)
      return true
    }
    false
  }
}

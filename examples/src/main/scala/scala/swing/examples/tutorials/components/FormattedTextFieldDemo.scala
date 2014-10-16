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
import scala.swing.event.ValueChanged
import java.text.NumberFormat
import java.awt.Color

/*
 * Tutorial: How to Use Formatted Text Fields
 * http://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java
 * 
 * FormattedTextFieldDemo.scala requires no other files.
 *
 * It implements a mortgage calculator that uses four JFormattedTextFields.
 */
class FormattedTextFieldDemo extends BorderPanel {

  //Values for the fields
  private var amount: Double = 100000
  private var rate: Double = 7.5 //7.5%
  private var numPeriods: Int = 30

  //Strings for the labels
  private val amountString = "Loan Amount: "
  private val rateString = "APR (%): "
  private val numPeriodsString = "Years: "
  private val paymentString = "Monthly Payment: "

  //Formats to format and parse numbers
  private val amountFormat = NumberFormat.getNumberInstance()
  private val percentFormat = NumberFormat.getNumberInstance()
  private val numPeriodsFormat = NumberFormat.getNumberInstance()
  percentFormat.setMinimumFractionDigits(3)
  private val paymentFormat = NumberFormat.getCurrencyInstance()

  val payment = computePayment(amount,
    rate,
    numPeriods)
  //Create the labels.
  val amountLabel = new Label(amountString)
  val rateLabel = new Label(rateString)
  val numPeriodsLabel = new Label(numPeriodsString)
  val paymentLabel = new Label(paymentString)

  //Create the text fields and set them up.
  val amountField = new FormattedTextField(amountFormat) {
    value = amount
    columns = 10
  }
  val rateField = new FormattedTextField(percentFormat) {
    value = rate
    columns = 10
  }
  // Format is obligatory in the scala constructors
  val numPeriodsField = new FormattedTextField(numPeriodsFormat) {
    value = numPeriods
    columns = 10
  }
  val paymentField = new FormattedTextField(paymentFormat) {
    value = payment
    columns = 10
    editable = false
    foreground = Color.red
  }

  //Tell accessibility tools about label/textfield pairs.
//  amountLabel.labelFor = amountField
//  rateLabel.labelFor = rateField
//  numPeriodsLabel.labelFor = numPeriodsField
//  paymentLabel.labelFor = paymentField

  //Layout the labels in a panel
  val labelPane = new GridPanel(0, 1) {
    contents ++= Seq(amountLabel, rateLabel, numPeriodsLabel, paymentLabel)
  }

  //Layout the text fields in a panel.
  val fieldPane = new GridPanel(0, 1) {
    contents ++= Seq(amountField, rateField, numPeriodsField, paymentField)
  }

  //Put the panels in this panel, labels on left, text fields on right.
  border = Swing.EmptyBorder(20, 20, 20, 20)
  layout(labelPane) = BorderPanel.Position.Center
  layout(fieldPane) = BorderPanel.Position.East

  listenTo(amountField)
  listenTo(rateField)
  listenTo(numPeriodsField)
  reactions += {
    case ValueChanged(`amountField`) =>
      amount = (amountField.value.asInstanceOf[Number]).doubleValue
      paymentField.value = computePayment(amount, rate, numPeriods)
    case ValueChanged(`rateField`) =>
      rate = (rateField.value.asInstanceOf[Number]).doubleValue
      paymentField.value = computePayment(amount, rate, numPeriods)
    case ValueChanged(`numPeriodsField`) =>
      numPeriods = (numPeriodsField.value.asInstanceOf[Number]).intValue
      paymentField.value = computePayment(amount, rate, numPeriods)
  }

  //Compute the monthly payment based on the loan amount,
  //APR, and length of loan.
  def computePayment(loanAmt: Double, rate: Double, numPeriodsYear: Int): Double = {
    val numPeriods = 12 * numPeriodsYear //get number of months
    val denominator = if (rate > 0.01) {
      val I = rate / 100.0 / 12.0 //get monthly rate from annual
      val partial1 = Math.pow((1 + I), (0.0 - numPeriods))
      (1 - partial1) / I
    } else { //rate ~= 0
      numPeriods
    }
    (-1 * loanAmt) / denominator
  }
}

object FormattedTextFieldDemo extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "FormattedTextFieldDemo"
    //Create and set up the content pane.
    javax.swing.UIManager.put("swing.boldMetal", false)
    contents = new FormattedTextFieldDemo();
  }
}
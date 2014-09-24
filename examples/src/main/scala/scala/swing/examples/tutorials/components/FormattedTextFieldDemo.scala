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
  private val amount: Double = 100000
  private val rate: Double = 7.5 //7.5%
  private val numPeriods: Int = 30

  //Labels to identify the fields
  //    private JLabel amountLabel
  //    private JLabel rateLabel
  //    private JLabel numPeriodsLabel
  //    private JLabel paymentLabel

  //Strings for the labels
  private val amountString = "Loan Amount: "
  private val rateString = "APR (%): "
  private val numPeriodsString = "Years: "
  private val paymentString = "Monthly Payment: "

  //Fields for data entry
  //    private JFormattedTextField amountField;
  //    private JFormattedTextField rateField;
  //    private JFormattedTextField numPeriodsField;
  //    private JFormattedTextField paymentField;

  //Formats to format and parse numbers
  private val amountFormat: NumberFormat = NumberFormat.getNumberInstance()
  private val percentFormat: NumberFormat = NumberFormat.getNumberInstance()
  percentFormat.setMinimumFractionDigits(3)
  private val paymentFormat: NumberFormat = NumberFormat.getCurrencyInstance()

  val payment = computePayment(amount,
    rate,
    numPeriods)
  //Create the labels.
  val amountLabel = new Label(amountString)
  val rateLabel = new Label(rateString)
  // val numPeriodsLabel = new Label(numPeriodsString)
  val paymentLabel = new Label(paymentString)

  //Create the text fields and set them up.
  val amountField = new FormattedTextField(amountFormat) {
    // There are no instances of FormattedTextField that work...
    // value = amount
    // columns = 10
  }
  val rateField = new FormattedTextField(percentFormat) {
    // There are no instances of FormattedTextField that work...
    // value = rate
    // columns = 10
  }
  // Format is obligatory in the scala constructors
  //    val     numPeriodsField = new FormattedTextField() {
  //    There are no instances of FormattedTextField that work...
  //       value = numPeriods
  //       columns = 10
  //    }
  val paymentField = new FormattedTextField(paymentFormat) {
    // There are no instances of FormattedTextField that work...
    // value = payment
    // columns = 10
    editable = false
    foreground = Color.red
  }

  //Tell accessibility tools about label/textfield pairs.
  amountLabel.peer.setLabelFor(amountField.peer)
  rateLabel.peer.setLabelFor(rateField.peer)
  // numPeriodsLabel.peer.setLabelFor(numPeriodsField.peer)
  paymentLabel.peer.setLabelFor(paymentField.peer)

  //Layout the labels in a panel
  val labelPane = new GridPanel(0, 1) {
    contents += amountLabel
    contents += rateLabel
    // contents += numPeriodsLabel
    contents += paymentLabel
  }

  //Layout the text fields in a panel.
  val fieldPane = new GridPanel(0, 1) {
    contents += amountField
    contents += rateField
    // contents += numPeriodsField
    contents += paymentField
  }

  //Put the panels in this panel, labels on left,
  //text fields on right.
  border = Swing.EmptyBorder(20, 20, 20, 20)
  layout(labelPane) = BorderPanel.Position.Center
  layout(fieldPane) = BorderPanel.Position.South

  listenTo(amountField)
  listenTo(rateField)
  // listenTo(numPeriodsField)
//What is the analogue of a PropertyChangeEvent?
//  reactions += {
//  }

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

object FormattedTextFieldDemo {
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  def createAndShowGUI(): Unit = {
    //Create and set up the window.
    val frame = new Frame() {
      title = "FormattedTextFieldDemo"

      //Create and set up the content pane.
      val contentPane: FormattedTextFieldDemo = new FormattedTextFieldDemo()
      contentPane.opaque = true
      contents = contentPane
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
        javax.swing.UIManager.put("swing.boldMetal", false)
        createAndShowGUI()
      }
    })
  }
}
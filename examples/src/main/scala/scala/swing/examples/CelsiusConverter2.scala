/*
 * scala-swing (https://www.scala-lang.org)
 *
 * Copyright EPFL, Lightbend, Inc., contributors
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala.swing.examples

import scala.swing._
import scala.swing.event._

object CelsiusConverter2 extends SimpleSwingApplication {
  def newField: TextField = new TextField {
    text = "0"
    columns = 5
    horizontalAlignment = Alignment.Right
  }

  val celsius     = newField
  val fahrenheit  = newField

  listenTo(fahrenheit, celsius)
  reactions += {
    case EditDone(`fahrenheit`) =>
      val f = Integer.parseInt(fahrenheit.text)
      val c = (f - 32) * 5 / 9
      celsius.text = c.toString
    case EditDone(`celsius`) =>
      val c = Integer.parseInt(celsius.text)
      val f = c * 9 / 5 + 32
      fahrenheit.text = f.toString
  }

  lazy val ui = new FlowPanel(celsius, new Label(" Celsius  =  "),
    fahrenheit, new Label(" Fahrenheit")) {
    border = Swing.EmptyBorder(15, 10, 10, 10)
  }

  def top: Frame = new MainFrame {
    title = "Convert Celsius / Fahrenheit"
    contents = ui
  }
}


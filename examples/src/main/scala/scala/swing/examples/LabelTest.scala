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

object LabelTest extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    contents = new Label {
      text = "Hello"
      listenTo(mouse.clicks)
      reactions += {
        case MousePressed(_, _, _, _, _) =>
          println("Mouse pressed")
      }
    }
  }
}


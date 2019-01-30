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

object SwingApp extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "SwingApp"
    var numClicks = 0

    object label extends Label {
      val prefix = "Number of button clicks: "
      text = prefix + "0  "
      listenTo(button)
      reactions += {
        case ButtonClicked(_) =>
          numClicks = numClicks + 1
          text = prefix + numClicks
      }
    }

    object button extends Button {
      text = "I am a button"
    }

    contents = new FlowPanel {
      contents ++= Seq(button, label)
      border = Swing.EmptyBorder(5, 5, 5, 5)
    }
  }
}


/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2014, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

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


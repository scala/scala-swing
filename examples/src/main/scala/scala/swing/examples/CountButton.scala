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

object CountButton extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "My Frame"
    contents = new GridPanel(2, 2) {
      hGap = 3
      vGap = 3
      val button = new Button {
        text = "Press Me!"
      }
      contents += button
      val label = new Label {
        text = "No button clicks registered"
      }
      contents += label

      listenTo(button)
      var nclicks = 0
      reactions += {
        case ButtonClicked(b) =>
          nclicks += 1
          label.text = "Number of button clicks: " + nclicks
      }
    }
  }
}

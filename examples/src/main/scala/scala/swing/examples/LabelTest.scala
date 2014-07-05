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

object LabelTest extends SimpleSwingApplication {
  def top = new MainFrame {
    contents = new Label {
      text = "Hello"
      listenTo(mouse.clicks)
      reactions += {
        case MousePressed(_, _, _, _, _) =>
          println("Mouse pressed2")
      }
    }
  }
}


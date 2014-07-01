package scala.swing.examples

import scala.swing._
import scala.swing.event._

class SimpleApplet extends Applet {
  object ui extends UI with Reactor {
    def init() = {
      val button = new Button("Press here!")
      val text = new TextArea("Java Version: " + util.Properties.javaVersion + "\n")
      listenTo(button)
      reactions += {
        case ButtonClicked(_) => text.text += "Button Pressed!\n"
        case _ =>
      }
      contents = new BoxPanel(Orientation.Vertical) { contents.append(button, text) }
    }
  }
}

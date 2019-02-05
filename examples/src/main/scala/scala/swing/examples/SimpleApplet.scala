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

class SimpleApplet extends Applet {

  object ui extends UI with Reactor {
    def init(): Unit = {
      val button = new Button("Press here!")
      val text = new TextArea("Java Version: " + util.Properties.javaVersion + "\n")
      listenTo(button)
      reactions += {
        case ButtonClicked(_) => text.text += "Button Pressed!\n"
        case _ =>
      }
      contents = new BoxPanel(Orientation.Vertical) {
        contents ++= Seq(button, text)
      }
    }
  }

}

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

/**
 * @author John Sullivan
 * @author Ingo Maier
 */
object PopupDemo extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    val popupMenu: PopupMenu = new PopupMenu {
      contents += new Menu("menu 1") {
        contents += new RadioMenuItem("radio 1.1")
        contents += new RadioMenuItem("radio 1.2")
      }
      contents += new Menu("menu 2") {
        contents += new RadioMenuItem("radio 2.1")
        contents += new RadioMenuItem("radio 2.2")
      }
    }
    val button = new Button("Show Popup Menu")
    reactions += {
      case ButtonClicked(b)     => popupMenu.show(b, 0, b.bounds.height)
      case PopupMenuCanceled(m) => println("Menu " + m + " canceled.")
    }
    listenTo(popupMenu)
    listenTo(button)

    contents = new FlowPanel(button)
  }
}
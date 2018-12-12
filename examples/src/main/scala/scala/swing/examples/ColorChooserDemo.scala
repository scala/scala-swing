/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2014, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing.examples

import java.awt.{Color, Font}

import scala.swing.BorderPanel._
import scala.swing.Swing._
import scala.swing._
import scala.swing.event._

/**
 * Demo for ColorChooser.
 * Based on http://download.oracle.com/javase/tutorial/uiswing/components/colorchooser.html
 *
 * @author andy@hicks.net
 */
object ColorChooserDemo extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "ColorChooser Demo"
    size = new Dimension(400, 400)

    contents = ui
  }

  val banner: Label = new Label("Welcome to Scala Swing") {
    horizontalAlignment = Alignment.Center
    foreground = Color.yellow
    background = Color.blue
    opaque = true
    font = new Font("SansSerif", Font.BOLD, 24)
  }

  def ui: BorderPanel = new BorderPanel {
    val colorChooser: ColorChooser = new ColorChooser {
      reactions += {
        case ColorChanged(_, c) =>
          banner.foreground = c
      }
    }

    colorChooser.border = TitledBorder(EtchedBorder, "Choose Text Color")

    val bannerArea: BorderPanel = new BorderPanel {
      layout(banner) = Position.Center
      border = TitledBorder(EtchedBorder, "Banner")
    }

    // Display a color selection dialog when button pressed 
    val selectColor: Button = new Button("Choose Background Color") {
      reactions += {
        case ButtonClicked(_) =>
          ColorChooser.showDialog(this, "Test", Color.red) match {
            case Some(c) => banner.background = c
            case None =>
          }
      }
    }

    layout(bannerArea)    = Position.North
    layout(colorChooser)  = Position.Center
    layout(selectColor)   = Position.South
  }
}
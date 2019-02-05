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

import java.awt.Color
import java.text.SimpleDateFormat
import java.util.Date

import javax.swing.{Icon, ImageIcon}

import scala.swing._
import scala.swing.event._

/**
 * Demonstrates how to use combo boxes and custom item renderers.
 *
 * TODO: clean up layout
 */
object ComboBoxes extends SimpleSwingApplication {

  import ComboBox._

  lazy val ui: FlowPanel = new FlowPanel {
    contents += new ComboBox(List(1, 2, 3, 4))

    val patterns = List(
      "dd MMMMM yyyy",
      "dd.MM.yy",
      "MM/dd/yy",
      "yyyy.MM.dd G 'at' hh:mm:ss z",
      "EEE, MMM d, ''yy",
      "h:mm a",
      "H:mm:ss:SSS",
      "K:mm a,z",
      "yyyy.MMMMM.dd GGG hh:mm aaa"
    )

    val dateBox = new ComboBox(patterns) {
      makeEditable()
    }

    contents += dateBox
    val field = new TextField(20) {
      editable = false
    }

    contents += field

    reactions += {
      case SelectionChanged(`dateBox`) => reformat()
    }

    listenTo(dateBox.selection)

    def reformat(): Unit = {
      try {
        val today = new Date
        val formatter = new SimpleDateFormat(dateBox.selection.item)
        val dateString = formatter.format(today)
        field.foreground = Color.black
        field.text = dateString
      } catch {
        case e: IllegalArgumentException =>
          field.foreground = Color.red
          field.text = "Error: " + e.getMessage
      }
    }


    val icons = try {
      List(
        new ImageIcon(resourceFromClassloader("images/margarita1.jpg")),
        new ImageIcon(resourceFromClassloader("images/margarita2.jpg")),
        new ImageIcon(resourceFromClassloader("images/rose.jpg")),
        new ImageIcon(resourceFromClassloader("images/banana.jpg"))
      )
    } catch {
      case _: Exception =>
        println("Couldn't load images for combo box")
        List(Swing.EmptyIcon)
    }

    val iconBox = new ComboBox(icons) {
      renderer = new ListView.AbstractRenderer[Icon, Label](new Label) {
        def configure(list: ListView[_], isSelected: Boolean, focused: Boolean, icon: Icon, index: Int): Unit = {
          component.icon = icon
          component.xAlignment = Alignment.Center
          if (isSelected) {
            component.border = Swing.LineBorder(list.selectionBackground, 3)
          } else {
            component.border = Swing.EmptyBorder(3)
          }
        }
      }
    }
    contents += iconBox
  }

  def top: Frame = new MainFrame {
    title = "ComboBoxes Demo"
    contents = ui
  }
}


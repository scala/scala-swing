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

package scala.swing.uitest



import scala.swing.Swing._
import scala.swing.event.ButtonClicked
import scala.swing._


/**
 * Test for issue SI-7597 https://issues.scala-lang.org/browse/SI-7597
 * (expanded to include other showXXXDialog dialogs )
 */
object SI7597 extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "SI7597 showXXXDialog tests"
    size = new Dimension(900, 200)

    lazy val dialog: Dialog = aDialog

    val fileChooserDialog = new FileChooser
    val colorChooser = new ColorChooser

    contents = new BoxPanel(Orientation.Vertical) {
      contents ++= Seq(
        fileChooserStyles("Component", parent = this),
        fileChooserStyles("Frame", parent = top),
        fileChooserStyles("Dialog", parent = dialog)
      )
    }

    def fileChooserStyles(rowTitle : String, parent : => PeerContainer): FlowPanel = new FlowPanel {
      contents ++= Seq(new Label(s"Parent is $rowTitle"))

      contents ++= Seq(
        simpleButton("Open", fileChooserDialog.showOpenDialog(parent)),
        simpleButton("Save", fileChooserDialog.showSaveDialog(parent)),
        simpleButton("Text", fileChooserDialog.showDialog(parent, "Text")),
        simpleButton("Confirmation", Dialog.showConfirmation(parent, "Confirmation") ),
        simpleButton("Input", Dialog.showInput(parent, "Input", initial = "Some text") ),
        simpleButton("Message", Dialog.showMessage(parent, "Message" )),
        simpleButton("Message", Dialog.showOptions(parent, "Message", entries = List("First", "Second", "Third"), initial=1 )),
        simpleButton("Color", ColorChooser.showDialog(parent, "Color", java.awt.Color.RED))
      )
    }

    def simpleButton(parentTitle : String, dialogChooser : => Any): Button = new Button {
      text = parentTitle
      reactions += {
        case _ : ButtonClicked =>
          dialogChooser match {
            case action => println(s"Result: $action")
          }
      }
    }
  }


  def aDialog:Dialog = new Dialog(top) {
    title = "A Dialog"
    size = new Dimension(300, 600)
    contents = new Label("Test Dialog.  Do Not Close")
    visible = true
  }
}

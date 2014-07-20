/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing.uitest


import scala.swing.FileChooser.Result
import scala.swing.Swing._
import scala.swing.event.ButtonClicked
import scala.swing._

/**
 * Test for issue SI-7597 https://issues.scala-lang.org/browse/SI-7597
 */
object SI7597 extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "SI7597 FileChooser test"


    lazy val dialog = aDialog

    val fileChooser = new FileChooser


    contents = new FlowPanel {
      contents ++= Seq(
        fileChooserStyles[Component]("Component", this),
        fileChooserStyles[Frame]("Frame", top),
        fileChooserStyles[Dialog]("Dialog", dialog)
      )
    }

    size = new Dimension(400, 400)


    def fileChooserStyles[T <: PeerContainer](rowTitle: String, parent: => T) = new FlowPanel {
      contents ++= Seq(
        new Label(s"Parent is $rowTitle"),
        fileChooserButton("Open", fileChooser.showOpenDialog(parent)),
        fileChooserButton("Save", fileChooser.showSaveDialog(parent)),
        fileChooserButton("Text", fileChooser.showDialog(parent, "Text"))
      )
    }

    def fileChooserButton(parentTitle: String, fileChooserStyle: => Result.Value): Button = new Button {
      text = parentTitle
      reactions += {
        case ButtonClicked(_) =>
          text = fileChooserStyle match {
            case Result.Approve => s"$parentTitle: ${fileChooser.selectedFile.toString}"
            case _ => parentTitle
          }
      }
    }

  }


  def aDialog = new Dialog(top) {
    title = "A Dialog"
    size = new Dimension(300, 300)
    contents = new Label("Do not Close")
    visible = true
  }

}

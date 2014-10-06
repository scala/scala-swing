/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.components

import scala.swing._
import scala.swing.event.ButtonClicked
import scala.swing.TabbedPane.Page
import javax.swing.border.Border
import javax.swing.ImageIcon
import java.awt.{ Dimension, Font }

/**
 * Tutorial: How to Use Dialogs
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/DialogDemo.java]]
 *
 * DialogDemo.scala requires these files:
 *   CustomDialog.scala
 *   /scala/swing/examples/tutorials/images/middle.gif
 */
class DialogDemo(val frame: Frame) extends BorderPanel {
  val oImage = DialogDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif")
  val icon: ImageIcon = if (oImage.isDefined) oImage.get else null
  val simpleDialogDesc = "Some simple message dialogs"
  val iconDesc = "A JOptionPane has its choice of icons"
  val moreDialogDesc = "Some more dialogs"
  // val customDialog = new CustomDialog(frame, "geisel", this)

  val padding: Border = Swing.EmptyBorder(20, 20, 5, 20)
  //Create the components.
  val label: Label = new Label("Click the \"Show it!\" button" +
    " to bring up the selected dialog.", Swing.EmptyIcon, Alignment.Center) {
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }
  val frequentPanel = createSimpleDialogBox()
  frequentPanel.border = padding
  val featurePanel = createFeatureDialogBox()
  featurePanel.border = padding
  val iconPanel = createIconDialogBox()
  iconPanel.border = padding

  //Lay them out.
  val tabbedPane = new TabbedPane()
  tabbedPane.pages += new Page("Simple Modal Dialogs", frequentPanel, simpleDialogDesc)
  tabbedPane.pages += new Page("More Dialogs", featurePanel, moreDialogDesc)
  tabbedPane.pages += new Page("Dialog Icons", iconPanel, iconDesc)

  layout(tabbedPane) = BorderPanel.Position.Center
  layout(label) = BorderPanel.Position.South

  /** Sets the text displayed at the bottom of the frame. */
  def setLabel(newText: String): Unit = {
    label.text = newText
  }
  /** Creates the panel shown by the first tab. */
  private def createSimpleDialogBox(): BorderPanel = {

    val showItButton: Button = new Button("Show it!")

    val defaultMessageCommandButton =new RadioButton("OK (in the L&F's words)") { selected = true }
    val yesNoCommandButton = new RadioButton("Yes/No (in the L&F's words)")
    val yeahNahCommandButton = new RadioButton("Yes/No (in the programmer's words)")
    val yncCommandButton = new RadioButton("Yes/No/Cancel (in the programmer's words)")

    val radioButtons:Array[RadioButton] = Array(
      defaultMessageCommandButton,
      yesNoCommandButton,
      yeahNahCommandButton,
      yncCommandButton
      )

    val group: ButtonGroup = new ButtonGroup() {
      radioButtons.foreach( buttons += _  )
    }

    listenTo(showItButton)

    reactions += {

      case ButtonClicked(`showItButton`) if group.selected == Some(defaultMessageCommandButton) =>
        Dialog.showMessage(this, "Eggs aren't supposed to be green.")

      case ButtonClicked(`showItButton`) if group.selected == Some(yesNoCommandButton)  =>
         Dialog.showConfirmation(
          this, "Would you like green eggs and ham?",
          "An Inane Question",
          Dialog.Options.YesNo) match {
           case Dialog.Result.Yes => setLabel("Ewww!")
           case Dialog.Result.No => setLabel("Me neither!")
           case _ => setLabel("Come on -- tell me!")
         }



      case ButtonClicked(`showItButton`) if group.selected == Some(yeahNahCommandButton)  =>
        Dialog.showOptions(this,
          "Would you like green eggs and ham?",
          "A Silly Question",
          Dialog.Options.YesNo,
          Dialog.Message.Question,
          Swing.EmptyIcon,
          Array("Yes, please", "No way!"),
          0) match {

          case Dialog.Result.Yes => setLabel("You're kidding!")
          case Dialog.Result.No => setLabel("I don't like them, either.")
          case _ => setLabel("Come on -- 'fess up!")
        }

      case ButtonClicked(`showItButton`) if group.selected == Some(yncCommandButton)  =>
        Dialog.showOptions(this,
          "Would you like some green eggs to go with that ham?",
          "A Silly Question",
          Dialog.Options.YesNoCancel,
          Dialog.Message.Question,
          null,
          Array("Yes, please", "No, thanks", "No eggs, no ham!"),
          2) match {
          case Dialog.Result.Yes => setLabel("Here you go: green eggs and ham!")
          case Dialog.Result.No => setLabel("OK, just the ham, then.")
          case Dialog.Result.Cancel => setLabel("Well, I'm certainly not going to eat them!")
          case _ => setLabel("Please tell me what you want!")
        }
    }
    createPane(simpleDialogDesc + ":", radioButtons, showItButton)
  }

  /**
   * Used by createSimpleDialogBox and createFeatureDialogBox
   * to create a pane containing a description, a single column
   * of radio buttons, and the Show it! button.
   */
  private def createPane(description: String, radioButtons: Array[RadioButton], showButton: Button): BorderPanel = {

    val box: BoxPanel = new BoxPanel(Orientation.Vertical) {
      contents +=  new Label(description)
      radioButtons.foreach( contents += _ )
    }

    new BorderPanel() {
      layout(box) = BorderPanel.Position.North
      layout(showButton) = BorderPanel.Position.South
    }
  }

  /**
   * Like createPane, but creates a pane with 2 columns of radio
   * buttons.  The number of buttons passed in  *must* be even.
   */
  private def create2ColPane(description: String,
    radioButtons: Array[RadioButton],
    showButton: Button): BorderPanel = {
    val label = new Label(description)
    val numPerColumn = radioButtons.length / 2
    val grid = new GridPanel(numPerColumn, 2)
    for (i <- 0 until radioButtons.length) {
      grid.contents += radioButtons(i)
    }
    val box = new BoxPanel(Orientation.Vertical) {
      contents += label
      grid.xLayoutAlignment = 0.0f
      contents += grid
    }

    new BorderPanel() {
      layout(box) = BorderPanel.Position.North
      layout(showButton) = BorderPanel.Position.South
    }
  }
  /*
     * Creates the panel shown by the 3rd tab.
     * These dialogs are implemented using showMessageDialog, but
     * you can specify the icon (using similar code) for any other
     * kind of dialog, as well.
     */
  def createIconDialogBox(): BorderPanel = {
    val plainCommandButton = new RadioButton("Plain (no icon)") { selected = true }
    val infoCommandButton = new RadioButton("Information icon")
    val questionCommandButton = new RadioButton("Question icon")
    val errorCommandButton = new RadioButton("Error icon")
    val warningCommandButton = new RadioButton("Warning icon")
    val customCommandButton = new RadioButton("Custom icon")

    val radioButtons = Array(
      plainCommandButton,
      infoCommandButton,
      questionCommandButton,
      errorCommandButton,
      warningCommandButton,
      customCommandButton
    )

    val group: ButtonGroup = new ButtonGroup() {
      radioButtons.foreach( buttons += _ )
    }
    val showItButton = new Button("Show it!")

    listenTo(showItButton)

    reactions += {
      case ButtonClicked(`showItButton`) if group.selected == Some(plainCommandButton) =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "A plain message",
              Dialog.Message.Plain)

      case ButtonClicked(`showItButton`) if group.selected == Some(infoCommandButton) =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane informational dialog",
              Dialog.Message.Info)

      case ButtonClicked(`showItButton`) if group.selected == Some(questionCommandButton) =>
            Dialog.showMessage(this,
              "You shouldn't use a message dialog " +
                "(like this)\n" +
                "for a question, OK?",
              "Inane question",
              Dialog.Message.Question)

      case ButtonClicked(`showItButton`) if group.selected == Some(errorCommandButton) =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane error",
              Dialog.Message.Error)

      case ButtonClicked(`showItButton`) if group.selected == Some(warningCommandButton)  =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane warning",
              Dialog.Message.Warning)

      case ButtonClicked(`showItButton`) if group.selected == Some(customCommandButton)  =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane custom dialog",
              Dialog.Message.Info,
              icon)
      }

    create2ColPane(iconDesc + ":", radioButtons, showItButton)
  }

  /** Creates the panel shown by the second tab. */
  private def createFeatureDialogBox(): BorderPanel = {
//    val pickOneCommand = "pickone"
//    val textEnteredCommand = "textfield"
//    val nonAutoCommand = "nonautooption"
//    val customOptionCommand = "customoption"
//    val nonModalCommand = "nonmodal"

    val pickOneCommandButton = new RadioButton("Pick one of several choices") { selected = true }

    val textEnteredCommandButton = new RadioButton("Enter some text")
    val nonAutoCommandButton = new RadioButton("Non-auto-closing dialog")
    val customOptionCommandButton = new RadioButton("Input-validating dialog (with custom message area)")
    val nonModalCommandButton = new RadioButton("Non-modal dialog")

    val radioButtons = Array(
      pickOneCommandButton,
      textEnteredCommandButton,
      nonAutoCommandButton,
      customOptionCommandButton,
      nonModalCommandButton
    )

    val group: ButtonGroup = new ButtonGroup() {
      radioButtons.foreach( buttons += _ )
    }

    val showItButton = new Button("Show it!")

    listenTo(showItButton)

    reactions += {
      case ButtonClicked(`showItButton`) if group.selected == Some(pickOneCommandButton)    =>
        Dialog.showInput[String](this,
          "Complete the sentence:\n" +
            "\"Green eggs and...\"",
          "Customized Dialog",
          Dialog.Message.Plain,
          icon,
          Array("ham", "spam", "yam"),
          "ham") match {
          case Some(msg) if msg.length > 1 => setLabel(s"Green eggs and...$msg!")
          case _ => setLabel("Come on, finish the sentence!")
        }

      case ButtonClicked(`showItButton`) if group.selected == Some(textEnteredCommandButton) =>
        Dialog.showInput[String](this,
          "Complete the sentence:\n" +
            "\"Green eggs and...\"",
          "Customized Dialog",
          Dialog.Message.Plain,
          icon,
          Nil,
          "ham") match {
          case Some(msg) if msg.length > 1 => setLabel(s"Green eggs and...$msg!")
          case _ => setLabel("Come on, finish the sentence!")
        }

      case ButtonClicked(`showItButton`) if group.selected == Some(nonAutoCommandButton) =>
          setLabel("Not supported.")
        //non-auto-closing dialog with custom message area
        //NOTE: if you don't intend to check the input,
        //then just use showInputDialog instead.

      case ButtonClicked(`showItButton`) if group.selected == Some(customOptionCommandButton) =>
          setLabel("Not supported.")

      case ButtonClicked(`showItButton`) if group.selected == Some(nonModalCommandButton) =>
        val dialog = new Dialog(frame, frame.peer.getGraphicsConfiguration) {
          title = "A Non-Modal Dialog"

          val label: Label = new Label("<html><p align=center>" +
            "This is a non-modal dialog.<br>" +
            "You can have one or more of these up<br>" +
            "and still use the main window.") {
            horizontalAlignment = Alignment.Center
            font = font.deriveFont(Font.PLAIN, 14.0f)
          }
          val closeButton: Button = new Button("Close")

          val closePanel = new BoxPanel(Orientation.Horizontal) {
            contents += Swing.HGlue
            contents += closeButton
            border = Swing.EmptyBorder(0, 0, 5, 5)
          }

          val contentPane: BorderPanel = new BorderPanel() {
            layout(label) = BorderPanel.Position.Center
            layout(closePanel) = BorderPanel.Position.South
            opaque = true
          }
          contents = contentPane
          listenTo(closeButton)
          reactions += {
            case ButtonClicked(`closeButton`) =>
              visible = false
              dispose()
          }
        }
        dialog.size = new Dimension(300, 150)
        dialog.peer.setLocationRelativeTo(frame.peer)
        dialog.visible = true
      }
    createPane(moreDialogDesc + ":", radioButtons, showItButton)
  }

}

object DialogDemo extends SimpleSwingApplication {
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new MainFrame() {
    title = "DialogDemo"
    //Create and set up the content pane.
    contents = new DialogDemo(this)
  }
}
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
import java.net.URL
import javax.swing.ImageIcon
import java.awt.{ Dimension, Font }

/*
 * Tutorial: How to Use Dialogs
 * http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/DialogDemoProject/src/components/DialogDemo.java
 *
 * DialogDemo.scala requires these files:
 *   CustomDialog.scala
 *   /scala/swing/examples/tutorials/images/middle.gif
 */
class DialogDemo(val frame: Frame) extends BorderPanel {
  val icon: ImageIcon = DialogDemo.createImageIcon("/scala/swing/examples/tutorials/images/middle.gif")
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
    val numButtons = 4
    val radioButtons: Array[RadioButton] = new Array[RadioButton](numButtons)

    val showItButton: Button = new Button("Show it!")

    val defaultMessageCommand = "default"
    val yesNoCommand = "yesno"
    val yeahNahCommand = "yeahnah"
    val yncCommand = "ync"

    val group: ButtonGroup = new ButtonGroup() {

    }

    radioButtons(0) = new RadioButton("OK (in the L&F's words)") {
      selected = true
    }

    // radioButtons[0].setActionCommand(defaultMessageCommand)

    radioButtons(1) = new RadioButton("Yes/No (in the L&F's words)")
    // radioButtons[1].setActionCommand(yesNoCommand)

    radioButtons(2) = new RadioButton("Yes/No "
      + "(in the programmer's words)")
    // radioButtons[2].setActionCommand(yeahNahCommand)

    radioButtons(3) = new RadioButton("Yes/No/Cancel "
      + "(in the programmer's words)")
    // radioButtons[3].setActionCommand(yncCommand)

    val defaultMessageCommandButton = radioButtons(0)
    val yesNoCommandButton = radioButtons(1)
    val yeahNahCommandButton = radioButtons(2)
    val yncCommandButton = radioButtons(3)

    for (i <- 0 until numButtons) {
      group.buttons += radioButtons(i)
    }

    var actionCommand = defaultMessageCommand
    listenTo(showItButton)
    listenTo(defaultMessageCommandButton)
    listenTo(yesNoCommandButton)
    listenTo(yeahNahCommandButton)
    listenTo(yncCommandButton)
    reactions += {
      case ButtonClicked(`defaultMessageCommandButton`) =>
        actionCommand = defaultMessageCommand
      case ButtonClicked(`yesNoCommandButton`) =>
        actionCommand = yesNoCommand
      case ButtonClicked(`yeahNahCommandButton`) =>
        actionCommand = yeahNahCommand
      case ButtonClicked(`yncCommandButton`) =>
        actionCommand = yncCommand
      case ButtonClicked(`showItButton`) =>
        actionCommand match {
          //ok dialog
          case "default" =>
            val command: String = defaultMessageCommand
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.")
          //yes/no dialog
          case "yesno" =>
            val n = Dialog.showConfirmation(
              this, "Would you like green eggs and ham?",
              "An Inane Question",
              Dialog.Options.YesNo)

            if (n == Dialog.Result.Yes) {
              setLabel("Ewww!");
            } else if (n == Dialog.Result.No) {
              setLabel("Me neither!");
            } else {
              setLabel("Come on -- tell me!");
            }
          //yes/no (not in those words)
          case "yeahnah" =>
            val options: Array[String] = Array("Yes, please", "No way!")
            val n = Dialog.showOptions(this,
              "Would you like green eggs and ham?",
              "A Silly Question",
              Dialog.Options.YesNo,
              Dialog.Message.Question,
              Swing.EmptyIcon,
              options,
              0)
            if (n == Dialog.Result.Yes) {
              setLabel("You're kidding!")
            } else if (n == Dialog.Result.No) {
              setLabel("I don't like them, either.")
            } else {
              setLabel("Come on -- 'fess up!")
            }
          //yes/no/cancel (not in those words)
          case "ync" =>
            val options: Array[String] = Array("Yes, please",
              "No, thanks",
              "No eggs, no ham!")
            val n = Dialog.showOptions(this,
              "Would you like some green eggs to go "
                + "with that ham?",
              "A Silly Question",
              Dialog.Options.YesNoCancel,
              Dialog.Message.Question,
              null,
              options,
              2)
            if (n == Dialog.Result.Yes) {
              setLabel("Here you go: green eggs and ham!")
            } else if (n == Dialog.Result.No) {
              setLabel("OK, just the ham, then.")
            } else if (n == Dialog.Result.Cancel) {
              setLabel("Well, I'm certainly not going to eat them!")
            } else {
              setLabel("Please tell me what you want!")
            }
        }
    }
    createPane(simpleDialogDesc + ":",
      radioButtons,
      showItButton)
  }

  /**
   * Used by createSimpleDialogBox and createFeatureDialogBox
   * to create a pane containing a description, a single column
   * of radio buttons, and the Show it! button.
   */
  private def createPane(description: String,
    radioButtons: Array[RadioButton],
    showButton: Button): BorderPanel = {

    val numChoices = radioButtons.length
    val label = new Label(description)
    val box: BoxPanel = new BoxPanel(Orientation.Vertical) {
      contents += label
    }

    for (i <- 0 until numChoices) {
      box.contents += radioButtons(i);
    }

    val pane = new BorderPanel() {
      layout(box) = BorderPanel.Position.North
      layout(showButton) = BorderPanel.Position.South
    }
    pane
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
    val pane = new BorderPanel() {
      layout(box) = BorderPanel.Position.North
      layout(showButton) = BorderPanel.Position.South
    }
    pane
  }
  /*
     * Creates the panel shown by the 3rd tab.
     * These dialogs are implemented using showMessageDialog, but
     * you can specify the icon (using similar code) for any other
     * kind of dialog, as well.
     */
  def createIconDialogBox(): BorderPanel = {
    val numButtons = 6
    val radioButtons: Array[RadioButton] = new Array[RadioButton](numButtons)
    val plainCommand = "plain"
    val infoCommand = "info"
    val questionCommand = "question"
    val errorCommand = "error"
    val warningCommand = "warning"
    val customCommand = "custom"
    radioButtons(0) = new RadioButton("Plain (no icon)") {
      selected = true
    }
    val plainCommandButton = radioButtons(0)
    radioButtons(1) = new RadioButton("Information icon")
    val infoCommandButton = radioButtons(1)
    radioButtons(2) = new RadioButton("Question icon")
    val questionCommandButton = radioButtons(2)
    radioButtons(3) = new RadioButton("Error icon")
    val errorCommandButton = radioButtons(3)
    radioButtons(4) = new RadioButton("Warning icon")
    val warningCommandButton = radioButtons(4)
    radioButtons(5) = new RadioButton("Custom icon")
    val customCommandButton = radioButtons(5)

    val group: ButtonGroup = new ButtonGroup() {
      for (i <- 0 until numButtons) {
        buttons += radioButtons(i)
      }
    }
    val showItButton = new Button("Show it!")
    var actionCommand = plainCommand
    listenTo(plainCommandButton)
    listenTo(infoCommandButton)
    listenTo(questionCommandButton)
    listenTo(errorCommandButton)
    listenTo(warningCommandButton)
    listenTo(customCommandButton)
    listenTo(showItButton)
    reactions += {
      case ButtonClicked(`plainCommandButton`) => actionCommand = plainCommand
      case ButtonClicked(`infoCommandButton`) => actionCommand = infoCommand
      case ButtonClicked(`questionCommandButton`) => actionCommand = questionCommand
      case ButtonClicked(`errorCommandButton`) => actionCommand = errorCommand
      case ButtonClicked(`warningCommandButton`) => actionCommand = warningCommand
      case ButtonClicked(`customCommandButton`) => actionCommand = customCommand
      case ButtonClicked(`showItButton`) =>
        actionCommand match {
          //no icon
          case "plain" =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "A plain message",
              Dialog.Message.Plain)
          //information icon
          case "info" =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane informational dialog",
              Dialog.Message.Info)
          //question icon
          case "question" =>
            Dialog.showMessage(this,
              "You shouldn't use a message dialog " +
                "(like this)\n" +
                "for a question, OK?",
              "Inane question",
              Dialog.Message.Question)
          //error icon
          case "error" =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane error",
              Dialog.Message.Error)
          //warning icon
          case "warning" =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane warning",
              Dialog.Message.Warning)
          //custom icon
          case "custom" =>
            Dialog.showMessage(this,
              "Eggs aren't supposed to be green.",
              "Inane custom dialog",
              Dialog.Message.Info,
              icon)
        }
    }
    create2ColPane(iconDesc + ":", radioButtons, showItButton)
  }

  /** Creates the panel shown by the second tab. */
  private def createFeatureDialogBox(): BorderPanel = {
    val numButtons = 5
    val radioButtons: Array[RadioButton] = new Array[RadioButton](numButtons)
    val pickOneCommand = "pickone"
    val textEnteredCommand = "textfield"
    val nonAutoCommand = "nonautooption"
    val customOptionCommand = "customoption"
    val nonModalCommand = "nonmodal"
    radioButtons(0) = new RadioButton("Pick one of several choices") {
      selected = true
    }
    val pickOneCommandButton = radioButtons(0)
    radioButtons(1) = new RadioButton("Enter some text")
    val textEnteredCommandButton = radioButtons(1)
    radioButtons(2) = new RadioButton("Non-auto-closing dialog")
    val nonAutoCommandButton = radioButtons(2)
    radioButtons(3) = new RadioButton("Input-validating dialog " +
      "(with custom message area)")
    val customOptionCommandButton = radioButtons(3)
    radioButtons(4) = new RadioButton("Non-modal dialog")
    val nonModalCommandButton = radioButtons(4)

    val group: ButtonGroup = new ButtonGroup() {
      for (i <- 0 until numButtons) {
        buttons += radioButtons(i)
      }
    }
    val showItButton = new Button("Show it!")
    var actionCommand = pickOneCommand
    listenTo(pickOneCommandButton)
    listenTo(textEnteredCommandButton)
    listenTo(nonAutoCommandButton)
    listenTo(customOptionCommandButton)
    listenTo(nonModalCommandButton)
    listenTo(showItButton)
    reactions += {
      case ButtonClicked(`pickOneCommandButton`) => actionCommand = pickOneCommand
      case ButtonClicked(`textEnteredCommandButton`) => actionCommand = textEnteredCommand
      case ButtonClicked(`nonAutoCommandButton`) => actionCommand = nonAutoCommand
      case ButtonClicked(`customOptionCommandButton`) => actionCommand = customOptionCommand
      case ButtonClicked(`nonModalCommandButton`) => actionCommand = nonModalCommand
      case ButtonClicked(`showItButton`) =>
        actionCommand match {
          //pick one of many
          case "pickone" =>
            val possibilities = Array("ham", "spam", "yam")
            val s: Option[String] = Dialog.showInput[String](this,
              "Complete the sentence:\n" +
                "\"Green eggs and...\"",
              "Customized Dialog",
              Dialog.Message.Plain,
              icon,
              possibilities,
              "ham")
            if (s.isDefined && s.get.length > 0) {
              setLabel("Green eggs and..." + s.get + "!")
            } else {
              setLabel("Come on, finish the sentence!")
            }
          //text input
          case "textfield" =>
            val s: Option[String] = Dialog.showInput[String](this,
              "Complete the sentence:\n" +
                "\"Green eggs and...\"",
              "Customized Dialog",
              Dialog.Message.Plain,
              icon,
              Nil,
              "ham")
            if (s.isDefined && s.get.length > 0) {
              setLabel("Green eggs and..." + s.get + "!")
            } else {
              setLabel("Come on, finish the sentence!")
            }
          //non-auto-closing dialog
          case "nonautooption" =>
            setLabel("Not supported.")
          //non-auto-closing dialog with custom message area
          //NOTE: if you don't intend to check the input,
          //then just use showInputDialog instead.
          case "customoption" =>
            setLabel("Not supported.")
          //non-modal dialog
          case "nonmodal" =>
            val dialog = new Dialog(frame, frame.peer.getGraphicsConfiguration()) {
              title = "A Non-Modal Dialog"

              val label: Label = new Label("<html><p align=center>" +
                "This is a non-modal dialog.<br>" +
                "You can have one or more of these up<br>" +
                "and still use the main window.") {
                horizontalAlignment = Alignment.Center
                val myFont = font
                font = myFont.deriveFont(Font.PLAIN, 14.0f)
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
    }
    createPane(moreDialogDesc + ":", radioButtons, showItButton)
  }

}

object DialogDemo extends SimpleSwingApplication {
  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String): ImageIcon = {
    val imgURL: URL = getClass().getResource(path)
    if (imgURL != null) {
      Swing.Icon(imgURL)
    } else {
      null
    }
  }

  def top = new MainFrame() {
    title = "DialogDemo"
    //Create and set up the content pane.
    contents = new DialogDemo(this);
  }
}
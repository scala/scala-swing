package scala.swing.examples.tutorials.components

import scala.swing._

/**
 * Tutorial: How to Use Password Fields
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/PasswordDemoProject/src/components/PasswordDemo.java]]
 *
 * PasswordDemo.scala requires no other files.
 */
class PasswordDemo(val controllingFrame: Frame) extends FlowPanel {
  val passwordField = new PasswordField(10)
  val label = new Label("Enter the password: ")

  val buttonPane: Component = createButtonPanel()
  val textPane  : Component = new FlowPanel {
    contents += label
    contents += passwordField
  }

  def createButtonPanel(): GridPanel = {
    new GridPanel(0, 1) {
      val okButton = new Button(Action("OK") {
        val input: Array[Char] = passwordField.password
        if (PasswordDemo.isPasswordCorrect(input)) {
          Dialog.showMessage(this,
            "Success! You typed the right password.",
            "Passord Success", Dialog.Message.Info, Swing.EmptyIcon)
        } else {
          Dialog.showMessage(this,
            "Invalid password. Try again.",
            "Error Message",
            Dialog.Message.Error, Swing.EmptyIcon)
        }
        //Zero out the possible password, for security.
        for (i <- input.indices) {
          input(i) = '0'
        }
        passwordField.selectAll()
        passwordField.requestFocusInWindow()
      })

      val helpButton = new Button(Action("Help") {
        Dialog.showMessage(this,
          "You can get the password by searching this example's\n"
            + "source code for the string \"correctPassword\".\n"
            + "Or look at the section How to Use Password Fields in\n"
            + "the components section of The Java Tutorial.",
          "Password Help", Dialog.Message.Info, Swing.EmptyIcon)
      })

      contents += okButton
      contents += helpButton

      listenTo(okButton)
      listenTo(helpButton)
    }
  }
}

object PasswordDemo extends SimpleSwingApplication {
  /**
   * Checks the passed-in array against the correct password.
   * After this method returns, you should invoke eraseArray
   * on the passed-in array.
   */
  def isPasswordCorrect(input: Array[Char]): Boolean = {
    val correctPassword = Array[Char]('b', 'u', 'g', 'a', 'b', 'o', 'o')
    val isCorrect = input.sameElements(correctPassword)

    //Zero out the password.
    // Arrays.fill(correctPassword,'0');
    for (i <- correctPassword.indices) {
      correctPassword(i) = '0'
    }
    isCorrect
  }
  lazy val top: Frame = new MainFrame() {
    title = "PasswordDemo"
    //Create and set up the content pane.
    contents = new PasswordDemo(this)
  }
}
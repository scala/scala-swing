package scala.swing.examples

import scala.swing._

/**
 * A simple swing demo.
 */
object HelloWorld extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Hello, World!"
    contents = new Button("Click Me!")
  }
}

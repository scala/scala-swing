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

/**
 * A simple swing demo.
 */
object HelloWorld extends SimpleSwingApplication {
  def top: Frame = new MainFrame {
    title = "Hello, World!"
    contents = new Button("Click Me!")
  }
}

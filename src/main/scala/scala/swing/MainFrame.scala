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

package scala.swing

/**
 * A frame that can be used for main application windows. It shuts down the
 * framework and quits the application when closed.
 */
class MainFrame(gc: java.awt.GraphicsConfiguration = null) extends Frame(gc) {
  override def closeOperation(): Unit = sys.exit(0)
}

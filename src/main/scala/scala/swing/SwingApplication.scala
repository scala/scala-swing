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

/** Convenience class with utility methods for GUI applications. */
abstract class SwingApplication extends Reactor {

  /** Initializes the application and runs the given program. */
  def main(args: Array[String]): Unit = Swing.onEDT { startup(args) }

  /** Called before the GUI is created. Override to customize. */
  def startup(args: Array[String]): Unit

  /** Finalizes the application by calling `shutdown` and exits.*/
  def quit(): Unit = { shutdown(); sys.exit(0) }

  /** Called before the application is exited. Override to customize. */
  def shutdown(): Unit = ()
}

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

import javax.swing.JToggleButton

/**
 * A two state button with a push button like user interface.
 * Usually used in tool bars.
 *
 * @see javax.swing.JToggleButton
 */
class ToggleButton(text0: String) extends AbstractButton {
  override lazy val peer: JToggleButton = new JToggleButton(text0) with SuperMixin
  def this() = this("")
}

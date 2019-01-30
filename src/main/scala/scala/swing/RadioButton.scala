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

import javax.swing.JRadioButton

/**
 * A two state button that is usually used in a <code>ButtonGroup</code>
 * together with other <code>RadioButton</code>s, in order to indicate
 * that at most one of them can be selected.
 *
 * @see javax.swing.JRadioButton
 */
class RadioButton(text0: String) extends ToggleButton {
  override lazy val peer: JRadioButton = new JRadioButton(text0) with SuperMixin
  def this() = this("")
}

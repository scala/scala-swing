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

import javax.swing.JFormattedTextField

object FormattedTextField {
  /**
   * The behavior of a formatted text field when it loses its focus.
   */
  object FocusLostBehavior extends Enumeration {
    import JFormattedTextField._
    val Commit        : FocusLostBehavior.Value = Value(COMMIT)
    val CommitOrRevert: FocusLostBehavior.Value = Value(COMMIT_OR_REVERT)
    val Persist       : FocusLostBehavior.Value = Value(PERSIST)
    val Revert        : FocusLostBehavior.Value = Value(REVERT)
  }
}

/**
 * A text field with formatted input.
 *
 * @see javax.swing.JFormattedTextField
 */
class FormattedTextField(format: java.text.Format) extends TextComponent {
  override lazy val peer: JFormattedTextField = new JFormattedTextField(format) with SuperMixin

  import FormattedTextField._

  def commitEdit(): Unit = peer.commitEdit()
  def editValid: Boolean = peer.isEditValid

  def focusLostBehavior: FocusLostBehavior.Value = FocusLostBehavior(peer.getFocusLostBehavior)
  def focusLostBehavior_=(b: FocusLostBehavior.Value): Unit = peer.setFocusLostBehavior(b.id)
}

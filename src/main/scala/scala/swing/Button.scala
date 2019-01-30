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

import javax.swing.JButton

object Button {
  def apply(text0: String)(op: => Unit): Button = new Button(Action(text0)(op))
}

/**
 * A button that can be clicked, usually to perform some action.
 *
 * @see javax.swing.JButton
 */
class Button(text0: String) extends AbstractButton with Publisher {
  override lazy val peer: JButton = new JButton(text0) with SuperMixin
  def this() = this("")
  def this(a: Action) = {
    this("")
    action = a
  }

  def defaultButton: Boolean = peer.isDefaultButton

  def defaultCapable: Boolean = peer.isDefaultCapable
  def defaultCapable_=(capable: Boolean): Unit = peer.setDefaultCapable(capable)
}

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

import javax.swing.JPasswordField

/**
 * A password field, that displays a replacement character for each character in the password.
 *
 * @see javax.swing.JPasswordField
 */
class PasswordField(text0: String, columns0: Int) extends TextField(text0, columns0) {
  override lazy val peer: JPasswordField = new JPasswordField(text0, columns0) with SuperMixin
  def this(text: String) = this(text, 0)
  def this(columns: Int) = this("", columns)
  def this() = this("")

  def echoChar: Char = peer.getEchoChar
  def echoChar_=(c: Char): Unit = peer.setEchoChar(c)

  /**
   * The text property should not be used on a password field for
   * security reasons.
   */
  override def text: String = ""
  override def text_=(s: String): Unit = ()
  def password: Array[Char] = peer.getPassword
}

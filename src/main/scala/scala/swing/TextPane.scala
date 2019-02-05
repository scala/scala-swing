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
 * A text component used to model styled paragraphs of text.
 * 
 * @see javax.swing.JTextPane
 */
class TextPane extends TextComponent {
  override lazy val peer: javax.swing.JTextPane = new javax.swing.JTextPane() with SuperMixin
  def styledDocument: javax.swing.text.StyledDocument = peer.getStyledDocument
}
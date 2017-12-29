/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */
package scala.swing

/**
 * A text component used to model styled paragraphs of text.
 * 
 * @see javax.swing.JTextPane
 */
class TextPane() extends TextComponent {
  override lazy val peer: javax.swing.JTextPane = new javax.swing.JTextPane() with SuperMixin
  def styledDocument: javax.swing.text.StyledDocument = peer.getStyledDocument
}
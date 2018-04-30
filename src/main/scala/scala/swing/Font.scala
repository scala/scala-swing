/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.awt.{Font => JFont}

object Font {
  def apply(name: String, style: Style.Value, size: Int): Font =
    new JFont(name, style.id, size)

  /** A String constant for the canonical family name of the
    * logical font "Dialog". It is useful in Font construction
    * to provide compile-time verification of the name.
    */
  val Dialog: String = JFont.DIALOG

  /** A String constant for the canonical family name of the
    * logical font "DialogInput". It is useful in Font construction
    * to provide compile-time verification of the name.
    */
  val DialogInput: String = JFont.DIALOG_INPUT

  /** A String constant for the canonical family name of the
    * logical font "SansSerif". It is useful in Font construction
    * to provide compile-time verification of the name.
    */
  val SansSerif: String = JFont.SANS_SERIF

  /** A String constant for the canonical family name of the
    * logical font "Serif". It is useful in Font construction
    * to provide compile-time verification of the name.
    */
  val Serif: String = JFont.SERIF

  /** A String constant for the canonical family name of the
    * logical font "Monospaced". It is useful in Font construction
    * to provide compile-time verification of the name.
    */
  val Monospaced: String = JFont.MONOSPACED

  // Constants to be used for styles. Can be combined to mix styles.

  object Style extends Enumeration {
    val Plain     : Style.Value = Value(JFont.PLAIN)
    val Bold      : Style.Value = Value(JFont.BOLD)
    val Italic    : Style.Value = Value(JFont.ITALIC)
    val BoldItalic: Style.Value = Value(JFont.BOLD | JFont.ITALIC)
  }

  // for convenience, have these values also directly in the `Font` name space
  val Plain     : Style.Value = Style.Plain
  val Bold      : Style.Value = Style.Bold
  val Italic    : Style.Value = Style.Italic
  val BoldItalic: Style.Value = Style.BoldItalic
}

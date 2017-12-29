/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.swing
package event

import javax.swing.JComponent

sealed abstract class KeyEvent extends InputEvent {
  def peer: java.awt.event.KeyEvent
}

case class KeyTyped(source: Component, char: Char, modifiers: Key.Modifiers,
                    location: Key.Location.Value)
                   (val peer: java.awt.event.KeyEvent) extends KeyEvent {
  def this(e: java.awt.event.KeyEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        e.getKeyChar, e.getModifiersEx,
        Key.Location(e.getKeyLocation))(e)
}

case class KeyPressed(source: Component, key: Key.Value, modifiers: Key.Modifiers,
                      location: Key.Location.Value)
                   (val peer: java.awt.event.KeyEvent) extends KeyEvent {
  def this(e: java.awt.event.KeyEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        Key(e.getKeyCode), e.getModifiersEx, Key.Location(e.getKeyLocation))(e)
}

case class KeyReleased(source: Component, key: Key.Value, modifiers: Key.Modifiers,
                       location: Key.Location.Value)
                   (val peer: java.awt.event.KeyEvent) extends KeyEvent {
  def this(e: java.awt.event.KeyEvent) =
    this(UIElement.cachedWrapper[Component](e.getSource.asInstanceOf[JComponent]),
        Key(e.getKeyCode), e.getModifiersEx, Key.Location(e.getKeyLocation))(e)
}

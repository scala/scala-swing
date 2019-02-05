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

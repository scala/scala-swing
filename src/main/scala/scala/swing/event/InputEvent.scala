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

trait InputEvent extends ComponentEvent {
  def peer: java.awt.event.InputEvent
  def when: Long = peer.getWhen
  def modifiers: Key.Modifiers
  def consume(): Unit = peer.consume()
  def consumed: Boolean = peer.isConsumed
}

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

trait UIEvent extends Event {
  val source: UIElement
}

case class UIElementMoved(source: UIElement) extends UIEvent
case class UIElementResized(source: UIElement) extends UIEvent
case class UIElementShown(source: UIElement) extends UIEvent
case class UIElementHidden(source: UIElement) extends UIEvent

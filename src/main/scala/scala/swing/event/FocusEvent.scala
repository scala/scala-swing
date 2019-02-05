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

/**
 * The other component is None if it is a non Swing, i.e., AWT or native, component.
 */
abstract class FocusEvent(override val source: Component, val other: Option[Component], val temporary: Boolean) extends ComponentEvent

case class FocusGained(override val source: Component, override val other: Option[Component], override val temporary: Boolean)
           extends FocusEvent(source, other, temporary)

case class FocusLost(override val source: Component, override val other: Option[Component], override val temporary: Boolean)
           extends FocusEvent(source, other, temporary)

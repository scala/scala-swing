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

abstract class ContainerEvent(override val source: Container) extends UIEvent

case class ComponentAdded(override val source: Container, child: Component) extends ContainerEvent(source)
case class ComponentRemoved(override val source: Container, child: Component) extends ContainerEvent(source)

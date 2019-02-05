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

import javax.swing.JSeparator

/**
 * A bar that can be used a separator, most commonly in menus.
 *
 * @see javax.swing.JSeparator
 */
class Separator(o: Orientation.Value) extends Component with Oriented.Wrapper {
  override lazy val peer: JSeparator = new JSeparator(o.id) with SuperMixin
  def this() = this(Orientation.Horizontal)
}

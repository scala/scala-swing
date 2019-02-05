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

object Orientable {
  trait Wrapper extends Oriented.Wrapper with Orientable {
    def orientation_=(o: Orientation.Value): Unit = peer.setOrientation(o.id)
  }
}

/**
 * An <code>Oriented</code> whose orientation can be changed.
 */
trait Orientable extends Oriented {
  def orientation_=(o: Orientation.Value): Unit
}

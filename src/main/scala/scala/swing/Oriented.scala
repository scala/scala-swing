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

object Oriented {
  trait Wrapper extends Oriented {
    def peer: OrientedMixin

    /*
     * Need to revert to structural type, since scroll bars are oriented
     * and these are created by scroll panes. Shouldn't be a bottleneck.
     */
    protected type OrientedMixin = {
      def getOrientation(): Int // note: must keep empty parentheses for Java compatibility
      def setOrientation(n: Int): Unit
    }
    def orientation: Orientation.Value = Orientation(peer.getOrientation())
  }
}

/**
 * Something that can have an orientation.
 */
trait Oriented {
  def orientation: Orientation.Value
}

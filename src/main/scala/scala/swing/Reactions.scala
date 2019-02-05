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

import scala.collection.mutable
import scala.swing.event.Event

object Reactions {
  class Impl extends Reactions {
    private val parts: mutable.Buffer[Reaction] = new mutable.ListBuffer[Reaction]
    def isDefinedAt(e: Event): Boolean = parts.exists(_ isDefinedAt e)
    def += (r: Reaction): this.type = { parts += r; this }
    def -= (r: Reaction): this.type = { parts -= r; this }
    def apply(e: Event): Unit = {
      for (p <- parts) if (p isDefinedAt e) p(e)
    }
  }

  type Reaction = PartialFunction[Event, Unit]

  /**
   * A Reaction implementing this trait is strongly referenced in the reaction list
   */
  trait StronglyReferenced

  class Wrapper(listener: Any)(r: Reaction) extends Reaction with StronglyReferenced with Proxy {
    def self: Any = listener
    def isDefinedAt(e: Event): Boolean = r.isDefinedAt(e)
    def apply(e: Event): Unit = r(e)
  }
}

/**
 * Used by reactors to let clients register custom event reactions.
 */
abstract class Reactions extends Reactions.Reaction {
  /**
   * Add a reaction.
   */
  def += (r: Reactions.Reaction): this.type

  /**
   * Remove the given reaction.
   */
  def -= (r: Reactions.Reaction): this.type
}

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

/**
 * The counterpart to publishers. Listens to events from registered publishers.
 */
trait Reactor {
  /**
   * All reactions of this reactor.
   */
  val reactions: Reactions = new Reactions.Impl
  /**
   * Listen to the given publisher as long as <code>deafTo</code> isn't called for
   * them.
   */
  def listenTo(ps: Publisher*): Unit = for (p <- ps) p.subscribe(reactions)
  /**
   * Installed reaction won't receive events from the given publisher anylonger.
   */
  def deafTo(ps: Publisher*): Unit = for (p <- ps) p.unsubscribe(reactions)
}

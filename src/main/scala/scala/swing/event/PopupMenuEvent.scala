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

abstract class PopupMenuEvent extends ComponentEvent

case class PopupMenuCanceled(source: PopupMenu) extends PopupMenuEvent
case class PopupMenuWillBecomeInvisible(source: PopupMenu) extends PopupMenuEvent
case class PopupMenuWillBecomeVisible(source: PopupMenu) extends PopupMenuEvent
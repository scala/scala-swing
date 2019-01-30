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
 * Like a menu, a way to map text or icons to actions;  but detachable from the rest of the user interface.
 */
class ToolBar extends scala.swing.Component with SequentialContainer.Wrapper {
  override lazy val peer: javax.swing.JToolBar = new javax.swing.JToolBar with SuperMixin
}
/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

/**
 * Like a menu, a way to map text or icons to actions;  but detachable from the rest of the user interface.
 */
class ToolBar extends scala.swing.Component with SequentialContainer.Wrapper {
  override lazy val peer: javax.swing.JToolBar = new javax.swing.JToolBar with SuperMixin
}
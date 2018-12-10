/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2018, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import scala.collection.mutable.{Growable, Shrinkable}

/**
 * Shims for changing collections API.
 */
protected[swing] trait MutableBufferShim[A] extends mutable.Buffer[A]

protected[swing] trait MutableSetShim[A] extends mutable.Set[A]

protected[swing] trait MutableMapShim[K, V] extends mutable.Map[K, V]

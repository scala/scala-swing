/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import scala.collection.mutable

/**
  * Default partial implementation for mutable set adapters.
  */
abstract class SetWrapper[A] extends mutable.Set[A] {
  /** The collection passed to `addAll` and `subtractAll` */
  type MoreElem[+B] = IterableOnce[B]

  /** Cross-version way for creating an iterator from `MoreElem`. */
  final protected def mkIterator[B](xs: MoreElem[B]): Iterator[B] = xs.iterator

  override def clear(): Unit = iterator.toList.foreach(remove)
}
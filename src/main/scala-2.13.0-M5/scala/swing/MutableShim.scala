/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2018, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import scala.collection.mutable

/**
 * Shims for changing collections API.
 */
protected[swing] trait MutableBufferShim[A] extends mutable.Buffer[A] {
  override def insert(i: Int, a: A) = insertAt(i, a)
  protected def insertAt(i: Int, a: A): Unit

  override def insertAll(n: Int, elems: IterableOnce[A]): Unit = insertAllImpl(n, elems.iterator)
  def insertAllImpl(n: Int, elems: Iterator[A]): Unit

  override def patchInPlace(from: Int, patch: scala.collection.Seq[A], replaced: Int) = {
    this
  }

  override def remove(idx: Int, count: Int) = {
    require(count >= 0)
    var n = 0
    while (n < count) {
      remove(idx + n)
      n += 1
    }
  }
}

protected[swing] trait MutableSetShim[A] extends mutable.Set[A] {
  def clear(): Unit = iterator.toList.foreach(remove)
}

protected[swing] trait MutableMapShim[K, V] extends mutable.Map[K, V]

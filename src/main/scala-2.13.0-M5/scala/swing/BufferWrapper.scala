/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import scala.collection.{Iterator, mutable}

/**
 * Default partial implementation for buffer adapters.
 */
protected[swing] abstract class BufferWrapper[A] extends mutable.Buffer[A] {
  def clear(): Unit = for (_ <- 0 until length) remove(0)

  def update(n: Int, a: A): Unit = {
    remove(n)
    insertAt(n, a)
  }

  def insertAll(n: Int, elems: Iterable[A]): Unit = {
    var i = n
    for (el <- elems) {
      insertAt(i, el)
      i += 1
    }
  }

  protected def insertAt(n: Int, a: A): Unit

// XXX TODO: remove
//  def +=:(a: A): this.type = { insertAt(0, a); this }

  def iterator: Iterator[A] = Iterator.range(0,length).map(apply)

  // XXX TODO
  def prepend(elem: A): BufferWrapper.this.type = ???

  def insert(idx: Int, elem: A): Unit = ???

  def insertAll(idx: Int, elems: IterableOnce[A]): Unit = ???

  def remove(idx: Int, count: Int): Unit = ???

  def patchInPlace(from: Int, patch: collection.Seq[A], replaced: Int): BufferWrapper.this.type = ???
}

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
protected[swing] abstract class BufferWrapper[A] extends mutable.Buffer[A] { outer =>
  def clear(): Unit = for (_ <- 0 until length) remove(0)

  def update(n: Int, a: A): Unit = {
    remove(n)
    insertAt(n, a)
  }

  def insertAll(n: Int, elems: Traversable[A]): Unit = {
    var i = n
    for (el <- elems) {
      insertAt(i, el)
      i += 1
    }
  }

  protected def insertAt(n: Int, a: A): Unit

  def +=:(a: A): this.type = { insertAt(0, a); this }

  def iterator: Iterator[A] = Iterator.range(0,length).map(apply)
}

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
abstract class BufferWrapper[A] extends mutable.Buffer[A] {
  // abstract

  def addOne(elem: A): this.type

  def insert(idx: Int, elem: A): Unit

  // impl

  type MoreElem[+B] = Traversable[B]

  final override def +=(elem: A): this.type = addOne(elem)

  override def clear(): Unit = for (_ <- 0 until length) remove(0)

  override def update(n: Int, a: A): Unit = {
    remove(n)
    insert(n, a)
  }

  override def insertAll(n: Int, elems: MoreElem[A]): Unit = {
    var i = n
    for (el <- elems) {
      insert(i, el)
      i += 1
    }
  }

  override def +=:(a: A): this.type = { insert(0, a); this }

  override def iterator: Iterator[A] = Iterator.range(0,length).map(apply)
}

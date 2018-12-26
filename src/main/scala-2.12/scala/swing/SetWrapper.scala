/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import scala.annotation.tailrec
import scala.collection.mutable

/**
  * Default partial implementation for mutable set adapters.
  */
abstract class SetWrapper[A] extends mutable.Set[A] {
  // abstract

  def addOne      (elem: A): this.type
  def subtractOne (elem: A): this.type

  // impl

  final def +=(elem: A): this.type = addOne     (elem)
  final def -=(elem: A): this.type = subtractOne(elem)

  /** The collection passed to `addAll` and `subtractAll` */
  type MoreElem[+B] = TraversableOnce[B]

  /** Cross-version way for creating an iterator from `MoreElem`. */
  final protected def mkIterator[B](xs: MoreElem[B]): Iterator[B] = xs.toIterator

  final override def ++=(xs: MoreElem[A]): this.type = addAll     (xs)
  final override def --=(xs: MoreElem[A]): this.type = subtractAll(xs)

  def addAll(xs: MoreElem[A]): this.type = {
    @tailrec def loop(xsl: scala.collection.LinearSeq[A]): Unit =
      if (xsl.nonEmpty) {
        addOne(xsl.head)
        loop(xsl.tail)
      }

    xs match {
      case xsl: scala.collection.LinearSeq[A] => loop(xsl)
      case _ => xs.foreach(addOne)
    }
    this
  }

  def subtractAll(xs: MoreElem[A]): this.type = {
    @tailrec def loop(xsl: collection.LinearSeq[A]): Unit =
      if (xsl.nonEmpty) {
        subtractOne(xsl.head)
        loop(xsl.tail)
      }

    xs match {
      case xsl: scala.collection.LinearSeq[A] => loop(xsl)
      case _ => xs.foreach(subtractOne)
    }
    this
  }
}
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

import scala.annotation.tailrec
import scala.collection.mutable

/**
  * Default partial implementation for mutable map adapters.
  */
abstract class MapWrapper[K, V] extends mutable.Map[K, V] {
  // abstract

  def addOne      (elem: (K, V)): this.type
  def subtractOne (key: K)      : this.type

  // impl

  final def +=(elem: (K, V)): this.type = addOne     (elem)
  final def -=(key: K)      : this.type = subtractOne(key)

  /** The collection passed to `addAll` and `subtractAll` */
  type MoreElem[+B] = TraversableOnce[B]

  /** Cross-version way for creating an iterator from `MoreElem`. */
  final protected def mkIterator[B](xs: MoreElem[B]): Iterator[B] = xs.toIterator

  final override def ++=(xs: MoreElem[(K, V)]): this.type = addAll     (xs)
  final override def --=(xs: MoreElem[ K    ]): this.type = subtractAll(xs)

  def addAll(xs: MoreElem[(K, V)]): this.type = {
    @tailrec def loop(xsl: scala.collection.LinearSeq[(K, V)]): Unit =
      if (xsl.nonEmpty) {
        addOne(xsl.head)
        loop(xsl.tail)
      }

    xs match {
      case xsl: scala.collection.LinearSeq[(K, V)] => loop(xsl)
      case _ => xs.foreach(addOne)
    }
    this
  }

  def subtractAll(xs: MoreElem[K]): this.type = {
    @tailrec def loop(xsl: collection.LinearSeq[K]): Unit =
      if (xsl.nonEmpty) {
        subtractOne(xsl.head)
        loop(xsl.tail)
      }

    xs match {
      case xsl: scala.collection.LinearSeq[K] => loop(xsl)
      case _ => xs.foreach(subtractOne)
    }
    this
  }
}
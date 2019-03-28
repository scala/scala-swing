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
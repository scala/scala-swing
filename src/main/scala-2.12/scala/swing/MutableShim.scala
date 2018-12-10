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

  override final def +=(a: A): this.type = addOne(a)
  override final def +=:(a: A): this.type = prepend(a)

  override final def -=(a: A): this.type = subtractOne(a)

  // Growable
  def addOne(a: A): this.type

  // Shrinkable, defined in terms of indexOf and remove
  def subtractOne(a: A): this.type = super.-=(a)

  def prepend(a: A): this.type
}

protected[swing] trait MutableSetShim[A] extends mutable.Set[A] {

  override final def +=(a: A): this.type = addOne(a)

  override final def -=(a: A): this.type = subtractOne(a)

  // Growable
  def addOne(a: A): this.type

  // Shrinkable
  def subtractOne(a: A): this.type
}

protected[swing] trait MutableMapShim[K, V] extends mutable.Map[K, V] {

  override final def +=(kv: (K, V)): this.type = addOne(kv)

  override final def -=(k: K): this.type = subtractOne(k)

  // Growable
  def addOne(kv: (K, V)): this.type

  // Shrinkable, defined in terms of indexOf and remove
  def subtractOne(k: K): this.type
}

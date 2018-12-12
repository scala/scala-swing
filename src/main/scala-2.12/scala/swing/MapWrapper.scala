package scala.swing

import scala.collection.mutable

/**
  * Default partial implementation for set adapters.
  */
protected[swing] abstract class MapWrapper[K, V] extends mutable.Map[K, V] {
  // abstract

  def addOne      (elem: (K, V)): this.type
  def subtractOne (key: K)      : this.type

  // impl

  final def +=(elem: (K, V)): this.type = addOne     (elem)
  final def -=(key: K)      : this.type = subtractOne(key)
}
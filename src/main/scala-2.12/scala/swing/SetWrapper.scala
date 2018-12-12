package scala.swing

import scala.collection.mutable

/**
  * Default partial implementation for set adapters.
  */
abstract class SetWrapper[A] extends mutable.Set[A] {
  // abstract

  def addOne      (elem: A): this.type
  def subtractOne (elem: A): this.type

  // impl

  final def +=(elem: A): this.type = addOne     (elem)
  final def -=(elem: A): this.type = subtractOne(elem)
}
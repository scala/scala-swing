/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala

/**
 * Useful imports that do not have wrappers.
 */
package object swing {
  type Point      = java.awt.Point
  type Dimension  = java.awt.Dimension
  type Rectangle  = java.awt.Rectangle
  type Insets     = java.awt.Insets

  type Graphics2D = java.awt.Graphics2D
  type Color      = java.awt.Color
  type Image      = java.awt.Image
  type Font       = java.awt.Font

  type Seq[A]     = scala.collection.Seq[A] // because scala.Seq differs between Scala 2.12 and 2.13

  implicit lazy val reflectiveCalls     = scala.language.reflectiveCalls
  implicit lazy val implicitConversions = scala.language.implicitConversions

  private[swing] def ifNull[A](o: Object, a: A): A = if(o eq null) a else o.asInstanceOf[A]
  private[swing] def toOption[A](o: Object): Option[A] = if(o eq null) None else Some(o.asInstanceOf[A])
  private[swing] def toAnyRef(x: Any): AnyRef = x.asInstanceOf[AnyRef]
}

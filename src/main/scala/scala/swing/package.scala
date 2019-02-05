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

package scala

/** Scala-swing is a graphical user interface library that will wrap most of Java Swing
  * for Scala in a straightforward manner.
  *
  * ==Overview==
  *
  * The widget class hierarchy loosely resembles that of Java Swing.
  * The main differences are:
  *
  *  - In Java Swing all components are containers per default. This does not make much sense for a number
  *    of components, like [[scala.swing.TextField]], [[scala.swing.CheckBox]], [[scala.swing.RadioButton]],
  *    and so on. Our guess is that this architecture
  *    was chosen because Java lacks multiple inheritance. In scala-swing, components that can have child
  *    components extend the [[scala.swing.Container]] trait.
  *  - Layout managers and panels are coupled. There is no way to exchange the layout manager of a panel.
  *    As a result, the layout constraints for widgets can be typed. (Note that you gain more type-safety
  *    and do not loose much flexibility here. Besides being not a common operation, exchanging the layout
  *    manager of a panel in Java Swing almost always leads to exchanging the layout constraints for every
  *    of the panel's child component. In the end, it is not more work to move all children to a newly
  *    created panel.)
  *  - Widget hierarchies are built by adding children to their parent container's contents collection.
  *    The typical usage style is to create anonymous subclasses of the widgets to customize their
  *    properties, and nest children and event reactions.
  *  - The scala-swing event system follows a different approach than the underlying Java system. Instead
  *    of adding event listeners with a particular interface (such as `java.awt.ActionListener`),
  *    a [[scala.swing.Reactor]]
  *    instance announces the interest in receiving events by calling `listenTo` for a [[scala.swing.Publisher]].
  *    Publishers
  *    are also reactors and listen to themselves per default as a convenience. A reactor contains an object
  *    `reactions` which serves as a convenient place to register observers by adding partial functions that
  *    pattern match for any event that the observer is interested in. This is shown in the examples section
  *    below.
  *  - For more details see [[https://github.com/scala/scala-swing/blob/work/docs/SIP-8.md SIP-8]].
  *
  * Scala-swing comprises two main packages:
  *
  *  - `scala.swing`: All widget classes and traits.
  *  - `scala.swing.event`: The event hierarchy.
  *
  * This package object contains useful type aliases that do not have wrappers.
  *
  * ==Examples==
  *
  * The following example shows how to plug components and containers together and react to a
  * mouse click on a button:
  *
  * {{{
  * import scala.swing._
  *
  * new Frame {
  *   title = "Hello world"
  *
  *   contents = new FlowPanel {
  *     contents += new Label("Launch rainbows:")
  *     contents += new Button("Click me") {
  *       reactions += {
  *         case event.ButtonClicked(_) =>
  *           println("All the colours!")
  *       }
  *     }
  *   }
  *
  *   pack()
  *   centerOnScreen()
  *   open()
  * }
  * }}}
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

  implicit lazy val reflectiveCalls     = scala.language.reflectiveCalls
  implicit lazy val implicitConversions = scala.language.implicitConversions

  private[swing] def ifNull   [A](o: Object, a: A): A   = if(o eq null) a     else o.asInstanceOf[A]
  private[swing] def toOption [A](o: Object): Option[A] = if(o eq null) None  else Some(o.asInstanceOf[A])

  private[swing] def toAnyRef(x: Any): AnyRef = x.asInstanceOf[AnyRef]
}

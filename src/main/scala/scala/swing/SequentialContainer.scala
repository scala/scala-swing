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

object SequentialContainer {
  /**
   * Utility trait for wrapping sequential containers.
   */
  trait Wrapper extends SequentialContainer with Container.Wrapper {
    override val contents: mutable.Buffer[Component] = new Content
    //def contents_=(c: Component*)  { contents.clear(); contents ++= c }
  }
}

/**
 * A container for which a sequential order of children makes sense, such as
 * flow panels, or menus. Its contents are mutable.
 */
trait SequentialContainer extends Container {
  /**
   * The mutable child components of this container. The order matters and
   * usually indicates the layout of the children.
   */
  override def contents: mutable.Buffer[Component]
  //def contents_=(c: Component*)
}

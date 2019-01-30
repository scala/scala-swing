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

/**
 * A component that can contain other components.
 *
 * @see javax.swing.JPanel
 */
abstract class Panel extends Component with Container.Wrapper {
  override lazy val peer: javax.swing.JPanel = new javax.swing.JPanel with SuperMixin
}

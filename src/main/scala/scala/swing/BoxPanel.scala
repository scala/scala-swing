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
 * A panel that lays out its contents one after the other,
 * either horizontally or vertically.
 *
 * @see javax.swing.BoxLayout
 */
class BoxPanel(orientation: Orientation.Value) extends Panel with SequentialContainer.Wrapper {
  override lazy val peer: javax.swing.JPanel = {
    val p = new javax.swing.JPanel with SuperMixin
    val l = new javax.swing.BoxLayout(p, orientation.id)
    p.setLayout(l)
    p
  }
}

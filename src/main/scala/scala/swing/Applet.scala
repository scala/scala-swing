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

import javax.swing.JApplet

/** <p>
 *    Clients should implement the ui field. See the <code>SimpleApplet</code>
 *    demo for an example.
 *  </p>
 *  <p>
 *    <b>Note</b>: <code>Applet</code> extends <code>javax.swing.JApplet</code>
 *    to satisfy Java's applet loading mechanism. The usual component wrapping
 *    scheme doesn't  work here.
 *  </p>
 *
 *  @see javax.swing.JApplet
 */
abstract class Applet extends JApplet { outer =>
  val ui: UI

  override def init (): Unit = ui.init ()
  override def start(): Unit = ui.start()
  override def stop (): Unit = ui.stop ()

  abstract class UI extends RootPanel {
    def peer: Applet = outer
    override def contents_=(c: Component): Unit = {
      super.contents_=(c)
      peer.validate()
    }

    def init (): Unit
    def start(): Unit = ()
    def stop (): Unit = ()
  }
}


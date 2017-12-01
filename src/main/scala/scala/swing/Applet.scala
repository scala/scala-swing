/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



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


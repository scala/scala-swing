package scala.swing

import java.util.concurrent.TimeUnit

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.swing.event.{UIElementHidden, UIElementMoved, UIElementResized, UIElementShown}
import scala.util.control.NonFatal

// Note: `AsyncFlatSpec` has issues with swallowing errors and returning early.
class Issue97 extends FlatSpec with Matchers {
  case class Count(shown: Int = 0, hidden: Int = 0, moved: Int = 0, resized: Int = 0)

  def countEvents(): Future[Count] = {
    val p = Promise[Count]()

    def safely(thunk: => Unit): Unit =
      try {
        thunk
      } catch {
        case NonFatal(ex) =>
          p.tryFailure(ex)
      }

    Swing.onEDT {
      safely {
        var c = Count()
        val lb = new Label("Foo")
        lb.listenTo(lb)
        lb.reactions += {
          case UIElementShown   (`lb`) => c = c.copy(shown    = c.shown   + 1)
          case UIElementHidden  (`lb`) => c = c.copy(hidden   = c.hidden  + 1)
          case UIElementMoved   (`lb`) => c = c.copy(moved    = c.moved   + 1)
          case UIElementResized (`lb`) => c = c.copy(resized  = c.resized + 1)
        }
        val b = new BoxPanel(Orientation.Horizontal)
        b.contents += lb
        lb.visible = false
        lb.visible = true
        b.contents.insert(0, new Label("Bar"))  // about to move `lb` to the right
        b.peer.doLayout()
        // note: `Frame#pack()` creates a native window peer,
        // and thus is not possible to run on Travis without X11

        // wait till next EDT cycle
        Swing.onEDT {
          p.trySuccess(c)
        }
      }
    }
    p.future
  }

  "Components" should "fire exactly one event when moved, removed or made visible or invisible" in {
    val futCount = countEvents()
    val c = Await.result(futCount, Duration(20, TimeUnit.SECONDS))
    assert(c.shown    === 1)
    assert(c.hidden   === 1)
    assert(c.moved    === 1)
    assert(c.resized  === 1)
  }
}

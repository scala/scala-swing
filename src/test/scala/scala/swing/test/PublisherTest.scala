/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2014, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing.test

import org.scalatest._
import scala.swing.event.Event
import scala.swing.{Reactor, Publisher}
import scala.collection.immutable


class PublisherTest extends FunSuite {

  val LoopCount = 1000
  val ReactorsSize = 1000

  object TstPublisher {
    case class Destroyed(source: TstPublisher) extends Event
  }

  class TstPublisher extends Publisher

  class TstReactor(publisher: TstPublisher) extends Reactor {
    listenTo(publisher)

    var reacted: Int = 0

    reactions += {
      case TstPublisher.Destroyed(p) if p eq publisher =>
        reacted = reacted + 1
        deafTo(publisher)
    }
  }

  // Regression test for: https://issues.scala-lang.org/browse/SI-8495
  test("all listeners should be called exactly once, even if listener state changes during publishing") {
    (1 until LoopCount) foreach { c =>
      val publisher = new TstPublisher
      val reactors = immutable.Seq.fill(ReactorsSize)(new TstReactor(publisher))

      publisher.publish(TstPublisher.Destroyed(publisher))

      reactors.foreach {  r =>
        assert(r.reacted == 1)
      }
    }
  }
}
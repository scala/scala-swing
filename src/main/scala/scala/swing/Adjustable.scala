package scala.swing

import java.awt.{Adjustable => JAdjustable}

object Adjustable {
  trait Wrapper extends Oriented.Wrapper with Adjustable {
    def peer: JAdjustable with OrientedMixin

    def unitIncrement: Int = peer.getUnitIncrement
    def unitIncrement_=(i: Int): Unit = peer.setUnitIncrement(i)
    def blockIncrement: Int = peer.getBlockIncrement
    def blockIncrement_=(i: Int): Unit = peer.setBlockIncrement(i)

    def value: Int = peer.getValue
    def value_=(v: Int): Unit = peer.setValue(v)

    def visibleAmount: Int = peer.getVisibleAmount
    def visibleAmount_=(v: Int): Unit = peer.setVisibleAmount(v)

    def minimum: Int = peer.getMinimum
    def minimum_=(m: Int): Unit = peer.setMinimum(m)
    def maximum: Int = peer.getMaximum
    def maximum_=(m: Int): Unit = peer.setMaximum(m)
  }
}

trait Adjustable extends Oriented {
  def unitIncrement: Int
  def unitIncrement_=(i: Int): Unit
  def blockIncrement: Int
  def blockIncrement_=(i: Int): Unit

  def value: Int
  def value_=(v : Int): Unit

  def visibleAmount: Int
  def visibleAmount_=(v: Int): Unit

  def minimum: Int
  def minimum_=(m: Int): Unit
  def maximum: Int
  def maximum_=(m: Int): Unit

// Needs implementation of AdjustmentEvent
//
//    val adjustments: Publisher = new Publisher {
//		peer.addAdjustmentListener(new AdjustmentListener {
//			def adjustmentValueChanged(e: java.awt.event.AdjustmentEvent) {
//				publish(new AdjustmentEvent(e))
//			}
//		})
//   	}
}

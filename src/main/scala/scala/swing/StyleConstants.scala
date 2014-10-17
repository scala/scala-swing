package scala.swing

import javax.swing.text.MutableAttributeSet

object StyleConstants  {
  def setComponent(a: MutableAttributeSet,  c: Component): Unit = 
     javax.swing.text.StyleConstants.setComponent(a, c.peer)
}
/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */



package scala.swing

import event.EditDone
import javax.swing.{InputVerifier, JComponent, JTextField}
import java.awt.event.FocusAdapter


/*object TextField {
  object FocusLostBehavior extends Enumeration {
    val Revert = Value(JFormattedTextField.REVERT)
    val Commit = Value(JFormattedTextField.REVERT)
    val CommitOrRevert = Value(JFormattedTextField.REVERT)
    val Persist = Value(JFormattedTextField.REVERT)
  }
}*/

/**
 * A text component that allows single line text input and display.
 *
 * @see javax.swing.JTextField
 */
class TextField(text0: String, columns0: Int) extends TextComponent with TextComponent.HasColumns with Action.Trigger.Wrapper {
  override lazy val peer: JTextField = new JTextField(text0, columns0) with SuperMixin
  def this(text: String) = this(text, 0)
  def this(columns: Int) = this("", columns)
  def this() = this("")

  def columns: Int = peer.getColumns
  def columns_=(n: Int): Unit = peer.setColumns(n)

  /** @see javax.swing.JTextField#getHorizontalAlignment() */
  def horizontalAlignment: Alignment.Value = Alignment(peer.getHorizontalAlignment)
  /** @see javax.swing.JTextField#setHorizontalAlignment() */
  def horizontalAlignment_=(x: Alignment.Value): Unit = peer.setHorizontalAlignment(x.id)

  private lazy val actionListener = Swing.ActionListener { _ =>
    publish(EditDone(TextField.this))
  }

  protected override def onFirstSubscribe(): Unit = {
    super.onFirstSubscribe()
    peer.addActionListener(actionListener)
    peer.addFocusListener(new FocusAdapter {
      override def focusLost(e: java.awt.event.FocusEvent): Unit = publish(EditDone(TextField.this))
    })
  }

  protected override def onLastUnsubscribe(): Unit = {
    super.onLastUnsubscribe()
    peer.removeActionListener(actionListener)
  }

  def verifier: String => Boolean = _ => Option(peer.getInputVerifier) forall (_ verify peer)
  def verifier_=(v: String => Boolean): Unit = {
    peer.setInputVerifier(new InputVerifier {
      private val old = Option(peer.getInputVerifier)
      def verify(c: JComponent) = v(text)
      override def shouldYieldFocus(c: JComponent): Boolean = old forall (_ shouldYieldFocus c)
    })
  }
  def shouldYieldFocus: String => Boolean = _ => Option(peer.getInputVerifier) forall (_ shouldYieldFocus peer)
  def shouldYieldFocus_=(y: String => Boolean): Unit = {
    peer.setInputVerifier(new InputVerifier {
      private val old = peer.getInputVerifier
      def verify(c: JComponent): Boolean = old.verify(c)
      override def shouldYieldFocus(c: JComponent) = y(text)
    })
  }
}

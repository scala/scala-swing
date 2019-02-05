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

import java.awt.event.FocusAdapter

import javax.swing.{InputVerifier, JComponent, JTextField}

import scala.swing.event.EditDone

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
      def verify(c: JComponent): Boolean = v(text)
      override def shouldYieldFocus(c: JComponent): Boolean = old forall (_ shouldYieldFocus c)
    })
  }
  def shouldYieldFocus: String => Boolean = _ => Option(peer.getInputVerifier) forall (_ shouldYieldFocus peer)
  def shouldYieldFocus_=(y: String => Boolean): Unit = {
    peer.setInputVerifier(new InputVerifier {
      private val old = peer.getInputVerifier
      def verify(c: JComponent): Boolean = old.verify(c)
      override def shouldYieldFocus(c: JComponent): Boolean = y(text)
    })
  }
}

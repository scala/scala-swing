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

import javax.swing.{Icon, AbstractButton => JAbstractButton}

import scala.swing.event.{ButtonClicked, Key}

/**
 * Base class of all button-like widgets, such as push buttons,
 * check boxes, and radio buttons.
 *
 * @see javax.swing.AbstractButton
 */
abstract class AbstractButton extends Component with Action.Trigger.Wrapper with Publisher {
  override lazy val peer: JAbstractButton = new JAbstractButton with SuperMixin {}

  def text: String = peer.getText
  def text_=(s: String): Unit = peer.setText(s)

  def icon: Icon = peer.getIcon
  def icon_=(i: Icon): Unit = peer.setIcon(i)
  def pressedIcon: Icon = peer.getPressedIcon
  def pressedIcon_=(i: Icon): Unit = peer.setPressedIcon(i)
  def selectedIcon: Icon = peer.getSelectedIcon
  def selectedIcon_=(i: Icon): Unit = peer.setSelectedIcon(i)
  def disabledIcon: Icon = peer.getDisabledIcon
  def disabledIcon_=(i: Icon): Unit = peer.setDisabledIcon(i)
  def disabledSelectedIcon: Icon = peer.getDisabledSelectedIcon
  def disabledSelectedIcon_=(i: Icon): Unit = peer.setDisabledSelectedIcon(i)
  def rolloverIcon: Icon = peer.getRolloverIcon
  def rolloverIcon_=(b: Icon): Unit = peer.setRolloverIcon(b)
  def rolloverSelectedIcon: Icon = peer.getRolloverSelectedIcon
  def rolloverSelectedIcon_=(b: Icon): Unit = peer.setRolloverSelectedIcon(b)

  peer.addActionListener(Swing.ActionListener { _ =>
    publish(ButtonClicked(AbstractButton.this))
  })

  def selected: Boolean = peer.isSelected
  def selected_=(b: Boolean): Unit = peer.setSelected(b)

  def contentAreaFilled: Boolean = peer.isContentAreaFilled
  def contentAreaFilled_=(b: Boolean): Unit = peer.setContentAreaFilled(b)

  def borderPainted: Boolean = peer.isBorderPainted
  def borderPainted_=(b: Boolean): Unit = peer.setBorderPainted(b)
  def focusPainted: Boolean = peer.isFocusPainted
  def focusPainted_=(b: Boolean): Unit = peer.setFocusPainted(b)

  def rolloverEnabled: Boolean = peer.isRolloverEnabled
  def rolloverEnabled_=(b: Boolean): Unit = peer.setRolloverEnabled(b)

  def verticalTextPosition: Alignment.Value = Alignment(peer.getVerticalTextPosition)
  def verticalTextPosition_=(a: Alignment.Value): Unit = peer.setVerticalTextPosition(a.id)
  def verticalAlignment: Alignment.Value = Alignment(peer.getVerticalAlignment)
  def verticalAlignment_=(a: Alignment.Value): Unit = peer.setVerticalAlignment(a.id)

  def horizontalTextPosition: Alignment.Value = Alignment(peer.getHorizontalTextPosition)
  def horizontalTextPosition_=(a: Alignment.Value): Unit = peer.setHorizontalTextPosition(a.id)
  def horizontalAlignment: Alignment.Value = Alignment(peer.getHorizontalAlignment)
  def horizontalAlignment_=(a: Alignment.Value): Unit = peer.setHorizontalAlignment(a.id)

  def iconTextGap: Int = peer.getIconTextGap
  def iconTextGap_=(x: Int): Unit = peer.setIconTextGap(x)

  def mnemonic: Key.Value = Key(peer.getMnemonic)
  def mnemonic_=(k: Key.Value): Unit = peer.setMnemonic(k.id)
  def displayedMnemonicIndex: Int = peer.getDisplayedMnemonicIndex
  def displayedMnemonicIndex_=(n: Int): Unit = peer.setDisplayedMnemonicIndex(n)

  def multiClickThreshold: Long = peer.getMultiClickThreshhold
  def multiClickThreshold_=(n: Long): Unit = peer.setMultiClickThreshhold(n)

  def doClick(): Unit = peer.doClick()
  def doClick(times: Int): Unit = peer.doClick(times)

  def margin: Insets = peer.getMargin
  def margin_=(i: Insets): Unit = peer.setMargin(i)
}

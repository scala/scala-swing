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

import javax.swing.JEditorPane
import javax.swing.text.EditorKit

/**
 * A text component that allows multi-line text input and display.
 *
 * @see javax.swing.JEditorPane
 */
class EditorPane(contentType0: String, text0: String) extends TextComponent {
	override lazy val peer: JEditorPane = new JEditorPane(contentType0, text0) with SuperMixin
	def this() = this("text/plain", "")

	def contentType: String = peer.getContentType
	def contentType_=(t: String): Unit = peer.setContentType(t)

	def editorKit: EditorKit = peer.getEditorKit
	def editorKit_=(k: EditorKit): Unit = peer.setEditorKit(k)
}

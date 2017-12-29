/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2007-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala.swing

import java.awt.event.ActionListener
import javax.swing.{AbstractListModel, ComboBoxModel, InputVerifier, JComboBox, JComponent, JTextField, ListCellRenderer}

import scala.swing.event._

object ComboBox {
  /**
   * An editor for a combo box. Let's you edit the currently selected item.
   * It is highly recommended to use the BuiltInEditor class. For anything
   * else, one cannot guarantee that it integrates nicely with the current
   * LookAndFeel.
   *
   * Publishes action events.
   */
  trait Editor[A] extends Publisher {
    lazy val comboBoxPeer: javax.swing.ComboBoxEditor = new javax.swing.ComboBoxEditor with Publisher {
      def addActionListener(l: ActionListener): Unit =
        this match {
          // TODO case w: Action.Trigger.Wrapper =>
          //  w.peer.addActionListener(l)
          case _ =>
            this.subscribe(new Reactions.Wrapper(l) ({
               case ActionEvent(c) => l.actionPerformed(new java.awt.event.ActionEvent(c.peer, 0, ""))
            }))
         }

      def removeActionListener(l: ActionListener): Unit =
        this match {
          // TODO case w: Action.Trigger.Wrapper =>
          //  w.peer.removeActionListener(l)
          case _ =>
            this.unsubscribe(new Reactions.Wrapper(l)({ case _ => }))
        }

      def getEditorComponent: JComponent = Editor.this.component.peer

      def getItem(): AnyRef = item.asInstanceOf[AnyRef]

      def selectAll(): Unit = startEditing()

      def setItem(a: Any): Unit =
        item = a.asInstanceOf[A]
    }
    def component: Component
    def item: A
    def item_=(a: A): Unit
    def startEditing(): Unit
  }

  /**
   * Use this editor, if you want to reuse the builtin editor supplied by the current
   * Look and Feel. This is restricted to a text field as the editor widget. The
   * conversion from and to a string is done by the supplied functions.
   *
   * It's okay if string2A throws exceptions. They are caught by an input verifier.
   */
  class BuiltInEditor[A](comboBox: ComboBox[A])(string2A: String => A,
                         a2String: A => String) extends ComboBox.Editor[A] {
    protected[swing] class DelegatedEditor(editor: javax.swing.ComboBoxEditor) extends javax.swing.ComboBoxEditor {
      var value: A = {
        val v = comboBox.peer.getSelectedItem
        try {
          v match {
            case s: String => string2A(s)
            case _ => v.asInstanceOf[A]
          }
        } catch {
          case _: Exception =>
            throw new IllegalArgumentException("ComboBox not initialized with a proper value, was '" + v + "'.")
        }
      }
      def addActionListener(l: ActionListener): Unit = editor.addActionListener(l)
      def removeActionListener(l: ActionListener): Unit = editor.removeActionListener(l)

      def getEditorComponent: JComponent = editor.getEditorComponent.asInstanceOf[JComponent]
      def selectAll(): Unit = editor.selectAll()
      def getItem(): AnyRef = { verifier.verify(getEditorComponent); value.asInstanceOf[AnyRef] }
      def setItem(a: Any): Unit = editor.setItem(a)

      val verifier: InputVerifier = new InputVerifier {
        // TODO: should chain with potentially existing verifier in editor
        def verify(c: JComponent): Boolean =
          try {
            value = string2A(c.asInstanceOf[JTextField].getText)
            true
          }
          catch {
            case _: Exception => false
          }
      }

      def textEditor: JTextField = getEditorComponent.asInstanceOf[JTextField]
      textEditor.setInputVerifier(verifier)
      textEditor.addActionListener(Swing.ActionListener{ _ =>
        getItem() // make sure our value is updated
        textEditor.setText(a2String(value))
      })
    }

    override lazy val comboBoxPeer: javax.swing.ComboBoxEditor = new DelegatedEditor(comboBox.peer.getEditor)

    def component: Component = Component.wrap(comboBoxPeer.getEditorComponent.asInstanceOf[JComponent])
    def item: A = { comboBoxPeer.asInstanceOf[DelegatedEditor].value }
    def item_=(a: A): Unit = comboBoxPeer.setItem(a2String(a))
    def startEditing(): Unit = comboBoxPeer.selectAll()
  }

  implicit def stringEditor(c: ComboBox[String]): Editor[String] = new BuiltInEditor(c)(s => s, s => s)
  implicit def intEditor(c: ComboBox[Int]): Editor[Int] = new BuiltInEditor(c)(s => s.toInt, s => s.toString)
  implicit def floatEditor(c: ComboBox[Float]): Editor[Float] = new BuiltInEditor(c)(s => s.toFloat, s => s.toString)
  implicit def doubleEditor(c: ComboBox[Double]): Editor[Double] = new BuiltInEditor(c)(s => s.toDouble, s => s.toString)

  def newConstantModel[A](items: Seq[A]): ComboBoxModel[A] = {
    new AbstractListModel[A] with ComboBoxModel[A] {
      private var selected: A = if (items.isEmpty) null.asInstanceOf[A] else items(0)
      def getSelectedItem: AnyRef = selected.asInstanceOf[AnyRef]
      def setSelectedItem(a: Any): Unit = {
        if ((selected != null && selected != a) ||
            selected == null && a != null) {
          selected = a.asInstanceOf[A]
          fireContentsChanged(this, -1, -1)
        }
      }
      def getElementAt(n: Int): A = items(n)
      def getSize: Int = items.size
    }
  }

  /*def newMutableModel[A, Self](items: Seq[A] with scala.collection.mutable.Publisher[scala.collection.mutable.Message[A], Self]): ComboBoxModel = {
    new AbstractListModel with ComboBoxModel {
      private var selected = items(0)
      def getSelectedItem: AnyRef = selected.asInstanceOf[AnyRef]
      def setSelectedItem(a: Any) { selected = a.asInstanceOf[A] }
      def getElementAt(n: Int) = items(n).asInstanceOf[AnyRef]
      def getSize = items.size
    }
  }

  def newConstantModel[A](items: Seq[A]): ComboBoxModel = items match {
    case items: Seq[A] with scala.collection.mutable.Publisher[scala.collection.mutable.Message[A], Self] => newMutableModel
    case _ => newConstantModel(items)
  }*/
}

/**
 * Let's the user make a selection from a list of predefined items. Visually,
 * this is implemented as a button-like component with a pull-down menu.
 *
 * @see javax.swing.JComboBox
 */
class ComboBox[A](items: Seq[A]) extends Component with Publisher {
  override lazy val peer: JComboBox[A] = new JComboBox(ComboBox.newConstantModel(items)) with SuperMixin

  object selection extends Publisher {
    def index: Int = peer.getSelectedIndex
    def index_=(n: Int): Unit = peer.setSelectedIndex(n)
    def item: A = peer.getSelectedItem.asInstanceOf[A]
    def item_=(a: A): Unit = peer.setSelectedItem(a)

    peer.addActionListener(Swing.ActionListener { _ =>
      publish(event.SelectionChanged(ComboBox.this))
    })
  }

  /**
   * Sets the renderer for this combo box's items. Index -1 is
   * passed to the renderer for the selected item (not in the pull-down menu).
   *
   * The underlying combo box renders all items in a <code>ListView</code>
   * (both, in the pull-down menu as well as in the box itself), hence the
   * <code>ListView.Renderer</code>.
   *
   * Note that the UI peer of a combo box usually changes the colors
   * of the component to its own defaults _after_ the renderer has been
   * configured. That's Swing's principle of most suprise.
   */
  def renderer: ListView.Renderer[A] = ListView.Renderer.wrap(peer.getRenderer.asInstanceOf[ListCellRenderer[A]])
  def renderer_=(r: ListView.Renderer[A]): Unit = peer.setRenderer(r.peer)

  /* XXX: currently not safe to expose:
  def editor: ComboBox.Editor[A] =
  def editor_=(r: ComboBox.Editor[A]) { peer.setEditor(r.comboBoxPeer) }
  */
  def editable: Boolean = peer.isEditable


  /**
   * Maximum number of rows to display without scrolling
   */
  def maximumRowCount: Int = peer.getMaximumRowCount

  /**
   * Maximum number of rows to display without scrolling
   */
  def maximumRowCount_=(c:Int): Unit = peer.setMaximumRowCount(c)

  /**
   * Makes this combo box editable. In order to do so, this combo needs an
   * editor which is supplied by the implicit argument. For default
   * editors, see ComboBox companion object.
   */
  def makeEditable()(implicit editor: ComboBox[A] => ComboBox.Editor[A]): Unit = {
    peer.setEditable(true)
    peer.setEditor(editor(this).comboBoxPeer)
  }

  def prototypeDisplayValue: Option[A] = Option(peer.getPrototypeDisplayValue)
  def prototypeDisplayValue_=(v: Option[A]): Unit =
    peer.setPrototypeDisplayValue(v getOrElse null.asInstanceOf[A])
}

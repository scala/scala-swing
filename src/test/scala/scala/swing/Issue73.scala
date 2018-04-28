package scala.swing

import org.scalatest.{FlatSpec, Matchers}

class Issue73 extends FlatSpec with Matchers {
  "Enumerations" should "not contain duplicate ids" in {
    // the initializers of any of these will through an
    // assertion error if an enumeration `Value` id is used twice.
    Alignment
    BorderPanel.Position
    Dialog.Message
    Dialog.Options
    Dialog.Result
    FileChooser.Result
    FileChooser.SelectionMode
    FlowPanel.Alignment
    FormattedTextField.FocusLostBehavior
    GridBagPanel.Anchor
    GridBagPanel.Fill
    ListView.IntervalMode
    Orientation
    ScrollPane.BarPolicy
    TabbedPane.Layout
    Table.AutoResizeMode
    Table.ElementMode
    Table.IntervalMode
    event.Key
    event.Key.Location

    Font.Style
  }
}

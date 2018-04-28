package scala.swing
package uitest

/** Example application to verify that table row sorting
  * and column reordering work correctly.
  */
object Issue47 extends SimpleSwingApplication {
  lazy val top: Frame = {
    val data0 = Array(
      Array("Schaeffer" , 1910, 1995),
      Array("Sun Ra"    , 1914, 1993),
      Array("Oram"      , 1925, 2003),
      Array("Oliveros"  , 1932, 2016)
    )

    val cn  = Seq("Name", "Born", "Died")
    val t   = new Table(data0, cn)
    val st  = new ScrollPane(t)
    t.autoCreateRowSorter = true

    val ggAsc   = new ToggleButton("Ascending")
    ggAsc.selected = true
    val ggSort  = cn.zipWithIndex.map { case (n, ci) => Button(n)(t.sort(ci, ascending = ggAsc.selected)) }
    val pSort   = new FlowPanel(new Label("Sort by:") +: ggSort :+ ggAsc: _*)

    val ggSelected = new TextArea(3, 40) {
      lineWrap  = true
      editable  = false
      font      = Font(Font.Monospaced, Font.Plain, 12)
    }

    val pSelected = new FlowPanel(new Label("Selection:"), ggSelected)

    t.selection.elementMode = Table.ElementMode.Cell
    t.listenTo(t.selection)

    def captureSelection() = t.selection.cells.toList.sorted

    def toModel(in: List[(Int, Int)]): List[(Int, Int)] = in.map { case (row, col) =>
      t.viewToModelRow(row) -> t.viewToModelColumn(col)
    }

    var lastSel = List.empty[(Int, Int)]

    t.reactions += {
      case _: event.TableRowsSelected | _: event.TableColumnsSelected =>
      val newSel = captureSelection()
      if (lastSel != newSel) {
        lastSel = newSel
        val mSel    = toModel(newSel)
        val data    = newSel.map { case (row, col) => t.apply(row = row, column = col) }
        val viewS   = newSel.mkString("View : ", " ; ", "\n")
        val modelS  = mSel  .mkString("Model: ", " ; ", "\n")
        val dataS   = data.mkString("Data : ", " ; ", "")
        ggSelected.text = s"$viewS$modelS$dataS"
      }
    }

    new MainFrame {
      contents = new BoxPanel(Orientation.Vertical) {
        contents += st
        contents += pSort
        contents += pSelected
      }
    }
  }
}

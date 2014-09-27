package scala.swing

class ToolBar extends scala.swing.Component with SequentialContainer.Wrapper {
  override lazy val peer: javax.swing.JToolBar = new javax.swing.JToolBar with SuperMixin
}
package scala.swing.examples.tutorials.components

import scala.swing._
import java.awt.{ AlphaComposite, Color, Composite, Graphics, Insets, RenderingHints }
import java.awt.font.FontRenderContext
import java.awt.geom.Rectangle2D
import javax.swing.Icon

class SizeDisplayer(text: String, icon: Icon) extends Label(text, icon, Alignment.Center) {
  val xTextPad = 5
  val yTextPad = 5
  opaque = true

  //Reuse textSizeD and textSizeR to avoid creating
  //lots of unnecessary Dimensions and Rectangles.
  private var textSizeR = new Rectangle()
  private var textSizeD = new Dimension()
  private var userPreferredSize: Dimension = null
  private var userMinimumSize: Dimension = null
  private var userMaximumSize: Dimension = null

  def paintComponent(g: Graphics): Unit = {
    val g2d = g.create().asInstanceOf[Graphics2D] //copy g
    var prefX = 0
    var prefY = 0;

    //Set hints so text looks nice.
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
      RenderingHints.VALUE_RENDER_QUALITY)

    //Draw the maximum size rectangle if we're opaque.
    if (opaque) {
      g2d.setColor(super.background)
      g2d.fillRect(0, 0, size.width, size.height)
    }

    //Draw the icon.
    if (icon != null) {
      val oldComposite: Composite = g2d.getComposite();
      g2d.setComposite(AlphaComposite.getInstance(
        AlphaComposite.SRC_OVER,
        0.1f))
      icon.paintIcon(peer, g2d,
        (size.width - icon.getIconWidth()) / 2,
        (size.height - icon.getIconHeight()) / 2)
      g2d.setComposite(oldComposite)
    }

    //Draw the preferred size rectangle.
    prefX = (size.width - preferredSize.width) / 2
    prefY = (size.height - preferredSize.height) / 2
    g2d.setColor(Color.RED)
    g2d.drawRect(prefX, prefY, preferredSize.width - 1, preferredSize.height - 1)

    //Draw the minimum size rectangle.
    if (minimumSize.width != preferredSize.width || minimumSize.height != preferredSize.height) {
      val minX = (size.width - minimumSize.width) / 2
      val minY = (size.height - minimumSize.height) / 2
      g2d.setColor(Color.CYAN)
      g2d.drawRect(minX, minY, minimumSize.width - 1, minimumSize.height - 1)
    }

    //Draw the text.
    if (text != null) {
      val textSize: Dimension = getTextSize(g2d)
      g2d.setColor(peer.getForeground())
      g2d.drawString(text,
        (size.width - textSize.width) / 2,
        (size.height - textSize.height) / 2
          + g2d.getFontMetrics().getAscent())
    }
    g2d.dispose();
  }

  def getTextSize(g2d: Graphics2D): Dimension = {
    if (text == null) {
      textSizeD.setSize(0, 0);
    } else {
      val frc: FontRenderContext =
        if (g2d != null) {
          g2d.getFontRenderContext()
        } else {
          new FontRenderContext(null, false, false)
        }
      val textRect: Rectangle2D = peer.getFont().getStringBounds(
        text,
        frc)
      textSizeR.setRect(textRect)
      textSizeD.setSize(textSizeR.width, textSizeR.height)
    }
    textSizeD;
  }

  def getMinimumSize(): Dimension = {
    if (userMinimumSize != null) { //user has set the min size
      userMinimumSize;
    } else {
      getPreferredSize();
    }
  }

  def getPreferredSize(): Dimension = {
    if (userPreferredSize != null) { //user has set the pref size
      userPreferredSize
    } else {
      calculatePreferredSize()
    }
  }

  def getMaximumSize(): Dimension = {
    if (userMaximumSize != null) { //user has set the max size
      userMaximumSize
    } else {
      new Dimension(Integer.MAX_VALUE,
        Integer.MAX_VALUE)
    }
  }

  def setMinimumSize(newSize: Dimension) {
    userMinimumSize = newSize
  }
  def setPreferredSize (newSize: Dimension) {
    userPreferredSize = newSize;
  }
  def setMaximumSize (newSize: Dimension) {
    userMaximumSize = newSize;
  }

  private def calculatePreferredSize(): Dimension = {
    val insets: Insets = peer.getInsets()
    val textSize: Dimension = getTextSize(null)
    var iconWidth = 0
    var iconHeight = 0

    if (icon != null) {
      iconWidth = icon.getIconWidth()
      iconHeight = icon.getIconHeight()
    }

    val d = new Dimension(
      Math.max(iconWidth, textSize.width + 2 * xTextPad)
        + insets.left + insets.right,
      Math.max(iconHeight, textSize.height + 2 * yTextPad)
        + insets.top + insets.bottom);
    super.preferredSize = d
    d
  }
}
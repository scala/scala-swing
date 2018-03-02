/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scala.swing.examples.tutorials.components

import scala.swing._
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import javax.swing.{ Icon, ImageIcon }

/**
 * Tutorial: How to Use Icons
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html]]
 * 
 * Source code references:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/IconDemoProject/src/components/IconDemoApp.java]]
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/IconDemoProject/src/components/MissingIcon.java]]
 *
 * IconDemoApp.scala 
 *   MissingIcon.scala
 */
/**
 * This application is intended to demonstrate the loading of image files into icons
 * for use in a Swing user interface. It creates a toolbar with a thumbnail preview
 * of each image.  Clicking on the thumbnail will show the full image
 * in the main display area.
 *
 * IconDemoApp.scala requires the following files: <br>
 * The following files are copyright 2006 spriggs.net and licensed under a
 * Creative Commons License (http://creativecommons.org/licenses/by-sa/3.0/)
 * <br>
 * /scala/swing/examples/tutorials/images/sunw01.jpg <br>
 * /scala/swing/examples/tutorials/images/sunw02.jpg <br>
 * images/sunw03.jpg <br>
 * /scala/swing/examples/tutorials/images/sunw04.jpg <br>
 * /scala/swing/examples/tutorials/images/sunw05.jpg <br>
 *
 * @author Collin Fagan
 * @version 2.0
 */
class IconDemoApp extends MainFrame {

  private val imagedir = "/scala/swing/examples/tutorials/images/"
  private val placeholderIcon = new MissingIcon()

  /**
   * List of all the descriptions of the image files. These correspond one to
   * one with the image file names
   */
  private val imageFileNames = Array[String]( "sunw01.jpg", "sunw02.jpg",
    "sunw03.jpg", "sunw04.jpg", "sunw05.jpg")

  private val imageCaptions = Array[String]("Original SUNW Logo", "The Clocktower",
    "Clocktower from the West", "The Mansion", "Sun Auditorium")

  // A label for displaying the pictures
  private val photographLabel = new Label() {
    verticalTextPosition = Alignment.Bottom
    horizontalTextPosition = Alignment.Center
    horizontalAlignment = Alignment.Center
    border = Swing.EmptyBorder(5, 5, 5, 5)
  }

  // We add two glue components. Later in process() we will add thumbnail buttons
  // to the toolbar in between these glue components. This will center the
  // buttons in the toolbar.
  private val buttonBar = new ToolBar() {
    contents += Swing.Glue
    contents += Swing.Glue
  }
  contents = new BorderPanel() {
    layout(buttonBar) = BorderPanel.Position.South
    layout(photographLabel) = BorderPanel.Position.Center
  }
  size = new Dimension(400, 300)

  // this centers the frame on the screen
  // setLocationRelativeTo(null)

  def loadImage( fileName:String, caption: String ): Option[ThumbnailAction] = {
    IconDemoApp.createImageIcon(imagedir + fileName) match {
      case Some(img) =>
        val thumbnailIcon = new ImageIcon(getScaledImage(img.getImage, 32, 32))
        Some(new ThumbnailAction(img, thumbnailIcon, caption, photographLabel))
      case None =>
        Some(new ThumbnailAction(placeholderIcon, placeholderIcon, caption, photographLabel))
    }
  }

  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global

  val f:Future[List[Option[ThumbnailAction]]] = Future {
    imageCaptions.zip(imageFileNames).map {
      case (cap, file) => loadImage(file, cap)
    }.toList
  }

  f.foreach {
    thumbs:List[Option[ThumbnailAction]] =>
      buttonBar.contents.dropRight(1)
      thumbs.foreach{ thumbAction => {
        thumbAction.foreach { ta =>
          buttonBar.contents += new Button(ta)
        }
      }}
      buttonBar.contents += Swing.Glue
  }


  /**
   * Resizes an image using a Graphics2D object backed by a BufferedImage.
   * @param srcImg - source image to scale
   * @param w - desired width
   * @param h - desired height
   * @return - the new resized image
   */
  private def getScaledImage(srcImg: Image, w: Int, h: Int): Image = {
    val resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val g2: Graphics2D = resizedImg.createGraphics()
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g2.drawImage(srcImg, 0, 0, w, h, null)
    g2.dispose()
    resizedImg
  }
}

object IconDemoApp extends SimpleSwingApplication {
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] =
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))

  lazy val top = new IconDemoApp() {
    title = "Icon Demo: Please Select an Image"
  }
}

/**
 * @param photo - The full size photo to show in the button.
 * @param thumb - The thumbnail to show in the button.
 * @param desc - The description of the icon.
 */
class ThumbnailAction(photo: Icon, thumb: Icon, desc: String, photographLabel:Label) extends Action("") {
  // icon when an Action is applied to a butt
  toolTip = desc

  // The short description becomes the tooltip of a button.
  icon = thumb

  def apply(): Unit = {
    photographLabel.icon = photo
    IconDemoApp.top.title = s"Icon Demo: $desc"
  }
}



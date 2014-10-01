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
import java.net.URL
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import javax.swing.{ Icon, ImageIcon, JToolBar, SwingWorker }

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
 * @date 7/25/2007
 * @version 2.0
 */
class IconDemoApp extends MainFrame {

  private val imagedir = "/scala/swing/examples/tutorials/images/"
  private val placeholderIcon = new MissingIcon()

  /**
   * List of all the descriptions of the image files. These correspond one to
   * one with the image file names
   */
  private val imageFileNames = Array[String]("sunw01.jpg", "sunw02.jpg",
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

  // start the image loading SwingWorker in a background thread
  loadImages.execute()

  /**
   * SwingWorker class that loads the images a background thread and calls publish
   * when a new one is ready to be displayed.
   *
   * We use Unit as the first SwingWroker param as we do not need to return
   * anything from doInBackground().
   */
  def loadImages(): SwingWorker[Unit, ThumbnailAction] =
    new SwingWorker[Unit, ThumbnailAction]() {
      /**
       * Creates full size and thumbnail versions of the target image files.
       */
      override protected def doInBackground(): Unit = {
        println("doInBackground")
        for (i <- 0 until imageCaptions.length) {
          val iconOption: Option[ImageIcon] = IconDemoApp.createImageIcon(imagedir + imageFileNames(i), imageCaptions(i))

          val thumbAction: ThumbnailAction =
            if (iconOption.isDefined) {
              val thumbnailIcon: ImageIcon = new ImageIcon(getScaledImage(iconOption.get.getImage(), 32, 32))
              new ThumbnailAction(iconOption.get, thumbnailIcon, imageCaptions(i))
            } else {
              // the image failed to load for some reason
              // so load a placeholder instead
              new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions(i))
            }
          publish(thumbAction);
        }
      }

      /**
       * Process all loaded images.
       */
      override def process(chunks: java.util.List[ThumbnailAction]): Unit = {
        println("process")
        val it: java.util.Iterator[ThumbnailAction] = chunks.iterator()
        while (it.hasNext()) {
          val thumbAction = it.next()
          val thumbButton = new Button(thumbAction);
          // add the new button BEFORE the last glue
          // this centers the buttons in the toolbar
          buttonBar.contents.dropRight(1)
          buttonBar.contents += new Button(thumbAction)
          buttonBar.contents += Swing.Glue
        }
      }

      /**
       * Done.
       */
      override def done(): Unit = {
        println("Done")
      }
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
    val g2: Graphics2D = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    resizedImg;
  }
}

object IconDemoApp extends SimpleSwingApplication {
  def createImageIcon(path: String, description: String): Option[javax.swing.ImageIcon] = {
    val icon = Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))
    if (icon.isDefined) { icon.get.setDescription(description); icon } else None
  }
  
  lazy val top = new IconDemoApp() {
    title = "IconDemoApp"
  }
}

/**
 * @param Icon - The full size photo to show in the button.
 * @param Icon - The thumbnail to show in the button.
 * @param String - The description of the icon.
 */
class ThumbnailAction(photo: Icon, thumb: Icon, desc: String) extends Action(desc) {
  /**
   * The icon if the full image we want to display.
   */
  private val displayPhoto: Icon = photo

  // The LARGE_ICON_KEY is the key for setting the
  // icon when an Action is applied to a butt
  peer.putValue(javax.swing.Action.SHORT_DESCRIPTION, desc)

  // The short description becomes the tooltip of a button.
  peer.putValue(javax.swing.Action.LARGE_ICON_KEY, thumb)

  def apply() {}
}


class ToolBar extends scala.swing.Component with SequentialContainer.Wrapper {
  override lazy val peer: JToolBar = new JToolBar with SuperMixin
}
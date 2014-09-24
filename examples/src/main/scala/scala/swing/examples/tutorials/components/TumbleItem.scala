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
import java.io.BufferedInputStream
import java.awt.event.{ ActionEvent, ActionListener }
import java.awt.{ Color, Graphics, Graphics2D, Toolkit }
import javax.swing.{ ImageIcon, SwingUtilities, SwingWorker, Timer }

/*
 * Tutorial: How to Make Applets
 * http://docs.oracle.com/javase/tutorial/uiswing/components/applet.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TumbleItemProject/src/components/TumbleItem.java
 * 
 * @author jag
 * @author mem
 * @author kwalrath
 * @author ir71389
 *
 * TumbleItem.scala requires these files:
 *   all the images in the /scala/swing/examples/tutorials/images/tumble directory
 *     (or, if specified in the applet tag, another directory [dir]
 *     with images named T1.gif ... Tx.gif, where x is the total
 *     number of images [nimgs])
 *   the appropriate code to specify that the applet be executed,
 *     such as the HTML code in TumbleItem.html or TumbleItem.atag,
 *     or the JNLP code in TumbleItem.jnlp
 *
 */
class TumbleItem extends Applet {
  var loopslot = -1 //the current frame number
  var timer: Timer = null //the timer animating the images
  var dir: String = null //the directory relative to the codebase
  //from which the images are loaded
  var pause: Int = 0 //the length of the pause between revs
  var offset: Int = 0 //how much to offset between loops
  var speed: Int = 0 //animation speed
  var nimgs: Int = 0 //number of images to animate
  var maxWidth: Int = 0 //width of widest image
  var mywidth: Int = 0 //width of the applet's content pane
  var off: Int = 0 //the current offset
  var imgs: Array[ImageIcon] = null
  var worker: SwingWorker[Array[ImageIcon], Void] = null
  val ui = new ui()

  def loadAppletParameters(): Unit = {
    //Get the applet parameters.
    var at: String = getParameter("img")
    dir = if (at != null) at else "/scala/swing/examples/tutorials/images/tumble"
    at = getParameter("pause")
    pause = if (at != null) Integer.valueOf(at).intValue() else 1900
    at = getParameter("offset")
    offset = if (at != null) Integer.valueOf(at).intValue() else 0
    at = getParameter("speed")
    speed = if (at != null) (1000 / Integer.valueOf(at).intValue()) else 100
    at = getParameter("nimgs");
    nimgs = if (at != null) Integer.valueOf(at).intValue() else 16
    at = getParameter("maxwidth");
    maxWidth = if (at != null) Integer.valueOf(at).intValue() else 0
    imgs = new Array[ImageIcon](nimgs)
  }

  class ui extends UI with ActionListener {
    /**
     * Create the GUI. For thread safety, this method should
     * be invoked from the event-dispatching thread.
     */
    private def createGUI(): Unit = {
      //Animate from right to left if offset is negative.
      mywidth = getSize().width
      if (offset < 0) {
        off = mywidth - maxWidth;
      }

      //Custom component to draw the current image
      //at a particular offset.
      animator = new Animator()
      animator.opaque = true
      animator.background = Color.white
      setContentPane(animator.peer)

      //Put a "Loading Images..." label in the middle of
      //the content pane.  To center the label's text in
      //the applet, put it in the center part of a
      //BorderLayout-controlled container, and center-align
      //the label's text.
      val statusLabel = new Label("Loading Images...",
        Swing.EmptyIcon, Alignment.Center)
      animator.layout(statusLabel) = BorderPanel.Position.Center
    }

    var animator: Animator = null //the applet's content pane
    //Background task for loading images.
    worker = new SwingWorker[Array[ImageIcon], Void]() {
      override def doInBackground(): Array[ImageIcon] = {
        val innerImgs: Array[ImageIcon] = new Array[ImageIcon](nimgs)
        for (i <- 0 until nimgs) {
          innerImgs(i) = loadImage(i + 1)
        }
        innerImgs;
      }

      override def done(): Unit = {
        //Remove the "Loading images" label.
        animator.clear()
        loopslot = -1
        try {
          imgs = get()
        } catch {
          case ignore: java.lang.InterruptedException => {}
          case e: java.util.concurrent.ExecutionException =>
            val cause: Throwable = e.getCause()
            val why = if (cause != null)
              cause.getMessage()
            else
              e.getMessage()
            System.err.println("Error retrieving file: " + why)
        }
      }
    }

    //Called when this applet is loaded into the browser.
    override def init(): Unit = {
      loadAppletParameters()

      //Execute a job on the event-dispatching thread:
      //creating this applet's GUI.
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          def run(): Unit = {
            createGUI();
          }
        });
      } catch {
        case e: Exception =>
          System.err.println("createGUI didn't successfully complete")
      }

      //Set up timer to drive animation events.
      timer = new Timer(speed, this);
      timer.setInitialDelay(pause);
      timer.start();

      //Start loading the images in the background.
      worker.execute();

    }

    override def start(): Unit = {
      if (worker.isDone() && (nimgs > 1)) {
        timer.restart
      }
    }

    override def stop(): Unit = {
      timer.stop
    }

    //The component that actually presents the GUI.
    class Animator extends BorderPanel {

      protected def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g.asInstanceOf[Graphics2D]);

        if (worker.isDone() &&
          (loopslot > -1) && (loopslot < nimgs)) {
          if (imgs != null && imgs(loopslot) != null) {
            imgs(loopslot).paintIcon(peer, g, off, 0);
          }
        }
      }

      def clear(): Unit = {
        removeAll()
      }

    }

    //Handle timer event. Update the loopslot (frame number) and the
    //offset.  If it's the last frame, restart the timer to get a long
    //pause between loops.
    def actionPerformed(e: ActionEvent): Unit = {
      //If still loading, can't animate.
      if (worker.isDone()) {
        loopslot += 1

        if (loopslot >= nimgs) {
          loopslot = 0
          off += offset

          if (off < 0) {
            off = mywidth - maxWidth
          } else if (off + maxWidth > mywidth) {
            off = 0;
          }
        }
        animator.repaint()
        if (loopslot == nimgs - 1) {
          timer.restart()
        }
      }
    }
  }

  /**
   * Load the image for the specified frame of animation. Since
   * this runs as an applet, we use getResourceAsStream for
   * efficiency and so it'll work in older versions of Java Plug-in.
   */
  def loadImage(imageNum: Int): ImageIcon = {
    val path = dir + "/T" + imageNum + ".gif"
    val MAX_IMAGE_SIZE = 2400 //Change this to the size of
    //your biggest image, in bytes.
    var count = 0
    val imgStream = new BufferedInputStream(this.getClass().getResourceAsStream(path))
    if (imgStream != null) {
      val buf = new Array[Byte](MAX_IMAGE_SIZE)
      var ex = false
      try {
        count = imgStream.read(buf);
        imgStream.close();
      } catch {
        case ioe: java.io.IOException =>
          System.err.println("Couldn't read stream from file: " + path)
          count = -1
      }
      if (count <= 0) {
        System.err.println("Empty file: " + path);
        null
      } else
        new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf));
    } else {
      System.err.println("Couldn't find file: " + path);
      null
    }
  }

  override def getAppletInfo(): String = {
    "Title: TumbleItem v1.2, 23 Jul 1997\n" +
      "Author: James Gosling\n" +
      "A simple Item class to play an image loop."
  }

  override def getParameterInfo(): Array[Array[String]] = {
    val info = new Array[Array[String]](6)
    info(0) = Array("img", "string", "the directory containing the images to loop")
    info(1) = Array("pause", "int", "pause between complete loops; default is 3900")
    info(2) = Array("offset", "int", "offset of each image to simulate left (-) or " +
      "right (+) motion; default is 0 (no motion)")
    info(3) = Array("speed", "int", "the speed at which the frames are looped; " +
      "default is 100")
    info(4) = Array("nimgs", "int", "the number of images to be looped; default is 16")
    info(5) = Array("maxwidth", "int", "the maximum width of any image in the loop; " +
      "default is 0")
    info
  }

}

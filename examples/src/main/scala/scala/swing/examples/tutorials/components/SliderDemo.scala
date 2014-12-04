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
import scala.swing.event.{ ValueChanged, WindowDeiconified, WindowIconified }
import java.awt.Font
import java.awt.event.{ ActionEvent, ActionListener }
import javax.swing.Timer
import javax.swing.{ImageIcon, UIManager}

/**
 * Tutorial: How to Use Sliders
 * [[http://docs.oracle.com/javase/tutorial/uiswing/components/slider.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SliderDemoProject/src/components/SliderDemo.java]]
 * 
 * SliderDemo.scala requires all the files in the /scala/swing/examples/tutorials/images/doggy
 * directory.
 */
class SliderDemo(window: Window) extends BoxPanel(Orientation.Vertical) with ActionListener {
  //Set up animation parameters.
  val FpsMin = 0
  val FpsMax = 30
  val FpsInit = 15 //initial frames per second
  var frameNumber = 0
  val NumFrames = 14
  val images = new Array[Option[ImageIcon]](NumFrames)
  var frozen = false
  var delay = 1000 / FpsInit

  val sliderLabel = new Label("Frames Per Second") {
    horizontalAlignment = Alignment.Center
    xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
  }

  val framesPerSecond = new Slider {
    orientation = Orientation.Horizontal
    min = FpsMin
    max = FpsMax
    value = FpsInit
    //Turn on labels at major tick marks.
    majorTickSpacing = 10
    minorTickSpacing = 1
    paintTicks = true
    paintLabels = true
    border = Swing.EmptyBorder(0, 0, 10, 0)
    font = new Font("Serif", Font.ITALIC, 15)
  }

  val picture = new Label() {
    horizontalAlignment = Alignment.Center
    xLayoutAlignment = java.awt.Component.CENTER_ALIGNMENT
    Swing.Lowered
    border = Swing.CompoundBorder(
      Swing.BeveledBorder(Swing.Lowered), Swing.EmptyBorder(10, 10, 10, 10))
  }

  updatePicture(0); //display first frame
  contents += sliderLabel
  contents += framesPerSecond
  contents += picture
  border = Swing.EmptyBorder(10, 10, 10, 10)

  //Set up a timer that calls this object's action handler.
  val timer = new Timer(delay, this)
  timer.setInitialDelay(delay * 7) //We pause animation twice per cycle
  //by restarting the timer
  timer.setCoalesce(true)

  listenTo(framesPerSecond)
  listenTo(window)
  reactions += {
    case ValueChanged(`framesPerSecond`) =>
      if (!framesPerSecond.adjusting) {
        val fps: Int = framesPerSecond.value
        if (fps == 0) {
          if (!frozen) stopAnimation()
        } else {
          delay = 1000 / fps
          timer.setDelay(delay)
          timer.setInitialDelay(delay * 10)
          if (frozen) startAnimation()
        }
      }
    case WindowIconified(`window`) =>
      stopAnimation()
    case WindowDeiconified(`window`) =>
      startAnimation()
  }

  startAnimation()

  def startAnimation(): Unit = {
    //Start (or restart) animating!
    timer.start()
    frozen = false
  }

  def stopAnimation(): Unit = {
    //Stop the animating thread.
    timer.stop()
    frozen = true
  }

  //Called when the Timer fires.
  def actionPerformed(e: ActionEvent): Unit = {
    //Advance the animation frame.
    frameNumber = if (frameNumber == (NumFrames - 1)) 0 else frameNumber + 1

    updatePicture(frameNumber); //display the next picture

    if (frameNumber == (NumFrames - 1) || frameNumber == (NumFrames / 2 - 1)) {
      timer.restart()
    }
  }

  /** Update the label to display the image for the current frame. */
  def updatePicture(frameNum: Int): Unit = {
    //Get the image if we haven't already.
    if (images(frameNumber) == null) {
      images(frameNumber) = SliderDemo.createImageIcon(s"/scala/swing/examples/tutorials/images/doggy/T$frameNumber.gif")
    }

    //Set the image.
    images(frameNumber) match {
      case Some( frm ) => picture.icon = frm
      case None => picture.text = s"image #$frameNumber not found"
    }
  }
}

object SliderDemo extends SimpleSwingApplication {
  //TD UIManager.put("swing.boldMetal", false)
  /** Returns an ImageIcon, or null if the path was invalid. */
  def createImageIcon(path: String): Option[javax.swing.ImageIcon] = {
    Option(resourceFromClassloader(path)).map(imgURL => Swing.Icon(imgURL))
  }

  lazy val top = new MainFrame() {
    title = "SliderDemo"
    //Create and set up the content pane.
    contents = new SliderDemo(this)
  }
}


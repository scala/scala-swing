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
package scala.swing.examples.tutorials.concurrency

import scala.swing._
import scala.swing.event.ButtonClicked
import javax.swing.SwingWorker
import java.util.Random

/**
 * Tutorial: Tasks that Have Interim Results
 * [[http://http://docs.oracle.com/javase/tutorial/uiswing/concurrency/interim.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/concurrency/FlipperProject/src/concurrency/Flipper.java]]
 */
class Flipper extends MainFrame {
  var flipTask: FlipTask = null
  title = "Flipper"

  val headsText = new TextField(20) {
    editable = false
    horizontalAlignment = Alignment.Right
    border = Swing.BeveledBorder(Swing.Lowered)
  }
  val totalText = new TextField(20) {
    editable = false
    horizontalAlignment = Alignment.Right
    border = Swing.BeveledBorder(Swing.Lowered)
  }
  val devText = new TextField(20) {
    editable = false
    horizontalAlignment = Alignment.Right
    border = Swing.BeveledBorder(Swing.Lowered)
  }
  val startButton = new Button("Start")
  val stopButton = new Button("Stop") {
    enabled = false
  }
  val panel = new GridBagPanel() {
    val c = new Constraints()
    c.insets = new Insets(3, 10, 3, 10)
    layout(headsText) = c
    layout(totalText) = c
    layout(devText) = c
    layout(startButton) = c
    layout(stopButton) = c
  }

  contents = panel

  class FlipPair(val heads: Long, val total: Long) {}

  class FlipTask extends SwingWorker[Unit, FlipPair] {
    override def doInBackground(): Unit = {
      var heads: Long = 0
      var total: Long = 0
      val random: Random = new Random()
      while (!isCancelled()) {
        total += 1
        if (random.nextBoolean()) {
          heads += 1
        }
        publish(new FlipPair(heads, total))
      }
    }

    override def process(pairs: java.util.List[FlipPair]): Unit = {
      val pair: FlipPair = pairs.get(pairs.size - 1);
      headsText.text = pair.heads.toInt.toString
      totalText.text = pair.total.toInt.toString
      val dt = (pair.heads.toDouble) / (pair.total.toDouble) - 0.5
      devText.text = f"$dt%.10g"
    }
  }
  
  listenTo(startButton)
  listenTo(stopButton)
  reactions += {
    case ButtonClicked(`startButton`) =>
      startButton.enabled = false
      stopButton.enabled = true
      flipTask = new FlipTask()
      flipTask.execute()
    case ButtonClicked(`stopButton`) =>
      startButton.enabled = true
      stopButton.enabled = false
      flipTask.cancel(true)
      flipTask = null
  }
}

object Flipper extends SimpleSwingApplication {
  lazy val top = new Flipper()
}
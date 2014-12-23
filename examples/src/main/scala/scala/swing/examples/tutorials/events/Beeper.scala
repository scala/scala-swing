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
package scala.swing.examples.tutorials.events

import scala.swing._
import scala.swing.event.ButtonClicked
import java.awt.{Dimension,Toolkit }

/**
 * Tutorial: Introduction to Event Listeners
 * [[http://docs.oracle.com/javase/tutorial/uiswing/events/intro.html]]
 * 
 * Source code reference:
 * [[http://docs.oracle.com/javase/tutorial/uiswing/examples/events/BeeperProject/src/events/Beeper.java]]
 * 
 * Beeper.scala requires no other files.
 */
class Beeper extends BorderPanel {
  val button = new Button("Click Me") {
    preferredSize = new Dimension(200, 80)
  }
  layout(button) = BorderPanel.Position.Center
  listenTo(button)
  reactions += {
    case ButtonClicked(`button`) =>
      Toolkit.getDefaultToolkit().beep()
  }
  
}

object Beeper extends SimpleSwingApplication {
  //Create and set up the window.
  lazy val top = new MainFrame {
    title = "Beeper"
    contents = new Beeper()
  }
}
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

import java.awt.Font
import java.awt.GraphicsEnvironment
import scala.swing._
import scala.swing.event.ButtonClicked

/*
 * Tutorial: How to Use Buttons, Check Boxes, and Radio Buttons
 * http://docs.oracle.com/javase/tutorial/uiswing/components/button.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialogRunner.java
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialog.java
 */

class ListDialogRunner(frame: Frame) extends BoxPanel(Orientation.NoOrientation) {
  val names: Array[String] = Array("Arlo", "Cosmo", "Elmo", "Hugo",
    "Jethro", "Laszlo", "Milo", "Nemo",
    "Otto", "Ringo", "Rocco", "Rollo")

  //Create the labels.
  val intro = new Label("The chosen name:") {
    xLayoutAlignment = 0.50 // Center
  }
  val nameLabel = new Label(names(1)) {
    //Use a wacky font if it exists. If not, this falls
    //back to a font we know exists.
    font = getAFont()
    xLayoutAlignment = 0.50 // Center
  }
  intro.peer.setLabelFor(nameLabel.peer)

  //Create the button.
  val button = new Button("Pick a new name...") {
    xLayoutAlignment = 0.50 // Center
  }

  contents += intro
  contents += nameLabel
  contents += button

  listenTo(button)
  reactions += {
    case ButtonClicked(`button`) =>
      nameLabel.text = ListDialog.showDialog(
        frame,
        button,
        "Baby names ending in 0:",
        "Name chooser",
        names.asInstanceOf[Array[String]],
        nameLabel.text,
        "Cosmo  ")
  }

  def getAFont(): Font = {
    //initial strings of desired fonts
    val desiredFonts = Array[String]("French Script", "FrenchScript", "Bitstream Vera Sans")
    //Search for all installed font families.  The first
    //call may take a while on some systems with hundreds of
    //installed fonts, so if possible execute it in idle time,
    //and certainly not in a place that delays painting of
    //the UI (for example, when bringing up a menu).
    //
    //In systems with malformed fonts, this code might cause
    //serious problems; use the latest JRE in this case. (You'll
    //see the same problems if you use Swing's HTML support or
    //anything else that searches for all fonts.)  If this call
    //causes problems for you under  the latest JRE, please let
    //us know.
    val gEnv: GraphicsEnvironment =
      GraphicsEnvironment.getLocalGraphicsEnvironment
    val existingFamilyNames: Array[String] = gEnv.getAvailableFontFamilyNames
    val chosenFonts: Array[Font] =
      for {
        desiredFont <- desiredFonts
        existingFamilyName <- existingFamilyNames
        if existingFamilyName.startsWith(desiredFont)
      } yield new Font(existingFamilyName, Font.PLAIN, 30)
    if (chosenFonts.length > 0) {
      chosenFonts(0)
    } else {
      new Font("Serif", Font.ITALIC, 36)
    }
  }
}

object ListDialogRunner extends SimpleSwingApplication {
  lazy val top = new MainFrame() {
    title = "Name That Baby"
    //Create and set up the content pane.
    contents = new ListDialogRunner(this);
  }
}
scala-swing (mostly-unsupported) [<img src="https://api.travis-ci.org/scala/scala-swing.png?branch=java7"/>](https://travis-ci.org/scala/scala-swing?branch=java7)
=========

This is now community maintained. If you're interested in helping then contact @adriaanm or @andy1138 
Questions can asked on the [Google Group](https://groups.google.com/forum/#!forum/scala-swing)

This is a UI library that will wrap most of Java Swing for Scala in a straightforward manner. 
The widget class hierarchy loosely resembles that of Java Swing. The main differences are:

- In Java Swing all components are containers per default. This doesn't make much sense for
  a number of components, like TextField, CheckBox, RadioButton, and so on. Our guess is that 
  this architecture was chosen because Java lacks multiple inheritance. 
  In scala.swing, components that can have child components extend the Container trait.
-  Layout managers and panels are coupled. There is no way to exchange the layout manager
  of a panel. As a result, the layout constraints for widgets can be typed. 
  (Note that you gain more type-safety and don't loose much flexibility here. Besides 
  being not a common operation, exchanging the layout manager of a panel in Java 
  Swing almost always leads to exchanging the layout constraints for every of the panel's 
  child component. In the end, it is not more work to move all children to a newly created 
  panel.)
   
  The event system. TODO

- For more details see [SIP-8](docs/SIP-8.md)

The library comprises three main packages:

- `scala.swing`: All widget classes and traits.
- `scala.swing.event`: The event hierarchy.


Examples
---

A number of examples can be found in the `examples` project. 
A good place to start is  `[12] scala.swing.examples.UIDemo` (_index number may be different for you_). This gives pulls in the all the other examples into a tabbed window.


```
$ sbt examples/run

Multiple main classes detected, select one to run:

 [1] scala.swing.examples.ButtonApp
 [2] scala.swing.examples.Dialogs
 [3] scala.swing.examples.ComboBoxes
 [4] scala.swing.examples.CelsiusConverter2
 [5] scala.swing.examples.ListViewDemo
 [6] scala.swing.examples.HelloWorld
 [7] scala.swing.examples.LabelTest
 [8] scala.swing.examples.PopupDemo
 [9] scala.swing.examples.ColorChooserDemo
 [10] scala.swing.examples.LinePainting
 [11] scala.swing.examples.GridBagDemo
 [12] scala.swing.examples.UIDemo
 [13] scala.swing.examples.TableSelection
 [14] scala.swing.examples.CelsiusConverter
 [15] scala.swing.examples.SwingApp
 [16] scala.swing.examples.CountButton

Enter number:
```




Versions
---
  
- Version 1.xx.xx branch is compiled with java6, 
- Version 2.xx.xx compiled with java7, targeted to java6.

_Reason for different versions can be found at [SI-3634](https://issues.scala-lang.org/browse/SI-3634)_


Notes:

- Visual appearance of combo boxes using the GTK LaF is broken on JDKs < 1.7b30. This is a Java Swing problem.
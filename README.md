# scala-swing (mostly-unsupported)

[<img src="https://img.shields.io/travis/scala/scala-swing/java7.svg"/>](https://travis-ci.org/scala/scala-swing)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-swing_2.11.svg?label=latest%20release%20for%202.11"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-swing_2.11)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-swing_2.12.svg?label=latest%20release%20for%202.12"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-swing_2.12)
[![Stories in Ready](https://badge.waffle.io/scala/scala-swing.svg?label=ready&title=Ready)](http://waffle.io/scala/scala-swing)

This is now community maintained by @andy1138 & @benhutchison. If you're interested in helping then contact them or @adriaanm.

This is a UI library that will wrap most of Java Swing for Scala in a straightforward manner. 
The widget class hierarchy loosely resembles that of Java Swing. The main differences are:

- In Java Swing all components are containers per default. This doesn't make much sense for
  a number of components, like TextField, CheckBox, RadioButton, and so on. Our guess is that 
  this architecture was chosen because Java lacks multiple inheritance. 
  In scala-swing, components that can have child components extend the Container trait.
-  Layout managers and panels are coupled. There is no way to exchange the layout manager
  of a panel. As a result, the layout constraints for widgets can be typed. 
  (Note that you gain more type-safety and don't loose much flexibility here. Besides 
  being not a common operation, exchanging the layout manager of a panel in Java 
  Swing almost always leads to exchanging the layout constraints for every of the panel's 
  child component. In the end, it is not more work to move all children to a newly created 
  panel.)
   
  The event system. TODO.

- For more details see [SIP-8](docs/SIP-8.md)

The library comprises two main packages:

- `scala.swing`: All widget classes and traits.
- `scala.swing.event`: The event hierarchy.


## Examples

A number of examples can be found in the `examples` project. 
A good place to start is  `[12] scala.swing.examples.UIDemo` (_index number may be different for you_). This pulls in the all the other examples into a tabbed window.

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


## Versions
  
- The `1.0.x` branch is compiled with JDK 6 and released for Scala 2.10, 2.11. The 1.0.x releases can be used with both Scala versions on JDK 6 or newer.
- The `2.0.x` branch is compiled with JDK 8 and released for Scala 2.11 and 2.12.
  - When using Scala 2.11, you can use the Scala swing 2.0.x releases on JDK 6 or newer.
  - Scala 2.12 requires you to use JDK 8 (that has nothing to do with scala-swing).

The reason to have two versions is to allow for binary incompatible changes. Also, some java-swing classes were generified in JDK 7 (see [SI-3634](https://issues.scala-lang.org/browse/SI-3634)) and require the scala-swing soruces to be adjusted.


## API documentation (Scaladoc)

The API documentation for scala-swing can be found at [http://www.scala-lang.org/documentation/api.html](http://www.scala-lang.org/documentation/api.html).


## Current Work

Current changes are being made on the `2.0.x` branch.


## Known Issues

- Visual appearance of combo boxes using the GTK LaF is broken on JDKs < 1.7b30. This is a Java Swing problem.
whe

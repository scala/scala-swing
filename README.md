# scala-swing (mostly-unsupported)

[<img src="https://img.shields.io/travis/scala/scala-swing/java7.svg"/>](https://travis-ci.org/scala/scala-swing)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-swing_2.11.svg?label=latest%20release%20for%202.11"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-swing_2.11)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-swing_2.12.svg?label=latest%20release%20for%202.12"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-swing_2.12)
[<img src="https://img.shields.io/maven-central/v/org.scala-lang.modules/scala-swing_2.13.svg?label=latest%20release%20for%202.13"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.scala-lang.modules%20a%3Ascala-swing_2.13)

This is now community maintained by [@Sciss](https://github.com/Sciss) and [@benhutchison](https://github.com/benhutchison). If you are interested in helping then contact them or [@adriaanm](https://github.com/adriaanm).

## Adding an sbt dependency

To use scala-swing from sbt, add this to your `build.sbt`:

```
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"
```

## About scala-swing

This is a UI library that wraps most of Java Swing for Scala in a straightforward manner. 
The widget class hierarchy loosely resembles that of Java Swing. The main differences are:

- In Java Swing all components are containers per default. This does not make much sense for
  a number of components, like TextField, CheckBox, RadioButton, and so on. Our guess is that 
  this architecture was chosen because Java lacks multiple inheritance. 
  In scala-swing, components that can have child components extend the Container trait.
- Layout managers and panels are coupled. There is no way to exchange the layout manager
  of a panel. As a result, the layout constraints for widgets can be typed. 
  (Note that you gain more type-safety and do not loose much flexibility here. Besides 
  being not a common operation, exchanging the layout manager of a panel in Java 
  Swing almost always leads to exchanging the layout constraints for every of the panel's 
  child component. In the end, it is not more work to move all children to a newly created 
  panel.)
- Widget hierarchies are built by adding children to their parent container's `contents`
  collection. The typical usage style is to create anonymous subclasses of the widgets to
  customize their properties, and nest children and event reactions.
- The scala-swing event system follows a different approach than the underlying Java system.
  Instead of adding event listeners with a particular interface (such as `java.awt.ActionListener`),
  a `Reactor` instance announces the interest in receiving events by calling `listenTo` for
  a `Publisher`. Publishers are also reactors and listen to themselves per default as a convenience.
  A reactor contains an object `reactions` which serves as a convenient place to register observers
  by adding partial functions that pattern match for any event that the observer is interested in.
  This is shown in the examples section below.
- For more details see [SIP-8](docs/SIP-8.md)

The library comprises two main packages:

- `scala.swing`: All widget classes and traits.
- `scala.swing.event`: The event hierarchy.

## Examples

A number of examples can be found in the `examples` project. 
A good place to start is `[16] scala.swing.examples.UIDemo`.
This pulls in the all the other examples into a tabbed window.

```
$ sbt examples/run

Multiple main classes detected, select one to run:

 [1] scala.swing.examples.ButtonApp
 [2] scala.swing.examples.CelsiusConverter
 [3] scala.swing.examples.CelsiusConverter2
 [4] scala.swing.examples.ColorChooserDemo
 [5] scala.swing.examples.ComboBoxes
 [6] scala.swing.examples.CountButton
 [7] scala.swing.examples.Dialogs
 [8] scala.swing.examples.GridBagDemo
 [9] scala.swing.examples.HelloWorld
 [10] scala.swing.examples.LabelTest
 [11] scala.swing.examples.LinePainting
 [12] scala.swing.examples.ListViewDemo
 [13] scala.swing.examples.PopupDemo
 [14] scala.swing.examples.SwingApp
 [15] scala.swing.examples.TableSelection
 [16] scala.swing.examples.UIDemo

Enter number:
```

### Frame with a Button

The following example shows how to plug components and containers together and react to a
mouse click on a button:

```scala
import scala.swing._

new Frame {
  title = "Hello world"
  
  contents = new FlowPanel {
    contents += new Label("Launch rainbows:")
    contents += new Button("Click me") {
      reactions += {
        case event.ButtonClicked(_) =>
          println("All the colours!")
      }
    }
  }
  
  pack()
  centerOnScreen()
  open()
}
```

## Versions
  
- The `1.0.x` branch is compiled with JDK 6 and released for Scala 2.11 and 2.11. The 1.0.x releases can be used with both Scala versions on JDK 6 or newer.
- The `2.0.x` branch is compiled with JDK 8 and released for Scala 2.11 and 2.12.
  - When using Scala 2.11, you can use the Scala swing 2.0.x releases on JDK 6 or newer.
  - Scala 2.12 requires you to use JDK 8 (that has nothing to do with scala-swing).
- The `2.1.x` series adds support for Scala 2.13, while dropping Scala 2.10.

The reason to have different major versions is to allow for binary incompatible changes. Also, some java-swing classes were 
generified in JDK 7 (see [SI-3634](https://issues.scala-lang.org/browse/SI-3634)) and require the scala-swing sources to be adjusted.


## API documentation (Scaladoc)

The API documentation for scala-swing can be found
at [http://www.scala-lang.org/documentation/api.html](http://www.scala-lang.org/documentation/api.html).


## Current Work

Current changes are being made on the `work` branch.
Last published version is found on the `main` branch.

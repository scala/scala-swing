scala-swing (unsupported) [<img src="https://api.travis-ci.org/scala/scala-swing.png"/>](https://travis-ci.org/scala/scala-swing)
=========

We welcome contributions, but do not actively maintain this library.
If you're interested in becoming a maintainer, please contact @adriaanm.

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
  

The library comprises three main packages:

- `scala.swing`: All widget classes and traits.
- `scala.swing.event`: The event hierarchy.
- `examples.swing`: A set of demos. ( in ./docs )

<br>

ScalaDocs
---

Documentation for scala-swing included in 2.11.1 is can be found [here](http://www.scala-lang.org/api/2.11.1/scala-swing/#scala.swing.package)

Other version can be found at [http://www.scala-lang.org/documentation/api.html](http://www.scala-lang.org/documentation/api.html) 

<br><br>

Current Work
---

Current changes are being made on the **java7** branch. This is to mainly because of the EOL of java6 and the generification that happened between jdk6 and jdk7 requiring a difference in the source code. This class files are targeted at java6 and will run with all versions upwards. 

<br><br>

_Notes:_

- Visual appearance of combo boxes using the GTK LaF is broken on JDKs < 1.7b30. This is a Java Swing problem.
- The generification added to some java7 swing components make the course code incompatible between java6 and java7. This is not an issue with the generated .class files because of _Type Erasure_ will work across all versions of java from java6 upwards. See [Issue SI-3634](https://issues.scala-lang.org/browse/SI-3634)






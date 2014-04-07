scala-swing (unsupported) [<img src="https://api.travis-ci.org/scala/scala-swing.png?branch=java7"/>](https://travis-ci.org/scala/scala-swing?branch=java7)
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
- `scala.swing.test`: A set of demos.
    

Notes:

- Visual appearance of combo boxes using the GTK LaF is broken on JDKs < 1.7b30. This is a Java Swing problem.

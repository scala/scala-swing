lazy val swing = project.in(file("."))
  .settings(ScalaModulePlugin.scalaModuleSettings)
  .settings(ScalaModulePlugin.scalaModuleSettingsJVM)
  .settings(
    name := "scala-swing",
    OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}"),
    scalaModuleMimaPreviousVersion := Some("2.1.0"),
    // set the prompt (for this build) to include the project id.
    shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " },
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    // Adds a `src/main/scala-2.13+` source directory for Scala 2.13 and newer
    // and  a `src/main/scala-2.13-` source directory for Scala version older than 2.13
    unmanagedSourceDirectories in Compile += {
      val sourceDir = (sourceDirectory in Compile).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case _                       => sourceDir / "scala-2.13-"
      }
    }
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

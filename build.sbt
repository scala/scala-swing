lazy val swing = project.in(file("."))
  .settings(ScalaModulePlugin.scalaModuleSettings)
  .settings(ScalaModulePlugin.scalaModuleOsgiSettings)
  .settings(
    name := "scala-swing",
    OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}"),
    scalaModuleMimaPreviousVersion := Some("2.1.0"),
    // set the prompt (for this build) to include the project id.
    ThisBuild / shellPrompt := { state => Project.extract(state).currentRef.project + "> " },
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % Test,
      "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.0" % Test,
    ),
    // Adds a `src/main/scala-2.13+` source directory for Scala 2.13 and newer
    // and  a `src/main/scala-2.13-` source directory for Scala version older than 2.13
    Compile / unmanagedSourceDirectories += {
      val sourceDir = (Compile / sourceDirectory).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case _                       => sourceDir / "scala-2.13-"
      }
    }
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (swing / scalaVersion).value,
    run / fork := true,
    fork := true
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (swing / scalaVersion).value,
    run / fork := true,
    fork := true
  )

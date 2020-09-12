lazy val scalaTestVersion = "3.2.2"

lazy val commonSettings = Seq(
  crossScalaVersions += "0.27.0-RC1",
  scalaVersion := "0.27.0-RC1", // while working in IntelliJ
)

lazy val swing = project.in(file("."))
  .settings(ScalaModulePlugin.scalaModuleSettings)
  .settings(ScalaModulePlugin.scalaModuleOsgiSettings)
  .settings(commonSettings)
  .settings(
    name := "scala-swing",
    OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}"),
    scalaModuleMimaPreviousVersion := Some("2.1.0"),
    // set the prompt (for this build) to include the project id.
    ThisBuild / shellPrompt := { state => Project.extract(state).currentRef.project + "> " },
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-flatspec"       % scalaTestVersion % Test,
      "org.scalatest" %% "scalatest-shouldmatchers" % scalaTestVersion % Test,
    ),
    // Adds a `src/main/scala-2.13+` source directory for Scala 2.13 and newer
    // and  a `src/main/scala-2.13-` source directory for Scala version older than 2.13
    Compile / unmanagedSourceDirectories += {
      val sourceDir = (Compile / sourceDirectory).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case Some((0, _))            => sourceDir / "scala-2.13+" // Dotty
        case _                       => sourceDir / "scala-2.13-"
      }
    }
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(commonSettings)
  .settings(
    scalaVersion := (swing / scalaVersion).value,
    run / fork := true,
    fork := true
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(commonSettings)
  .settings(
    scalaVersion := (swing / scalaVersion).value,
    run / fork := true,
    fork := true
  )

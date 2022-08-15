lazy val scalaTestVersion = "3.2.10"

ThisBuild / crossScalaVersions := Seq("3.1.3", "2.13.8", "2.12.16", "2.11.12")
ThisBuild / scalaVersion := crossScalaVersions.value.head

// We use <epoch>.<major>.<minor> like 99% of Scala libraries.
// Versions are binary compatible within x.y.* but not within x.*.*
ThisBuild / versionScheme := Some("pvp")
ThisBuild / versionPolicyIntention := Compatibility.None  // 3.0.0

lazy val swing = project.in(file("."))
  .settings(ScalaModulePlugin.scalaModuleSettings)
  .settings(ScalaModulePlugin.scalaModuleOsgiSettings)
  .settings(
    name := "scala-swing",
    scalaModuleAutomaticModuleName := Some("scala.swing"),
    OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}"),
    // scalaModuleMimaPreviousVersion := Some("2.1.0"),  TODO re-enable after we have a 3.0 release
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
        case Some((3, _))            => sourceDir / "scala-2.13+" // Dotty
        case _                       => sourceDir / "scala-2.13-"
      }
    },
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(
    run / fork := true,
    fork := true,
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(
    run / fork := true,
    fork := true,
  )

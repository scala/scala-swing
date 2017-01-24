import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

name               := "scala-swing"

version            := "2.0.1-SNAPSHOT"

scalaVersion       := crossScalaVersions.value.head

crossScalaVersions := Seq("2.11.8", "2.12.1")

scalacOptions      ++= Seq("-deprecation", "-feature")

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

mimaPreviousVersion := None

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val swing = project.in( file(".") )

lazy val examples = project.in( file("examples") )
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

lazy val uitest = project.in( file("uitest") )
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

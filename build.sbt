import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

organization := "org.scala-lang.modules"

name                       := "scala-swing"

version := "2.0.0-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-target:jvm-1.6")

snapshotScalaBinaryVersion := "2.11"

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage     := Seq(s"scala.swing.*;version=${version.value}")

MimaPlugin.mimaDefaultSettings

MimaKeys.previousArtifact  := Some(organization.value % s"${name.value}_2.11" % "1.0.1")

// run mima during tests
test in Test := {
  MimaKeys.reportBinaryIssues.value
  (test in Test).value
}


// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }


lazy val swing = project.in( file(".") )

lazy val examples = project.in( file("examples") )
  .dependsOn(swing)
  .settings(
    scalaVersion := "2.11.1",
    fork in run := true,
    fork := true
  )

lazy val uitest = project.in( file("uitest") )
  .dependsOn(swing)
  .settings(
    scalaVersion := "2.11.1",
    fork in run := true,
    fork := true
  )






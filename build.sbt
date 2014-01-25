import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

organization := "org.scala-lang.modules"

scalaModuleSettings

name                       := "scala-swing"

version := "2.0.0-SNAPSHOT"

scalaVersion := "2.11.1"

snapshotScalaBinaryVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-target:jvm-1.6")

snapshotScalaBinaryVersion := "2.11"

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage     := Seq(s"scala.swing.*;version=${version.value}")

MimaPlugin.mimaDefaultSettings

MimaKeys.previousArtifact  := Some(organization.value % s"${name.value}_2.11.1" % "1.0.0")

// run mima during tests
test in Test := {
  MimaKeys.reportBinaryIssues.value
  (test in Test).value
}





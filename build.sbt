import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

name                       := "scala-swing"

version                    := "1.0.2-SNAPSHOT"

scalaVersion               := "2.11.0"

snapshotScalaBinaryVersion := "2.11"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.4" % "test"

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

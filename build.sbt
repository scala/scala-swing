import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

name                       := "scala-swing"

version                    := "1.0.0-SNAPSHOT"

scalaVersion               := "2.11.0-M8"

snapshotScalaBinaryVersion := "2.11.0-M8"

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage     := Seq(s"scala.swing.*;version=${version.value}")

MimaPlugin.mimaDefaultSettings

MimaKeys.previousArtifact  := Some(organization.value % s"${name.value}_2.11.0-RC1" % "1.0.0")

// run mima during tests
test in Test := {
  MimaKeys.reportBinaryIssues.value
  (test in Test).value
}

scalaModuleSettings

name                       := "scala-swing"

version                    := "1.0.0-SNAPSHOT"

scalaVersion               := "2.11.0-M8"

snapshotScalaBinaryVersion := "2.11.0-M8"

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

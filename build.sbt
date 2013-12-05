import VersionKeys._

organization := "org.scala-lang.modules"

name := "scala-swing"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.0-M7"

snapshotScalaBinaryVersion := "2.11.0-M7"

// important!! must come here (why?)
osgiSettings

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

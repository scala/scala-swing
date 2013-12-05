import VersionKeys._

organization := "org.scala-lang.modules"

name := "scala-swing"

version := "2.0.0-SNAPSHOT"

scalaVersion := "2.11.1"

snapshotScalaBinaryVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-target:jvm-1.6")

snapshotScalaBinaryVersion := "2.11"

// important!! must come here (why?)
osgiSettings

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

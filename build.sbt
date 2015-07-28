import com.typesafe.tools.mima.plugin.{MimaPlugin, MimaKeys}

scalaModuleSettings

name                       := "scala-swing"

version                    := "1.0.3-SNAPSHOT"

scalaVersion               := crossScalaVersions.value.head

crossScalaVersions         := Seq("2.11.7")

// important!! must come here (why?)
scalaModuleOsgiSettings

OsgiKeys.exportPackage     := Seq(s"scala.swing.*;version=${version.value}")

mimaPreviousVersion := Some("1.0.1")

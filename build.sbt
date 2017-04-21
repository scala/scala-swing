scalaModuleSettings

name    := "scala-swing"
version := "1.0.3-SNAPSHOT"

scalaVersionsByJvm := {
  val vs = List("2.11.11", "2.10.6")

  // Map[JvmMajorVersion, List[(ScalaVersion, UseForPublishing)]]
  Map(
    6 -> vs.map(_ -> true),
    7 -> vs.map(_ -> false),
    8 -> vs.map(_ -> false)
  )
}

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

mimaPreviousVersion := Some("1.0.1")

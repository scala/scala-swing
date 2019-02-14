import ScalaModulePlugin._

scalaModuleSettings

name               := "scala-swing"

version            := "2.1.1-SNAPSHOT"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature")

// Map[JvmMajorVersion, List[(ScalaVersion, UseForPublishing)]]
scalaVersionsByJvm in ThisBuild := Map(
   8 -> List("2.11.12", "2.12.8", "2.13.0-M5").map(_ -> true),
   9 -> List("2.11.12", "2.12.8", "2.13.0-M5").map(_ -> false),
  10 -> List("2.11.12", "2.12.8", "2.13.0-M5").map(_ -> false),
  11 -> List("2.11.12", "2.12.8", "2.13.0-M5").map(_ -> false),
  12 -> List("2.11.12", "2.12.8", "2.13.0-M5").map(_ -> false)
)

scalaVersion in ThisBuild := "2.13.0-M5"  // for testing

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

mimaPreviousVersion := Some("2.1.0")

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val swing = project.in(file("."))
  .settings(
    libraryDependencies += {
      val v = if (scalaVersion.value == "2.13.0-M5") "3.0.6-SNAP5" else "3.0.5"
      "org.scalatest" %% "scalatest" % v % Test
    }
  )

lazy val examples = project.in(file("examples"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

lazy val uitest = project.in(file("uitest"))
  .dependsOn(swing)
  .settings(
    scalaVersion := (scalaVersion in swing).value,
    fork in run := true,
    fork := true
  )

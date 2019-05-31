import ScalaModulePlugin._

scalaModuleSettings

name               := "scala-swing"

version            := "2.1.1"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature")

// Map[JvmMajorVersion, List[(ScalaVersion, UseForPublishing)]]
scalaVersionsByJvm in ThisBuild := Map(
   8 -> List("2.11.12", "2.12.8", "2.13.0-RC2").map(_ -> true),
   9 -> List("2.11.12", "2.12.8", "2.13.0-RC2").map(_ -> false),
  10 -> List("2.11.12", "2.12.8", "2.13.0-RC2").map(_ -> false),
  11 -> List("2.11.12", "2.12.8", "2.13.0-RC2").map(_ -> false),
  12 -> List("2.11.12", "2.12.8", "2.13.0-RC2").map(_ -> false)
)

scalaVersion in ThisBuild := "2.12.8"

OsgiKeys.exportPackage := Seq(s"scala.swing.*;version=${version.value}")

mimaPreviousVersion := Some("2.1.0")

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }

lazy val swing = project.in(file("."))
  .settings(
    libraryDependencies += {
      "org.scalatest" %% "scalatest" % "3.0.8-RC5" % Test
    },
    // Adds a `src/main/scala-2.13+` source directory for Scala 2.13 and newer
    // and  a `src/main/scala-2.13-` source directory for Scala version older than 2.13
    unmanagedSourceDirectories in Compile += {
      val sourceDir = (sourceDirectory in Compile).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case _                       => sourceDir / "scala-2.13-"
      }
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

lazy val baseName     = "fedex"
lazy val majorVersion = "0.1"

version         := majorVersion
publishArtifact := false

ThisBuild / organization           := "com.alexdupre"
ThisBuild / scalaVersion           := "3.3.5"
ThisBuild / versionScheme          := Some("strict")
ThisBuild / licenses               := Seq("BSD-style" -> url("https://opensource.org/license/BSD-2-Clause"))
ThisBuild / publishTo              := sonatypePublishToBundle.value
ThisBuild / sonatypeCredentialHost := Sonatype.sonatypeLegacy

lazy val sttpVersion  = "4.0.0-RC1"
lazy val circeVersion = "0.14.10"

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client4" %% "core"         % sttpVersion,
    "com.softwaremill.sttp.client4" %% "circe"        % sttpVersion,
    "io.circe"                      %% "circe-core"   % circeVersion,
    "io.circe"                      %% "circe-parser" % circeVersion
  ),
  sonatypeProjectHosting := Some(Sonatype.GitHubHosting("alexdupre", "fedex-scala", "Alex Dupre", "ale@FreeBSD.org"))
)

lazy val authorization = project
  .in(file("modules") / "authorization")
  .settings(
    name    := s"$baseName-authorization",
    version := s"$majorVersion.0",
    commonSettings
  )

def fedexProject(subName: String, minor: Int) = Project(subName, file("modules") / subName)
  .settings(
    name    := s"$baseName-$subName",
    version := s"$majorVersion.$minor",
    commonSettings
  )
  .dependsOn(authorization % "compile->compile;test->test")

lazy val ship  = fedexProject("ship", 0)
lazy val track = fedexProject("track", 0)

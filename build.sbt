lazy val baseName     = "fedex"
lazy val majorVersion = "0.1"

lazy val sttpVersion  = "4.0.0-RC1"
lazy val circeVersion = "0.14.10"

lazy val commonSettings = Seq(
  organization := "com.alexdupre",
  scalaVersion := "3.3.5",
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client4" %% "core"         % sttpVersion,
    "com.softwaremill.sttp.client4" %% "circe"        % sttpVersion,
    "io.circe"                      %% "circe-core"   % circeVersion,
    "io.circe"                      %% "circe-parser" % circeVersion
  ),
  versionScheme          := Some("strict"),
  publishTo              := sonatypePublishToBundle.value,
  publishMavenStyle      := true,
  licenses               := Seq("BSD-style" -> url("https://opensource.org/license/BSD-2-Clause")),
  sonatypeProjectHosting := Some(xerial.sbt.Sonatype.GitHubHosting("alexdupre", "fedex-scala", "Alex Dupre", "ale@FreeBSD.org"))
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

publishTo       := sonatypePublishToBundle.value
publishArtifact := false
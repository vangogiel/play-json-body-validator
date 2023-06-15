import CiCommands.{ ciBuild, devBuild }

scalaVersion := "2.13.6"
crossScalaVersions := Seq("2.12.10", "2.13.6")

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Ywarn-unused:imports",
  "-Ywarn-dead-code",
  "-Xlint:adapted-args",
  "-Xsource:2.13",
  "-Xfatal-warnings"
)

commands ++= Seq(ciBuild, devBuild)

coverageMinimumStmtTotal := 100
coverageFailOnMinimum := true

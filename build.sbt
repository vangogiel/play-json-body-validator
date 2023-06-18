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

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.9.2",
  "com.typesafe.play" %% "play" % "2.8.8",
  "org.typelevel" %% "cats-effect" % "2.3.0",
  "org.typelevel" %% "cats-effect" % "2.3.0",
  "org.julienrf" %% "play-json-derived-codecs" % "7.0.0",
  "net.logstash.logback" % "logstash-logback-encoder" % "6.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test",
  "io.circe" %% "circe-parser" % "0.14.1" % "test"
)

commands ++= Seq(ciBuild, devBuild)

coverageMinimumStmtTotal := 100
coverageFailOnMinimum := true

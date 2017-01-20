lazy val root =
  (project in file(".")).
  settings(
    scalaVersion := "2.11.8",
    name := "stp",
    TwirlKeys.templateFormats += ("tex" -> "play.twirl.api.TxtFormat"),
    sbt.Keys.scalacOptions ++= Seq("-feature", "-deprecation"),
    scalacOptions ++= Seq("-feature", "-deprecation", "-Xno-uescape"),
    libraryDependencies ++= Seq(
      "commons-io" % "commons-io" % "2.5",
      "ch.qos.logback" % "logback-classic" % "1.1.8",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
    )
  ).enablePlugins(SbtTwirl)

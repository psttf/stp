lazy val makeTex = taskKey[Unit]("Builds LaTeX source.")

lazy val root =
  (project in file(".")).
  settings(
    scalaVersion := "2.11.7",
    name := "stp",
    TwirlKeys.templateFormats += ("tex" -> "play.twirl.api.TxtFormat"),
    sbt.Keys.scalacOptions ++= Seq("-feature", "-deprecation")
  ).enablePlugins(SbtTwirl)

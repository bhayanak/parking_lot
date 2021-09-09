name := "parking-lot"

version := "0.1"

scalaVersion := "2.13.1"
lazy val scalaTestVersion = "3.2.2"

libraryDependencies ++= Seq(
  // testing
  "org.scalatest" %% "scalatest" % scalaTestVersion% Test
)


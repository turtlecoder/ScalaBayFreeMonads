import sbt._

name := "freeMonadSample"

version := "0.1"

val scalaLangVersion = "2.11.7"

scalaVersion := scalaLangVersion

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-feature"

scalacOptions in Test ++= Seq("-Yrangepos")

val scalazVersion = "7.2.0"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
  "org.specs2" %% "specs2-core" % "3.7" % "test",
  "org.scala-lang" % "scala-compiler" % scalaLangVersion,
  "org.scala-lang" % "scala-library" % scalaLangVersion,
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-concurrent" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion,
  "org.scalaz" %% "scalaz-iteratee" % scalazVersion
)

resolvers ++= Seq(
/*
  "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "http://oss.sonatype.org/content/repositories/releases",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases",
  */
  "Maven Central" at "https://repo1.maven.org/maven2"
)

scalacOptions in Test ++= Seq("-Yrangepos")


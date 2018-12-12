name := "pl-class14"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.12"
libraryDependencies += "com.ning" % "async-http-client" % "1.7.19"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.7"

parallelExecution in Test := false

scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings")


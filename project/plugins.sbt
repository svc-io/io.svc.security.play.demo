// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += "local nexus public" at "http://localhost:8081/nexus/content/groups/public"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.3")

addSbtPlugin("no.arktekk.sbt" % "aether-deploy" % "0.6")

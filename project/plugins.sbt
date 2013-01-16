// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += Resolver.url("sbt-plugin-releases", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.3")

addSbtPlugin("com.jsuereth" % "xsbt-gpg-plugin" % "0.6.1")

import sbt._
import sbt.Keys._
import PlayProject._
import scala.Some

object ApplicationBuild extends Build {

    val appName         = "io.svc.security.play.demo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "io.svc" %% "io-svc-security-play" % "0.1-SNAPSHOT" changing
    )

  val repositories =
    //Seq("local nexus public" at "http://localhost:8081/nexus/content/groups/public")
    Seq("Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/")

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(

    resolvers := repositories,

    credentials += Credentials(Path.userHome / ".m2" / ".credentials"),

    publishTo <<= version {
      v: String =>
        val nexus = "http://localhost:8081/"
        if (v.trim.endsWith("SNAPSHOT")) {
          Some("snapshots" at nexus + "nexus/content/repositories/snapshots")
        }
        else {
          Some("releases" at nexus + "nexus/content/repositories/releases")
        }
    },

    publishMavenStyle := true,

    //publishArtifact in Test := false,

    pomIncludeRepository := {
      x => false
    },

    testOptions in Test += Tests.Argument("junitxml")

  ).settings(
    addArtifact(Artifact(appName, "dist", "zip", "dist"), dist ).settings : _*
  ).settings(
    aether.Aether.aetherPublishSettings : _*
  )
}

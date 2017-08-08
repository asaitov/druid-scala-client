resolvers += "Scala Tools Nexus" at "http://nexus.tapad.com:8080/nexus/content/groups/aggregate/"

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.2")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.6")


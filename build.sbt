name := "Alfredo"

version := "1.0-SNAPSHOT"


libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  filters
)     


play.Project.playJavaSettings

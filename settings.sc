
import coursier.core.Authentication
import coursier.maven.MavenRepository

val scalaVersion = "2.13.8"
val scala212Version = "2.12.7"

val customRepositories = Seq(
  MavenRepository("https://maven.pkg.github.com/ranglang/packages",
    authentication= Some(Authentication(user="ranglang").withPassword(""))
  ),
  MavenRepository("http://dl.bintray.com/content/lightshed/maven"),
  MavenRepository("https://s01.oss.sonatype.org/content/repositories/snapshots/"),
  MavenRepository("https://papermc.io/repo/service/rest/repository/browse/maven-public/"),
  MavenRepository("http://clojars.org/repo"),
  MavenRepository("https://mvnrepository.com/artifact"),
  MavenRepository("https://dl.bintray.com/btomala/maven"),
  MavenRepository("https://dl.bintray.com/outworkerss/oss-releases/"),
  MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
  MavenRepository("http://maven.aliyun.com/nexus/content/groups/public/"),
  MavenRepository("http://maven.aliyun.com/nexus/content/repositories/snapshots"),
  MavenRepository("http://dl.john-ky.io/maven/releases"),
  MavenRepository("https://evolution.jfrog.io/artifactory/maven-local-releases"),
 MavenRepository("https://evolution.jfrog.io/artifactory/public"),
 MavenRepository("https://dl.bintray.com/rainier/maven"),
   MavenRepository("https://evolution.jfrog.io/artifactory/maven-local-releases"),
  MavenRepository("https://evolution.jfrog.io/artifactory/public")
)

val defaultScalacOptions1: Seq[String] = Seq(
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-deprecation",
  //  "-Ylog-classpath",
  "-Ymacro-annotations",
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:postfixOps",
  "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
  //  "-Ywarn-unused:-implicits",
  "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
  "-language:higherKinds",             // Allow higher-kinded types
  "-language:implicitConversions",     // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  //    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  //  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
//  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code", // Warn when dead code is identified.
//  "-Ymacro-annotations",
  "8"
)

//defaultScalacOptions1
val defaultScalacOptions12 = Seq(
  "-encoding",
  "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-deprecation",
  //  "-Ylog-classpath",
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:postfixOps",
  "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
  //  "-Ywarn-unused:-implicits",
  "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
  "-language:higherKinds",             // Allow higher-kinded types
  "-language:implicitConversions",     // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  //    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  //  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
//  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code", // Warn when dead code is identified.
//  -Ymacro-annotations
  "8"
)

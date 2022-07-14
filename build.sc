import java.util.jar.JarFile
import java.net.InetAddress
import mill.scalalib.{PublishModule, _}
import $file.dependencies
import $file.settings
import coursier.Repository
import mill.{Agg, _}
import mill.modules.Assembly._
import mill.scalalib._
import mill.scalalib.publish.{PomSettings, _}
import mill.scalalib.scalafmt.ScalafmtModule
import $ivy.`com.lihaoyi::mill-contrib-bloop:$MILL_VERSION`
import $file.dependencies
import $file.settings
import $file.env
import coursier.Repository
import jdk.internal.loader.BootLoader.packages
import jep.{envMaps, forkArgsEnv}
import mill.bsp.BSP.millSourcePath
import mill.define.Target
import mill.modules.Assembly._
import mill.scalalib._
import mill.scalalib.publish.{PomSettings, _}
import mill.scalalib.scalafmt.ScalafmtModule
import os.{/, Path, RelPath}

import scala.Predef.->

val doobieVersion = "0.9.0";
val scassandraVersion = "3.2.0"
val qiniuVersion = "7.2.20"
val webBasedVersion = "0.0.5"


trait SbtLqiongModule extends SbtModule with ScalafmtModule {

  override def scalaVersion: Target[String] = settings.scalaVersion

  override def scalacOptions: Target[Seq[String]] =
    settings.defaultScalacOptions1

  override def repositories: Seq[Repository] =
    super.repositories ++ settings.customRepositories

  override def assemblyRules = super.assemblyRules.++(
    Seq(
      Rule.Exclude(JarFile.MANIFEST_NAME),
      Rule.Append("application.conf"),
      Rule.Append("application.prod.conf"),
      Rule.AppendPattern(".*\\.conf"),
      Rule.ExcludePattern(".*\\.[sS][fF]"),
      Rule.AppendPattern(".*\\.dic"),
      Rule.ExcludePattern(".*\\.[dD][sS][aA]"),
      Rule.ExcludePattern(".*\\.[rR][sS][aA]"),
      Rule.ExcludePattern("META-INF/versions/\\.*"),
      Rule.Append(".*spring\\.*")
    )
  )
}


trait LPublishModule extends PublishModule {
  override def publishVersion = webBasedVersion 
  override def sonatypeUri: String = "https://maven.pkg.github.com/ranglang/claim"
  def pomSettings = PomSettings(
    description = "tools for shangche",
    organization = "tech.shangche",
    url = "https://maven.pkg.github.com/ranglang/packages",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("lihaoyi", "example"),
    developers = Seq(
      Developer("ranglang", "Rang Lang", "https://github.com/ranglang")
    )
  )
}

object `web-based` extends JepLqiongModule with ScalafmtModule with LPublishModule {

//  override def moduleDeps = Seq(crcle.asInstanceOf[PublishModule])

  override def scalacOptions: Target[Seq[String]] =
    settings.defaultScalacOptions1


  override def mainClass = Some("com.lqiong.main.A")

  override def ivyDeps = super.ivyDeps()
    .++(dependencies.lqiong.enumeratum)
    .++(dependencies.lqiong.json)
    .++(dependencies.lqiong.di)
    .++(dependencies.lqiong.jodaTime)
    .++(dependencies.lqiong.base1)
    .++(dependencies.lqiong.cal)
    .++(dependencies.lqiong.akkaBase)
    .++(dependencies.lqiong.common)
    .++(dependencies.lqiong.tapir)
    .++(dependencies.lqiong.promise)
    .++(dependencies.lqiong.slick)
    .++(Agg(dependencies.lqiong.aliyunLog))
    .++(
      dependencies.lqiong.cats
    ).++(
    dependencies.lqiong.logOthers
  )


  override def repositories: Seq[Repository] =
    super.repositories ++ settings.customRepositories

  override def scalaVersion: Target[String] = settings.scalaVersion
}

trait JepLqiongModule extends ScalaModule with ScalafmtModule {

  object test extends Tests {
    //    override def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.1")
    //    def testFramework = "utest.runner.Framework"
    override def testFrameworks = Seq("org.scalatest.tools.Framework")

    override def ivyDeps =
      Agg(
        dependencies.scalatest
      )
        .++(dependencies.testIvyDeps)
        .++(
          dependencies.lqiong.test
        )

    override def forkArgs = forkArgsEnv

    override def forkEnv = envMaps
  }

  override def scalaVersion: Target[String] = settings.scalaVersion

  override def scalacOptions: Target[Seq[String]] =
    settings.defaultScalacOptions1

  override def repositories: Seq[Repository] =
    super.repositories ++ settings.customRepositories

  override def assemblyRules = super.assemblyRules.++(
    Seq(
      Rule.Exclude(JarFile.MANIFEST_NAME),
      Rule.Append("application.conf"), // all application.conf files will be concatenated into single file
      Rule.Append("application.prod.conf"), // all application.conf files will be concatenated into single file
      Rule.AppendPattern(".*\\.conf"),
      Rule.ExcludePattern(".*\\.[sS][fF]"),
      Rule.AppendPattern(".*\\.dic"),
      Rule.ExcludePattern(".*\\.[dD][sS][aA]"),
      Rule.ExcludePattern(".*\\.[rR][sS][aA]"),
      Rule.ExcludePattern("META-INF/versions/\\.*"),
      Rule.Append(".*spring\\.*")
    )
  )

  val f = System.getProperty("LD_LIBRARY_PATH")
  println(s"fffffff ${f}");
  println(s"fffffff ${f}");
  println(s"fffffff ${f}");
  println(s"fffffff ${f}");
  val envMaps: Map[String, String] = Map(
    "PYTHONHOME" -> env.env.pythonHome,
    "CHROME_DRIVER_PATH" -> env.env.chromeDriver,
    "SCHEDULE_ENABLE" -> env.env.scheduleEnable,
    "ENV" -> "development",
    "JQ_USERNAME" -> env.env.jqUserName,
    "TEMP_ENABLED" -> env.env.tempEnable,
    "JQ_PASSWORD" -> env.env.jqPassword,
    "TUSHARE_AK" -> env.env.tuShare,
    "USER_EMAIL" -> env.env.userEmail,
    "EMAIL_HOST" -> env.env.mailHost,
    "EMAIL_PORT" -> env.env.mailPort,
    "EMAIL_USER_NAME" -> env.env.mailUserName,
    "EMAIL_PASSWORD" -> env.env.mailPassword,
    "TRADE_BACKEND" -> env.env.tradeBackend,
    "TRADE_USERNAME" -> env.env.tradeUserName,

    "CASSANDRA_PASSWORD" -> env.env.password,
    "CASSANDRA_HOST" -> env.env.host,
    "CASSANDRA_PORT" -> env.env.port,
    "CASSANDRA_USERNAME" -> env.env.userName,
    "CASSANDRA_KEYSPACE" -> env.env.keyspace,

    "PG_HOST" -> env.env.pgHost,
    "PG_PORT" -> env.env.pgPort,
    "PG_USERNAME" -> env.env.pgUserName,
    "PG_PASSWORD" -> env.env.pgPassword,
    "PG_DATABASE" -> env.env.pgDatabase,

    "TRADE_AK" -> env.env.tradeAk,
    "MEMCACHED" -> env.env.memcached
  )

  // bloop server -Djava.library.path=/Users/rang/opt/miniconda3/lib/python3.8/site-packages/jep
  // bloop run jep --args notion1  -- -J-Djava.library.path=/Users/rang/opt/miniconda3/lib/python3.8/site-packages/jep
  val forkArgsEnv = Seq(
    "-Dquill.binds.log=true",
    "--illegal-access=permit",
    s"-Djava.library.path=${env.env.jepHome}"
  )
}


trait LqiongModule extends ScalaModule with ScalafmtModule {


  override def scalaVersion: Target[String] = settings.scalaVersion

  override def scalacOptions: Target[Seq[String]] =
    settings.defaultScalacOptions1

  override def repositoriesTask = T.task {
    super.repositoriesTask() ++ settings.customRepositories
  }
}


object punchclock extends JepLqiongModule {

  override def ivyDeps =
    dependencies.lqiong.cats
      .++(
        dependencies.lqiong.di
      )
      .++(
      dependencies.lqiong.circleBase
    )
    .++(
        dependencies.lqiong.baseImg
      )
      .++(
        dependencies.lqiong.phantom
      )
      .++(
        dependencies.lqiong.sttpV3
      )
      .++(Agg(
        ivy"com.codahale.metrics:metrics-core:3.0.2",
        dependencies.lqiong.aliyunLog,
        dependencies.logging.cqLog
      ))
      .++(
        dependencies.lqiong.json
      )

  override def mainClass = Some("punchclock.M")

  override def forkArgs = forkArgsEnv

  override def forkEnv = envMaps

}


object circle extends JepLqiongModule {

  override def moduleDeps = Seq(punchclock)

  override def ivyDeps =
    dependencies.lqiong.cats
    .++(Agg(
      ivy"tech.shangche::web-based:${webBasedVersion}"
    ))
    .++(
      dependencies.lqiong.baseImg
    )
      .++(
      Agg(
          ivy"ch.megard::akka-http-cors:1.1.3" excludeOrg "com.typesafe.akka"
        )
      ).++(
      dependencies.lqiong.sttpV3
    )
      .++(
        dependencies.lqiong.cache
      )
    .++(Agg(
      dependencies.lqiong.aliyunLog,
      dependencies.logging.cqLog
    ))
    .++(
      dependencies.lqiong.slick
    )
    .++(
      dependencies.lqiong.tapir
    )
    .++(
      dependencies.lqiong.json
    )
    .++(
      dependencies.lqiong.cache
    )
    .++(
      dependencies.lqiong.kamon
    )

  override def mainClass = Some("e.Create1")

  override def forkArgs = forkArgsEnv

  override def forkEnv = envMaps

}


object jep extends JepLqiongModule {

  override def moduleDeps = Seq(circle, punchclock)

  override def ivyDeps = dependencies.lqiong.jep
    .++(dependencies.lqiong.cats)
    .++(Agg(
      ivy"tech.shangche::web-based:${webBasedVersion}"
    ))
    .++(
      dependencies.lqiong.baseImg
    )
    .++(
        dependencies.lqiong.sttpV3
    )
    .++(
      Agg(
        dependencies.logging.slf4jSimple
      )
    )
    .++(Agg(
      dependencies.lqiong.aliyunLog,
      dependencies.logging.cqLog
    ))
    .++(
      dependencies.lqiong.slick
    )
    .++(
      dependencies.lqiong.tapir
    )
    .++(
      dependencies.lqiong.json
    )
    .++(
      dependencies.lqiong.cache
    )
    .++(
      dependencies.lqiong.kamon
    )

  override def mainClass = Some("e.M")

  override def forkArgs = forkArgsEnv

  override def forkEnv = envMaps

}

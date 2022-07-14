import mill._
import mill.scalalib._

val qiniuVersion = "7.2.20"

val doobieVersion = "0.9.4";
val akkaVersion = "2.6.19"
val akkaHttpVersion = "10.2.1"
val akkaPersistenceCassandra = "1.0.5"
val quillVersion = "3.10.0"
val sparkVersion = "3.2.0"

val scassandraVersion = "3.1.0"
val phantomVersion = "2.59.0"
val scalacacheVersion = "0.28.0";
val catsVersion = "2.8.0"
val esVersion = "7.12.2"
val declineVersion = "1.3.0"
val kubernateApi = "1.1.1"

object lqiong {
  val akkaStreamVersion = "2.0.1"
  val kamonVersion = "2.1.8"
  val circeVersion = "0.13.0"
  val safeActorVersion = "3.0.0"
  val monixVersion = "3.3.0"
  val akkaMarathonVersion = "1.1.1"

  val korolevVersion = "0.16.4"
  val slickVersion = "3.3.3"

  val effekt = Agg(
    ivy"de.b-studios::effekt:0.4-SNAPSHOT",
  )

  val rainer = Agg(
    ivy"de.b-studios:effekt_2.12:0.4-SNAPSHOT",
    ivy"com.stripe::rainier-core:0.3.0"
  )

  val rabbitmq = Agg(
    ivy"com.newmotion::akka-rabbitmq:5.1.1"
  )

  val slick = Agg(
    ivy"com.typesafe.slick::slick:$slickVersion",
    ivy"com.typesafe.slick::slick-hikaricp:$slickVersion",
    ivy"org.postgresql:postgresql:9.4-1206-jdbc42",
    ivy"com.github.tototoshi::slick-joda-mapper:2.4.1",
    ivy"com.fasterxml.jackson.datatype:jackson-datatype-joda:2.9.0",
    ivy"com.typesafe.slick::slick-hikaricp:$slickVersion"
  )

  val mariadb = Agg(
    ivy"org.mariadb.jdbc:mariadb-java-client:2.3.0"
  )

  val di = Agg(
    ivy"org.wvlet.airframe::airframe:22.7.0"
  )

  val mysql = Agg(
    ivy"org.joda:joda-convert:2.2.1"
  ).++(slick).++(mariadb)

  val mysqlConnector = Agg(
    ivy"mysql:mysql-connector-java:8.0.18"
  )


  val es = Agg(

    ivy"com.sksamuel.elastic4s::elastic4s-core:$esVersion" excludeOrg ("com.fasterxml.jackson.core"),
    ivy"com.sksamuel.elastic4s::elastic4s-client-esjava:$esVersion" excludeOrg ("com.fasterxml.jackson.core"),
    ivy"com.sksamuel.elastic4s::elastic4s-json-circe:$esVersion" excludeOrg ("com.fasterxml.jackson.core")
  )

  val phantom = Agg(
    ivy"com.outworkers::phantom-connectors:$phantomVersion", //exclude(("io.dropwizard.metrics","metrics-jmx")),
    ivy"com.outworkers::phantom-dsl:$phantomVersion" //exclude(("io.dropwizard.metrics","metrics-jmx")),
  )
  val metrics = Agg(
    ivy"com.codahale.metrics:metrics-core:3.0.2"
  )

  val logOthers = Agg(
    ivy"net.logstash.logback:logstash-logback-encoder:5.2" excludeOrg ("com.fasterxml.jackson.core")
  )


  val enumeratum = Agg(
    ivy"com.beachape::enumeratum-slick:1.6.0",
    ivy"com.beachape::enumeratum-circe:1.6.1",
    ivy"com.beachape::enumeratum:1.6.1"
  )

  val talib1 = Agg(
    ivy"com.tictactec:ta-lib:0.4.0"
  )

  val jodaTime = Agg(
    ivy"joda-time:joda-time:2.10.5",
    ivy"org.joda:joda-convert:2.2.1"
  )

  val common = Agg(
    ivy"commons-codec:commons-codec:1.9",
    ivy"commons-io:commons-io:2.4"
  )

  val cal = Agg(
    ivy"io.github.carldata::timeseries:0.7.0"
  )


  val tapirVersion = "1.0.0-M3"
  val tapir = Agg(
    ivy"com.softwaremill.sttp.tapir::tapir-core:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-enumeratum:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-derevo:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-json-circe:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-openapi-docs:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-akka-http-server:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-openapi-docs:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-openapi-circe-yaml:$tapirVersion" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.tapir::tapir-swagger-ui:$tapirVersion" excludeOrg "com.typesafe.akka"
  )

  val inject = Agg(
    ivy"com.google.inject.extensions:guice-assistedinject:5.0.1",
    ivy"net.codingwell::scala-guice:5.0.2"
  )

  val cache = Agg(
    ivy"com.github.cb372::scalacache-memcached:$scalacacheVersion",
    ivy"com.github.cb372::scalacache-circe:$scalacacheVersion",
    ivy"com.github.cb372::scalacache-cats-effect:$scalacacheVersion"
  )

  val others = Agg(
    ivy"javax.servlet:javax.servlet-api:3.1.0",
    ivy"commons-logging:commons-logging:1.2"
  ).++(jodaTime).++(common).++(inject)

  val message = Agg(
    ivy"com.aliyun:aliyun-java-sdk-core:4.0.6",
    ivy"com.aliyun:aliyun-java-sdk-dysmsapi:1.1.0",
    ivy"com.github.jurajburian::mailer:1.2.4"
  )

  val auth = Agg(
    ivy"com.pauldijou::jwt-circe:3.0.1"
  )
  val cluster = Agg(
    ivy"io.monix::monix:$monixVersion",
    ivy"com.typesafe.akka::akka-cluster:$akkaVersion",
    ivy"com.typesafe.akka::akka-cluster-sharding-typed:$akkaVersion",
    ivy"com.typesafe.akka::akka-cluster-typed:$akkaVersion",
    ivy"com.typesafe.akka::akka-cluster-tools:$akkaVersion",
    ivy"com.typesafe.akka::akka-persistence-cassandra:1.0.5" exclude(
      ("com.fasterxml.jackson.core", "jackson-databind"),
      ("com.fasterxml.jackson.core", "jackson-core"),
      ("com.typesafe.akka", "akka-cluster-tools"), ("com.typesafe.akka", "akka-stream"), ("com.typesafe.akka", "akka-persistence-query")),
    ivy"com.lightbend.akka.management::akka-management-cluster-http:$akkaMarathonVersion" excludeOrg "com.typesafe.akka",
    ivy"com.lightbend.akka.management::akka-management-cluster-bootstrap:$akkaMarathonVersion" excludeOrg "com.typesafe.akka",
    ivy"com.lightbend.akka.management::akka-management:$akkaMarathonVersion",
    ivy"com.lightbend.akka.discovery::akka-discovery-kubernetes-api:$kubernateApi"
  )

  val stream = Agg(
    ivy"com.typesafe.akka::akka-stream:$akkaVersion",
    ivy"com.lightbend.akka::akka-stream-alpakka-amqp:$akkaStreamVersion",
    ivy"com.lightbend.akka::akka-stream-alpakka-cassandra:$akkaStreamVersion",
    ivy"com.lightbend.akka::akka-stream-alpakka-slick:$akkaStreamVersion",
    ivy"com.lightbend.akka::akka-stream-alpakka-elasticsearch:$akkaStreamVersion"
  )

  val markdown = Agg(
    ivy"com.vladsch.flexmark:flexmark:0.62.2"
  )

  val wechat = Agg(
    ivy"com.github.binarywang:weixin-java-mp:3.9.1.B",
    ivy"com.github.binarywang:weixin-java-cp:3.9.1.B"
  )

  val promise = Agg(
    ivy"com.softwaremill.retry::retry:0.3.3",
    ivy"com.github.takezoe::scala-retry:0.0.6"
  )

  val jfree = Agg(
    ivy"org.jfree:jcommon:1.0.24",
    ivy"org.jfree:jfreechart:1.5.3"
  )

  val qiniu = Agg(
    ivy"com.qiniu:qiniu-java-sdk:$qiniuVersion"
  )
  val talib = Agg(
    ivy"org.ta4j:ta4j-core:0.14"
  )


  val akkaBase = Agg(
    ivy"com.typesafe.akka::akka-stream:$akkaVersion",
    ivy"com.typesafe.akka::akka-actor-typed:$akkaVersion",

    ivy"com.softwaremill.retry::retry:0.3.3",
    ivy"io.altoo::akka-kryo-serialization:1.1.5" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::executor-tools:1.0.2" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::future-helper:1.0.6" excludeOrg "com.typesafe.akka",
    ivy"com.typesafe.akka::akka-slf4j:$akkaVersion",
    ivy"com.typesafe.akka::akka-actor:$akkaVersion",
    ivy"com.typesafe.akka::akka-http:$akkaHttpVersion",
  )

  val akka = Agg(
    ivy"com.twitter::chill:0.10.0",
    ivy"com.softwaremill.retry::retry:0.3.3",
    ivy"com.twitter::chill-akka:0.9.5" excludeOrg "com.typesafe.akka",
    ivy"com.typesafe.akka::akka-serialization-jackson:${akkaVersion}",
    ivy"com.typesafe.akka::akka-cluster-tools:$akkaVersion",
    ivy"com.typesafe.akka::akka-http-spray-json:$akkaHttpVersion",
    ivy"com.typesafe.akka::akka-persistence-typed:$akkaVersion",
    ivy"com.typesafe.akka::akka-persistence-query:$akkaVersion",
    ivy"com.lightbend.akka::akka-persistence-jdbc:5.0.4",

    ivy"com.swissborg::lithium:0.11.2",
    ivy"com.typesafe.akka::akka-discovery:$akkaVersion",
    ivy"io.altoo::akka-kryo-serialization:1.1.5" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::executor-tools:1.0.2" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::future-helper:1.0.6" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::safe-actor:$safeActorVersion" excludeOrg "com.typesafe.akka",
    ivy"com.evolutiongaming::safe-persistence:$safeActorVersion" excludeOrg "com.typesafe.akka",

    ivy"com.typesafe.akka::akka-persistence-query:$akkaVersion",
    ivy"com.typesafe.akka::akka-cluster:$akkaVersion",
    ivy"com.typesafe.akka::akka-slf4j:$akkaVersion",
    ivy"com.typesafe.akka::akka-remote:$akkaVersion",
    ivy"com.typesafe.akka::akka-actor:$akkaVersion",
    ivy"com.typesafe.akka::akka-http:$akkaHttpVersion",
    ivy"com.typesafe.akka::akka-http-xml:$akkaHttpVersion",
    ivy"io.kamon::kamon-bundle:2.2.3",
    ivy"io.kamon::kamon-apm-reporter:2.2.3",
  )

  val circleBase = Agg(
    ivy"io.circe::circe-core:$circeVersion",
    ivy"io.circe::circe-shapes:$circeVersion",
    ivy"io.circe::circe-generic:$circeVersion",
    ivy"io.circe::circe-parser:$circeVersion",
    ivy"io.circe::circe-generic-extras:$circeVersion"
  )

  val circle = circleBase ++ Agg(
    ivy"org.typelevel::cats-core:$catsVersion",
    ivy"io.spray::spray-json:1.3.5",
    ivy"com.davegurnell::bridges:0.24.0"
  )

  val scassandra = Agg(
    ivy"com.evolutiongaming::scassandra:$scassandraVersion" exclude (("com.datastax.cassandra", "cassandra-driver-core"))
    //      exclude (("com.fasterxml.jackson.core", "jackson-databind"))
  )
  val hash = Agg(
    ivy"com.github.t3hnar::scala-bcrypt:4.3.0"
  )

  val jackson = Agg(
    ivy"com.fasterxml.jackson.core:jackson-databind:2.5.2"
  )


  val cronish = Agg(
    ivy"com.github.philcali::cronish:0.1.3"
  )

  val jep = Agg(

    ivy"black.ninia:jep:3.9.1",
    ivy"com.datastax.oss:java-driver-core:4.6.1" exclude (("com.fasterxml.jackson.core", "jackson-databind"))
  )

  val retry = Agg(
    ivy"com.softwaremill.retry::retry:0.3.3"
  )

  val spark = Agg(
    ivy"org.apache.spark::spark-core:$sparkVersion" exclude (("org.slf4j", "slf4j-log4j12")),
    ivy"org.apache.spark::spark-sql:$sparkVersion" exclude (("org.slf4j", "slf4j-log4j12"))
  )

  val shale = Agg(
    ivy"com.chuusai::shapeless:2.3.3",
    ivy"io.underscore::slickless:0.3.6"
  )

  val kamon = Agg(
    ivy"io.kamon::kamon-bundle:2.2.2",
    ivy"io.kamon::kamon-apm-reporter:2.2.2"
  )
  val html = Agg(
    ivy"com.github.fomkin::levsha-core:1.0.1"
  )
  val json = Agg(
    ivy"ch.megard::akka-http-cors:0.4.3" excludeOrg "com.typesafe.akka",
    ivy"de.heikoseeberger::akka-http-circe:1.39.2" excludeOrg "com.typesafe.akka"
  ).++(circle)

  val email = Agg(
    ivy"com.github.daddykotex::courier:3.0.0-M1"
  )
  val dev = Agg(
    ivy"com.monovore::decline:2.0.0",
    ivy"io.github.zhongwm::cable:0.4.1",
    ivy"com.monovore::decline-enumeratum:2.0.0"
  )

  val decline = Agg(
    ivy"com.monovore::decline:$declineVersion",
    ivy"com.monovore::decline-effect:$declineVersion",
    ivy"com.monovore::decline-enumeratum:$declineVersion",
    ivy"io.github.zhongwm::cable:0.4.0"
  )

  val sup = Agg(
    ivy"com.kubukoz::sup-core:0.9.0-M6" ,
    ivy"com.kubukoz::sup-akka-http:0.7.0",
    ivy"com.softwaremill.sttp::async-http-client-backend-cats:1.7.2",
    ivy"com.kubukoz::sup-circe:0.7.0",
    ivy"com.kubukoz::sup-scalacache:0.7.0",
    ivy"com.kubukoz::sup-doobie:0.7.0" exclude (("org.typelevel", "cats-effect")),
    ivy"com.kubukoz::sup-sttp:0.7.0"
  )


  val sttpV3Version = "3.5.2"


  val sttpV3 = Agg(
//    ivy"com.softwaremill.sttp.client3::core:${sttpV3Version}" excludeOrg "com.typesafe.akka",
//    ivy"com.softwaremill.sttp.client3::circe:${sttpV3Version}" excludeOrg "com.typesafe.akka",
//    ivy"com.softwaremill.sttp.client3::akka-http-backend:${sttpV3Version}" excludeOrg "com.typesafe.akka",
//    ivy"com.softwaremill.sttp.client3::async-http-client-backend-future:${sttpV3Version}" excludeOrg "com.typesafe.akka"
  )

  val sttp = Agg(
    ivy"com.softwaremill.sttp.client::core:2.2.9" excludeOrg "com.typesafe.akka",
    ivy"com.softwaremill.sttp.client::akka-http-backend:2.2.9" excludeOrg "com.typesafe.akka"
  )

  val cats = Agg(
    ivy"org.typelevel::cats-core:$catsVersion",
    ivy"org.typelevel::cats-effect:2.5.1",
    //  # https://mvnrepository.com/artifact/org.typelevel/cats-effect
    //    'org.typelevel:cats-effect_3:jar:3.4-387-e167c59'
    ivy"org.typelevel::log4cats-core:2.1.1" exclude (("org.typelevel", "cats-effect"))
  )


  //  val sttp = Agg(
  //    ivy"com.softwaremill.sttp.client::core:2.0.0-RC7",
  //    ivy"com.github.daddykotex::courier:3.0.0-M1"
  //  )

  val quill = Agg(
    ivy"io.getquill::quill-sql:$quillVersion" exclude (("org.slf4j", "slf4j-log4j12")),
    ivy"io.getquill::quill-core:$quillVersion",
    ivy"io.getquill::quill-jdbc:$quillVersion",
    ivy"io.getquill::quill-async:$quillVersion",
    ivy"io.getquill::quill-async-postgres:$quillVersion"

  )
  val tofu = Agg(
    //    ivy"ru.tinkoff::tofu-core:0.10.3"  exclude(("org.typelevel", "cats-effect")),
    ivy"io.monix::monix:3.2.2" exclude (("org.typelevel", "cats-effect"))

  )


  val base1 = Agg(
    ivy"io.scalaland::chimney:0.3.2",
    ivy"org.picoworks::pico-hashids:4.5.151"
  )

  val plotly = Agg(
    ivy"org.plotly-scala::kaleido:0.1.0" exclude(
      ("org.slf4j", "log4j-over-slf4j"),
      ("org.apache.logging.log4j", "log4j-slf4j-impl"))
  )

  val aliyunLog = ivy"com.aliyun.openservices:aliyun-log-logback-appender:0.1.15"

  val baseImg = Agg(
    ivy"net.unmz.java:unmz-common-util:1.0.23",
    ivy"org.jsoup:jsoup:1.12.1",
    //    ivy"net.unmz.java.wechat.pay:wechat-pay-sdk:1.0.12",
    ivy"com.wix::accord-core:0.7.4",
    ivy"com.kailuowang::henkan-convert:0.6.4",
    ivy"io.scalaland::chimney:0.3.2",
    ivy"com.kailuowang::henkan-optional:0.6.4",
    ivy"com.github.pureconfig::pureconfig:0.12.3",
    //    ivy"com.hazelcast:hazelcast:3.8",
    ivy"com.github.krzemin::octopus:0.4.1"
  )

  val base = Agg(
    ivy"net.unmz.java:unmz-common-util:1.0.23",
    ivy"org.jsoup:jsoup:1.12.1",
    ivy"net.unmz.java.wechat.pay:wechat-pay-sdk:1.0.12",
    ivy"com.wix::accord-core:0.7.4",
    ivy"com.kailuowang::henkan-convert:0.6.4",
    ivy"io.scalaland::chimney:0.3.2",

    ivy"com.kailuowang::henkan-optional:0.6.4",
    ivy"com.github.pureconfig::pureconfig:0.12.3",
    ivy"com.hazelcast:hazelcast:3.8",
    ivy"com.github.krzemin::octopus:0.4.1"
  )

  val config = Agg(
    ivy"com.typesafe:config:1.4.1"
  )

  val monovore = Agg(
    ivy"com.monovore::decline:1.3.0",
    ivy"com.monovore::decline-enumeratum:1.3.0"

  )


  val postgresql = Agg(
    ivy"org.postgresql:postgresql:42.1.4",
    ivy"com.lightbend.akka::akka-persistence-r2dbc:0.4.0",
    ivy"io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE",
  )

  val doobie = Agg(
//    ivy"com.github.tototoshi::slick-joda-mapper:2.4.1",
//    ivy"com.github.nscala-time::nscala-time:2.30.0",
//    ivy"org.joda:joda-convert:2.2.1",
//    ivy"org.mariadb.jdbc:mariadb-java-client:2.4.0",
//    ivy"org.tpolecat::doobie-core:$doobieVersion"
  )

  val selenium = Agg(
    ivy"org.jsoup:jsoup:1.13.1",
    //    ivy"org.seleniumhq.selenium:selenium-java:3.141.59",
    ivy"org.seleniumhq.selenium:selenium-java:4.1.2"
  )

  val test = Agg(
    ivy"com.typesafe.akka::akka-testkit:$akkaVersion",
    ivy"com.typesafe.akka::akka-actor-testkit-typed:$akkaVersion",
    ivy"com.typesafe.akka::akka-http-testkit:$akkaHttpVersion"
  )


  val notion = Agg(
    //  ivy"tw.yukina.notion.sdk:notion-sdk-java:0.0.4",
    ivy"org.jraf:klibnotion-jvm:1.11.0"
  )
}

/** Logging. */
object logging {
  val log4s = ivy"org.log4s::log4s:1.9.0"
  val slf4jApi = ivy"org.slf4j:slf4j-api:1.7.25"
  val slf4jSimple = ivy"org.slf4j:slf4j-simple:1.7.25"
  val cqLog = ivy"ch.qos.logback:logback-classic:1.2.11"
  //  ch.qos.logback" % "logback-classic" % "1.2.11"
}

/** Enumerations. */
val enumeratum = ivy"com.beachape::enumeratum:1.6.1"

/** Tests. */
val scalatest = ivy"org.scalatest::scalatest:3.2.4"

def testIvyDeps = Agg(
)

def scalaTestIvyDeps = Agg(
  ivy"com.typesafe.akka::akka-testkit:$akkaVersion",
  ivy"com.typesafe.akka::akka-persistence-testkit:$akkaVersion",
  ivy"com.typesafe.akka::akka-http-testkit:$akkaHttpVersion"
)


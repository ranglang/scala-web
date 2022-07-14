package e

import a.PunchClockDatabase
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.event.LoggingAdapter
import akka.http.scaladsl.Http
import com.lqiong.OpenAPIYaml
import com.lqiong.a.DBconfig
import com.lqiong.circle.PgDao
import com.lqiong.circle.controller.{CircleTapirController, PunchClockTapirController}
import com.typesafe.config.{Config, ConfigFactory}
import wvlet.airframe._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
class App1(c: CircleTapirController, d: PunchClockTapirController) {
  // Do something with x, y, and z

  def route = OpenAPIYaml.getList(List(c, d))
}

object M extends App {
  implicit val a: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "sss")

  lazy val globalConfig = ConfigFactory.load()
  implicit val executionContext: ExecutionContext = a.executionContext

  final val f = PgDao.db
  val logger = a.classicSystem.log
  def run() = {
    val design: Design =
      newDesign
        .bind[Config]
        .toInstance(globalConfig)
        .bind[PunchClockDatabase]
        .toProvider { (d1: DBconfig) =>
          new PunchClockDatabase(d1.tribe4Connector)
        }
        .bind[ActorSystem[Nothing]]
        .toInstance(a) // Bind type X to a concrete instance
        .bind[ExecutionContext]
        .toInstance(executionContext) // Bind type X to a concrete instance
        .bind[LoggingAdapter]
        .toInstance(logger) // Bind type X to a concrete instance


    import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
    design.build[App1] { app =>
      Http().newServerAt("0.0.0.0", 8080).bind(cors(){app.route})
    }
  }

  run()
}

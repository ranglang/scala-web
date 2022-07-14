package punchclock

import a.PunchClockDatabase
import com.User
import com.datastax.driver.core.utils.UUIDs
import com.lqiong.a.{DBconfig, PunchClockRecord, PunchService}
import com.typesafe.config.{Config, ConfigFactory}
import org.joda.time.DateTime
import wvlet.airframe._

import java.util.UUID
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration.DurationInt

class App1(x: PunchService) {
  // Do something with x, y, and z

  def printName = {
    println(s"x ${x}")
    val d = PunchClockRecord(
        1,
        "",
        User.user,
        DateTime.parse("2022-10-01")
      )
//    Await.result(
//      x.run(
//        d
//      ),
//      50.seconds
//    )
    val r = Await.result(
      x.select(
        d
      ),
      50.seconds
    )
    println(s"r ${r}")
  }
}

object M extends App {

  lazy val globalConfig = ConfigFactory.load()

  def run() = {

//    .bind[ExecutionContextExecutor]
//      .toInstance(scala.concurrent.ExecutionContext.global)
    val design: Design =
      newDesign
        .bind[Config]
        .toInstance(globalConfig)
        .bind[PunchClockDatabase]
        .toProvider { (d1: DBconfig) =>
          new PunchClockDatabase(d1.tribe4Connector)
        }

    // Resolve D1 and D2

    design.build[App1] { app =>
      // Do something with App
      app.printName
    }
  }
  run()
}

package e

import com.lqiong.base.slick1.CustomSlickProfile.api._
import com.lqiong.circle.model.Piece
import com.lqiong.circle.{MissionTables, PgDao}
import org.joda.time.DateTime

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Create1 extends App {
  def run = {

    val d: slick.jdbc.JdbcBackend.Database = PgDao.db
//    val f2 = d.run(MissionTables.pieces.schema.create)

    import com.lqiong.base.slick1.CustomSlickProfile.api._
    val data = Piece(
      id = 1,
      time = DateTime.now,
      themeId = 1,
      themeName = "",
      content = UUID.randomUUID(),
      index = 1,
      status = 0,
      overdue = false,
      title = Some("sssss")
    )
    val f2 = d
      .run(MissionTables.themes.filter(_.id === data.themeId).result)
      .flatMap(item => {
        val head = item.head
        val in = MissionTables.pieces returning MissionTables.pieces
          .map(_.id) into ((item, id) => item.copy(id = id))
        val f = d
          .run(in += data.copy(themeName = head.name))
        f
      })

    //    val f1 = d.run(MissionTables.themes.schema.create)
    //  val f1 = d.run(MissionTables.missionMessage.schema.create)
//    println("OK1")
//    Await.result(f1, 5.minutes)
    println("OK2")
    Await.result(f2, 5.minutes)
    println("OK")
  }
  run
}

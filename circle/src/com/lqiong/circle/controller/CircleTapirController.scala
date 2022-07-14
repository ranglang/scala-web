package com.lqiong.circle.controller

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.lqiong.base.common.model.RetMsg
import com.lqiong.base.common.modeltrait._
import com.lqiong.circle.{MissionTables, PgDao}
import com.lqiong.circle.model.{Piece, Theme, UpdateMarkPiece}
import com.lqiong.controller.{LqiongErrorHandler, TapirController}
import com.lqiong.sq.model.ColumnTrait
import org.joda.time.DateTime
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.{endpoint, header, query, AnyEndpoint, _}

import java.sql.Timestamp
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class CircleTapirController(
    override val logger: LoggingAdapter,
    implicit val ex: ExecutionContext
) extends TapirController
    with LqiongErrorHandler
    with ColumnTrait {

  val d = PgDao.db

  private def deletePiece() = {
    endpoint.delete
      .in("v1" / "circle" / "piece")
      .in(query[Long]("id"))
      .prependIn("api"/"circle")
      .out(
        jsonBody[RetMsg]
      )
      .errorOut(jsonBody[RetMsg])
  }

  private def createPiece() = {
    endpoint.post
      .in("v1" / "circle" / "piece" / "create")
      .in(jsonBody[Piece])
      .prependIn("api"/"circle")
      .out(
        jsonBody[RetMsg]
      )
      .errorOut(jsonBody[RetMsg])
  }

  private def themeList() = {
    endpoint.get
      .in("v1" / "circle" / "theme" / "list")
      .prependIn("api"/"circle")
      .out(
        jsonBody[NoSqlPagingListData[Theme]]
      )
  }

  private def today() = {
    endpoint.get
      .in("v1" / "circle" / "theme" / "current")
      .in(query[Option[Long]]("themeId"))
      .prependIn("api"/"circle")
      .out(
        jsonBody[NoSqlPagingListData[Piece]]
      )
  }

  private def updatePiece() = {
    endpoint.post
      .in("v1" / "circle" / "piece" / "mark")
      .in(jsonBody[UpdateMarkPiece])
      .prependIn("api"/"circle")
      .out(
        jsonBody[RetMsg]
      )
      .errorOut(jsonBody[RetMsg])
  }

  private def createTheme() = {
    endpoint.post
      .in("v1" / "circle" / "theme" / "create")
      .in(jsonBody[Theme])
      .prependIn("api"/"circle")
      .out(
        jsonBody[RetMsg]
      )
      .errorOut(jsonBody[RetMsg])
  }
  import com.lqiong.base.slick1.CustomSlickProfile.api._
  implicit def jodaTimeMapping1: BaseColumnType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dateTime => new Timestamp(dateTime.getMillis),
      timeStamp => new DateTime(timeStamp.getTime)
    )

  private def getAllTheme(): Future[NoSqlPagingListData[Theme]] = {
    val f1 = MissionTables.themes.result
    d.run(f1).map(_.toList).map(NoSqlPagingClass.okList)
  }

  private def deletePeciceImpl(id: Long): Future[RetMsg] = {
    val f1 = MissionTables.pieces.filter(_.id === id).delete
    d.run(f1).map(r => RetMsg(code = ConstantMSG.RET_OK))
  }

  private def getTodayPieces(data: Option[Long]) = {
    val end = DateTime.now
      .withHourOfDay(23)
      .withMinuteOfHour(59)
      .withSecondOfMinute(59)
      .withMillisOfSecond(59)
      .toDateTime()
    val f1 = MissionTables.pieces
      .filter(item => (item.time < end))
      .filterOpt(data)(_.themeId === _)
      .result
    d.run(f1).map(_.toList).map(NoSqlPagingClass.okList)
  }

  private def updateMarkPiece(data: UpdateMarkPiece): Future[RetMsg] = {
    import com.lqiong.base.slick1.CustomSlickProfile.api._
    val f = d
      .run(
        MissionTables.pieces
          .filter(_.themeId === data.themeId)
          .filter(_.id === data.id)
          .take(1)
          .result
      )
      .flatMap(item => {
        val head = item.head

        val previouse: Int = head.status

        val current: Int = data.delta // delta delta

        val data1: (Int, Int) = previouse match {
          case zero if zero == 0        => (1, 2)
          case a if 1 to 3 contains a   => (3, a + current)
          case b if 4 to 6 contains b   => (5, b + current)
          case c if 7 to 9 contains c   => (9, c + current)
          case d if 10 to 12 contains d => (11, d + current)
          case e if 13 to 15 contains e => (15, e + current)
          case f if 16 to 18 contains f => (25, f + current)
          case g if 19 to 21 contains g => (50, g + current)
          case e if 22 to 25 contains e => (80, e + current)
          case _                        => (100, 25)
        }

        val in = MissionTables.pieces
          .filter(_.themeId === data.themeId)
          .filter(_.id === data.id)
          .map(r => (r.time, r.status))
          .update((DateTime.now().plusDays(data1._1), data1._2 + previouse))
        val f = d
          .run(in)
          .map(_ => RetMsg(code = ConstantMSG.RET_OK, ""))

        f
      })
    f recover { case NonFatal(x) =>
      logger.error(" insertItem {}", x)
      RetMsg(code = ConstantMSG.RET_FAIL, x.toString)
    }
  }

  private def insertPiece(data: Piece): Future[RetMsg] = {
    logger.error(" data Piece {}", data)

    import com.lqiong.base.slick1.CustomSlickProfile.api._
    val f = d
      .run(MissionTables.themes.filter(_.id === data.themeId).result)
      .flatMap(item => {
        val head = item.head
        val in = MissionTables.pieces returning MissionTables.pieces
          .map(_.id) into ((item, id) => item.copy(id = id))
        val f = d
          .run(in += data.copy(themeName = head.name))
          .map(_ => RetMsg(code = ConstantMSG.RET_OK, ""))
        f
      })
    f recover { case NonFatal(x) =>
      logger.error(" insertItem {}", x)
      RetMsg(code = ConstantMSG.RET_FAIL, x.toString)
    }
  }

  def insertMarkPieceImpl(data: Theme) = {
    import com.lqiong.base.slick1.CustomSlickProfile.api._

    val in =
      MissionTables.themes returning MissionTables.themes.map(_.id) into (
        (item, id) => item.copy(id = id)
      )
    val f = d
      .run(in += data)
      .map(item => RetMsg(code = ConstantMSG.RET_OK, item.id.toString))
    f recover { case NonFatal(x) =>
      logger.error(" insertItem {}", x)
      RetMsg(code = ConstantMSG.RET_FAIL, x.toString)
    }
  }

  def createThemeImpl(data: Theme) = {
    import com.lqiong.base.slick1.CustomSlickProfile.api._

    val in =
      MissionTables.themes returning MissionTables.themes.map(_.id) into (
        (item, id) => item.copy(id = id)
      )
    val f = d
      .run(in += data)
      .map(item => RetMsg(code = ConstantMSG.RET_OK, item.id.toString))
    f recover { case NonFatal(x) =>
      logger.error(" insertItem {}", x)
      RetMsg(code = ConstantMSG.RET_FAIL, x.toString)
    }
  }

  val p3: Route =
    AkkaHttpServerInterpreter().toRoute(
      today()
        .serverLogicSuccess(r => getTodayPieces(r))
    )

  val p4: Route =
    AkkaHttpServerInterpreter().toRoute(
      updatePiece()
        .serverLogic(input => (updateMarkPiece _).andThen(handleErrors)(input))
    )

  val p1: Route =
    AkkaHttpServerInterpreter().toRoute(
      createTheme()
        .serverLogic(input => (createThemeImpl _).andThen(handleErrors)(input))
    )

  val p2: Route =
    AkkaHttpServerInterpreter().toRoute(
      createPiece()
        .serverLogic(input => (insertPiece _).andThen(handleErrors)(input))
    )

  val p7: Route =
    AkkaHttpServerInterpreter().toRoute(
      deletePiece()
        .serverLogic(input => (deletePeciceImpl _).andThen(handleErrors)(input))
    )

  val p5: Route =
    AkkaHttpServerInterpreter().toRoute(
      themeList()
        .serverLogicSuccess(_ => getAllTheme())
    )

  override val route: Route =
    RouteConcatenation.concat(p1, p2, p3, p4, p5, p7)
  override val opiList: Iterable[AnyEndpoint] =
    List(
      createPiece(),
      deletePiece(),
      createTheme(),
      today(),
      updatePiece(),
      themeList()
    )
}

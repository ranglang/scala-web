package com.lqiong.circle.controller

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.User
import com.lqiong.a.{JsonDateTime, PunchClockRecord, PunchService}
import com.lqiong.base.common.model.RetMsg
import com.lqiong.base.common.modeltrait._
import com.lqiong.circle.{MissionTables, PgDao}
import com.lqiong.circle.model.{Piece, Theme, UpdateMarkPiece}
import com.lqiong.controller.{LqiongErrorHandler, TapirController}
import com.lqiong.sq.model.ColumnTrait
import io.circe.generic.JsonCodec
import org.joda.time.DateTime
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.{AnyEndpoint, endpoint, header, query, _}

import java.sql.Timestamp
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal



@JsonCodec
case class PunchClockReq(
                             punchId: String,
                             readAt: DateTime // matero
                           )
object  PunchClockReq extends  JsonDateTime

class PunchClockTapirController(
    override val logger: LoggingAdapter,
    implicit val ex: ExecutionContext,
  val punchService: PunchService,
) extends TapirController
    with LqiongErrorHandler
    with ColumnTrait {

  val d = PgDao.db

  private def checkPunchClock() = {
    endpoint.post
      .in("v1" / "punchclock" / "check")
      .in(jsonBody[PunchClockReq])
      .prependIn("api"/"circle")
      .out(
        jsonBody[Boolean]
      )
      .errorOut(jsonBody[RetMsg])
  }

  private def createPunchClock() = {
    endpoint.post
      .in("v1" / "punchclock" / "create")
      .in(jsonBody[PunchClockReq])
      .prependIn("api"/"circle")
      .out(
        jsonBody[RetMsg]
      )
      .errorOut(jsonBody[RetMsg])
  }

  val p4: Route =
    AkkaHttpServerInterpreter().toRoute(
      checkPunchClock()
        .serverLogicSuccess(ibput => {
          punchService.select(PunchClockRecord(
            channelId = 0,
            punchId = ibput.punchId,
            userId = User.user,
            readAt = ibput.readAt // matero
          )).map(r =>r.isDefined)
        })
    )

  val p5: Route =
    AkkaHttpServerInterpreter().toRoute(
        createPunchClock()
        .serverLogicSuccess(ibput => {
          punchService.run(PunchClockRecord(
            channelId=0,
            punchId=ibput.punchId,
            userId=User.user,
            readAt=ibput.readAt // matero
          )).map(r => RetMsg(code = ConstantMSG.RET_OK))
        })
    )

  override val route: Route =
    RouteConcatenation.concat(p5,p4)
  override val opiList: Iterable[AnyEndpoint] =
    List(
      createPunchClock(),
      checkPunchClock()
    )
}

package com.lqiong.circle.model

import com.lqiong.base.common.model.JsonDateTime
import com.lqiong.base.common.model.m1.JsonUUIDTrait
import io.circe.generic.JsonCodec
import org.joda.time.DateTime

import java.util.UUID


@JsonCodec
case class Piece(
    id: Long,
    time: DateTime,
    themeId: Long,
    themeName: String,
    content: UUID,
    index: Int,
    status: Int = 0,
    overdue: Boolean,
    title: Option[String]
//  = Option.empty
)

object Piece extends JsonUUIDTrait with JsonDateTime {
  val tupled = (this.apply _).tupled
}

@JsonCodec
case class UpdateMarkPiece(
    id: Long,
    themeId: Long,
    delta: Int
)

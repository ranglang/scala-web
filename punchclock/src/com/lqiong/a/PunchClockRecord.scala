package com.lqiong.a

import io.circe.generic.{AutoDerivation, JsonCodec}
import org.joda.time.DateTime

import java.util.UUID

@JsonCodec
case class PunchClockRecord(
  channelId: Long,
  punchId: String,
  userId: UUID,
  readAt: DateTime // matero
)

object PunchClockRecord extends AutoDerivation with JsonDateTime

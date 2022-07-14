package com.lqiong.circle.model

import com.lqiong.base.common.model.JsonDateTime
import org.joda.time.DateTime

/** mission message
  */

case class MissionMessage(id: Long, createAt: DateTime, message: String)
//  extends AutoDerivationJson

object MissionMessage extends JsonDateTime {
  val tupled = (this.apply _).tupled
}

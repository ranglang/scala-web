package com.lqiong.circle.model

import com.lqiong.base.common.model.JsonDateTime
import io.circe.generic.JsonCodec
import org.joda.time.DateTime

@JsonCodec
case class Theme(
    id: Long,
    name: String,
    isForRemember: Boolean = false,
    status: Int = 1,
    createAt: DateTime = DateTime.now
)

object Theme extends JsonDateTime {
  val tupled = (this.apply _).tupled
}

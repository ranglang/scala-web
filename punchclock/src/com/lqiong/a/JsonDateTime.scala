package com.lqiong.a

import io.circe.{Decoder, Encoder, Json}
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}

import scala.util.Try

trait JsonDateTime {
  private val parserISO: DateTimeFormatter =
    ISODateTimeFormat.dateTimeNoMillis()

  implicit val dateTimeWithoutMillisDecoder: Decoder[DateTime] =
    Decoder[String].emapTry(str => Try(parserISO.parseDateTime(str))) or Decoder[Long].emapTry(r =>
      Try(new DateTime(r))
    )

  implicit val dateTimeWithoutMillisEncoder: Encoder[DateTime] = new Encoder[DateTime] {

    override def apply(a: DateTime): Json =
      Encoder.encodeLong.apply(a.getMillis)
  }
}

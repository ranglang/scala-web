package com.lqiong.comment

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir.SchemaType.SNumber
import sttp.tapir.{Codec, DecodeResult, Schema}

import scala.util.Try

trait JodaTimeSchemaTrait {
  implicit val schemaForDateTime: Schema[DateTime] = Schema(SNumber())
}

trait HashLongJodaTime extends JodaTimeSchemaTrait {
  def encode(id: DateTime): Long = id.getMillis
  private val parserISO: DateTimeFormatter =
    ISODateTimeFormat.dateTimeNoMillis()

  def decode(s: Long): DecodeResult[DateTime] = {
    val f = Try {
      new DateTime(s)
    }
    f.toOption match {
      case Some(v) => DecodeResult.Value(v)
      case _ =>
        DecodeResult.Error("", new Exception("hash long id"))
    }
  }

  implicit val jodaCodec: Codec[String, DateTime, TextPlain] = {
    Codec.long.mapDecode(decode)(encode)
  }

}

package com.lqiong.base.common.model

import com.lqiong.base.common.model.m1.JsonUUIDTrait
import io.circe.{Decoder, Encoder, Json}
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import shapeless.{Coproduct, Generic}

import scala.util.Try

trait JsonGenic {

  implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](implicit
    gen: Generic.Aux[A, Repr],
    encodeRepr: Encoder[Repr]
  ): Encoder[A] = encodeRepr.contramap(gen.to)

  implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](implicit
    gen: Generic.Aux[A, Repr],
    decodeRepr: Decoder[Repr]
  ): Decoder[A] = decodeRepr.map(gen.from)

}
trait JsonDateTime extends JsonUUIDTrait {
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

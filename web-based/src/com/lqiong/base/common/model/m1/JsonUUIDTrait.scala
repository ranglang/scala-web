package com.lqiong.base.common.model.m1

import io.circe.Decoder.Result
import io.circe._

import java.util.UUID

trait JsonUUIDTrait {

  implicit final val encodeUUID1: Encoder[UUID] = new Encoder[UUID] {
    final def apply(a: UUID): Json = Json.fromString(a.toString.replaceAll("-", ""))
  }

  implicit final val decodeUUID1: Decoder[UUID] = new Decoder[UUID] {

    private[this] def fail(c: HCursor): Result[UUID] =
      Left(DecodingFailure("UUIDCustom", c.history))

    final def apply(c: HCursor): Result[UUID] = c.value match {
      case a: io.circe.Json
          if a.isString && a.asString.isDefined && (a.asString.get.length == 36 || a.asString.get.length == 32) => {

        if (a.asString.get.length == 32) {
          try Right(UUID.fromString(UUIDUtil.insertDashUUID(a.asString.get)))
          catch {
            case _: IllegalArgumentException => fail(c)
          }
        } else {
          try Right(UUID.fromString(a.asString.get))
          catch {
            case _: IllegalArgumentException => fail(c)
          }
        }
      }
      case _ => fail(c)
    }
  }

}

object JsonUUIDTrait extends JsonUUIDTrait

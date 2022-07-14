package com.lqiong.base.common.modeltrait

import enumeratum.values._
import sttp.tapir.codec.enumeratum.TapirCodecEnumeratum

trait TapirStringEnumEntry extends StringEnumEntry with TapirCodecEnumeratum
trait TapirIntEnumEntry extends IntEnumEntry with TapirCodecEnumeratum


sealed abstract class ConstantMSG(val value: Int, val name: String)
    extends TapirIntEnumEntry

object ConstantMSG extends IntEnum[ConstantMSG] with IntCirceEnum[ConstantMSG] {
  val values = findValues
  case object RET_FAIL extends ConstantMSG(value = 3000, name = "RET_OK")
  case object RET_OK extends ConstantMSG(value = 2001, name = "RET_OK")

  case object RET_LOGIN_FAIL extends ConstantMSG(value = 1001, name = "RET_LOGIN_FAIL")
  case object RET_REG_FAIL extends ConstantMSG(value = 3001, name = "RET_REG_FAIL")
  case object RET_AUTH_FAIL extends ConstantMSG(value = 4000, name = "RET_AUTH_FAIL")
  case object RET_FORM_ERROR extends ConstantMSG(value = 6001, name = "RET_FORM_ERROR")

  case object RET_FREQUENCE extends ConstantMSG(value = 8001, name = "RET_AUTH_FAIL")
  case object RET_TIME_OUT extends ConstantMSG(value = 5001, name = "RET_TIME_OUT")
}

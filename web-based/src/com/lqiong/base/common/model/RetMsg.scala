
package  com.lqiong.base.common.model

import com.lqiong.base.common.modeltrait.ConstantMSG
import io.circe.generic.JsonCodec

@JsonCodec
case class RetMsg(code: ConstantMSG, msg: String = "")

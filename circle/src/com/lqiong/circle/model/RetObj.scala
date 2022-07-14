package com.lqiong.circle.model

import io.circe.generic.JsonCodec

@JsonCodec
case class RetObj(code: Int, arr: String)

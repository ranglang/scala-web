package com.lqiong.base.common.model.m1
import java.util.UUID

object UUIDUtil {

  def getUUIDTxt(uuid: UUID): String = {
    uuid.toString.replaceAll("-", "")
  }
  def insertDashUUID(uuid: String): String = {
    var sb = new StringBuilder(uuid)
    sb.insert(8, "-")
    sb = new StringBuilder(sb.toString)
    sb.insert(13, "-")
    sb = new StringBuilder(sb.toString)
    sb.insert(18, "-")
    sb = new StringBuilder(sb.toString)
    sb.insert(23, "-")
    sb.toString
  }
}

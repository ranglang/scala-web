package com.lqiong.circle.controller

import com.lqiong.base.common.modeltrait.ConstantMSG
import io.circe.generic.JsonCodec

@JsonCodec
case class NoSqlPagingListData[T](
    val code: ConstantMSG,
    val msg: String,
    val records: List[T] = List.empty[T],
    val pageSize: Int = 0,
    val pageState: Option[String] = Option.empty[String]
)

trait NoSqlPagingClass {
  val pageState: Option[String]
  val pageSize: Option[Int] = Option.empty[Int]
}

object NoSqlPagingClass {

  def getEmpty[T](x: Throwable): NoSqlPagingListData[T] = {
    new NoSqlPagingListData[T](code = ConstantMSG.RET_FAIL, msg = x.toString)
  }

  def okWithPageState[T](
      x: List[T],
      pageState: Option[String] = Option.empty[String],
      pageSize: Int = 5
  ): NoSqlPagingListData[T] = {
    new NoSqlPagingListData[T](
      code = ConstantMSG.RET_OK,
      msg = "",
      records = x,
      pageState = pageState,
      pageSize = pageSize
    )
  }

  def okList[T](x: List[T]): NoSqlPagingListData[T] = {
    new NoSqlPagingListData[T](code = ConstantMSG.RET_OK, msg = "", records = x)
  }
}

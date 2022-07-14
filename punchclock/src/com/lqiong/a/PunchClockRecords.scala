package com.lqiong.a

import com.outworkers.phantom.dsl.{ClusteringOrder, PartitionKey, Row, _}

import scala.concurrent.Future

abstract class PunchClockRecords extends Table[ConcretePunchClockRecord, PunchClockRecord] {

  object channelId extends LongColumn with PartitionKey

  object punchId extends StringColumn with PartitionKey

  object userId extends UUIDColumn with PartitionKey

  object readAt extends DateTimeColumn with PrimaryKey

  override def fromRow(row: Row): PunchClockRecord = {
    PunchClockRecord(
      channelId = channelId(row),
      punchId = punchId(row),
      userId = userId(row),
      readAt = readAt(row)
    )
  }
}

abstract class ConcretePunchClockRecord extends PunchClockRecords with RootConnector {

  def storeItem(data: PunchClockRecord) = {
    insert
      .value(_.channelId, data.channelId)
      .value(_.punchId, data.punchId)
      .value(_.userId, data.userId)
      .value(_.readAt, data.readAt)
      .future()
  }

  def checkIf(data: PunchClockRecord): Future[Option[PunchClockRecord]] = {
    select
      .where(_.channelId eqs data.channelId)
      .and(_.punchId eqs data.punchId)
      .and(_.userId eqs data.userId)
      .and(_.readAt eqs data.readAt)
      .one()
  }

}

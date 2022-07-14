package com.lqiong.a



import a.PunchClockDatabase

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.concurrent.duration.DurationInt

class PunchServiceCreate(
                    val a: PunchClockDatabase,
                    dBconfig: DBconfig
                    //  implicit val ex1: ExecutionContextExecutor
                  ) extends DBconfig.tribe4Connector.Connector {

  def run(store: PunchClockRecord) = {
    a.kunOffer$.storeItem(store)
  }

  def createTable() = {
    import com.outworkers.phantom.dsl.{ClusteringOrder, PartitionKey, Row, _}
    a.kunOffer$.createSchema(50.seconds)
  }
}

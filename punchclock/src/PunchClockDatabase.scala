package a

import com.lqiong.a.{ConcretePunchClockRecord, PunchClockRecord}
import com.outworkers.phantom.builder.query.CreateQuery
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

import scala.concurrent.duration._

class PunchClockDatabase(override val connector: CassandraConnection)
    extends Database[PunchClockDatabase](connector) {

  object kunOffer$ extends ConcretePunchClockRecord with connector.Connector {
    override def autocreate(
      space: KeySpace
    ): CreateQuery.Default[ConcretePunchClockRecord, PunchClockRecord] = {
      create
        .ifNotExists()(space)
        .and(gc_grace_seconds eqs 10.seconds)
        .and(read_repair_chance eqs 0.2)
    }
  }

}

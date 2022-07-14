package com.lqiong.a

import com.datastax.driver.core.PlainTextAuthProvider
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.typesafe.config.{Config, ConfigFactory}

class DBconfig(val config: Config) {

  val dbcLink: String = config.getString("dbc.host")
  val dbcPort: Int = config.getString("dbc.port").toInt
  val userName: String = config.getString("dbc.userName")
  val password: String = config.getString("dbc.password")

  val tribe4Connector: CassandraConnection = ContactPoints(Seq(dbcLink), dbcPort)
    .withClusterBuilder(
      _.withAuthProvider(new PlainTextAuthProvider(userName, password))
    )
    .keySpace(config.getString("dbc.keySpace"))
}

object DBconfig {
  val config = ConfigFactory.load()

  val dbcLink: String = config.getString("dbc.host")
  val dbcPort: Int = config.getString("dbc.port").toInt
  val userName: String = config.getString("dbc.userName")
  val password: String = config.getString("dbc.password")

  val tribe4Connector: CassandraConnection = ContactPoints(Seq(dbcLink), dbcPort)
    .withClusterBuilder(
      _.withAuthProvider(new PlainTextAuthProvider(userName, password))
    )
    .keySpace(config.getString("dbc.keySpace"))
}

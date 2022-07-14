package com.lqiong.circle

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.JdbcBackend.Database

/** Created by biancapetracca on 1/5/18.
  */
class DB(db: String) {

  val config: Config = ConfigFactory.load()
  val connection: Database = Database.forConfig("mydb")
}

object PgDao {

//  def setupSimpleSchema(db: DB): Database = {
//    db.connection
//  }

//  private val db = setupSimpleSchema(new DB("mariadb"))

  lazy final val db: slick.jdbc.JdbcBackend.Database =
    Database.forConfig("mydb")

//  final  def getDb(): slick.jdbc.JdbcBackend.Database = {
//    this.db
//  }
}

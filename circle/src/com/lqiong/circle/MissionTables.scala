package com.lqiong.circle

import com.github.tototoshi.slick.MySQLJodaSupport._
import com.lqiong.circle.model.{MissionMessage, Piece, Theme}
import com.lqiong.sq.model.ColumnTrait
import org.joda.time.DateTime
import com.lqiong.base.slick1.CustomSlickProfile.api._

import java.util.UUID

object MissionTables extends ColumnTrait {

  class Themes(tag: Tag) extends Table[Theme](tag, "circle_theme") {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey, O.Length(40))

    def name = column[String]("name", O.Unique)

    def isForRemember = column[Boolean]("isForRemember")

    def status = column[Int]("status")

    def createAt = column[DateTime]("createAt")

    def lastTime = column[DateTime]("lastTime")

    override def * =
      (
        id,
        name,
        isForRemember,
        status,
        createAt
      ) <> (Theme.tupled, Theme.unapply)
  }

  class Pieces(tag: Tag)
      extends Table[Piece](tag, "circlepiece4")
      with ColumnTrait {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def time = column[DateTime]("time")
    def themeId = column[Long]("themeId")
    def themeName = column[String]("themeName")
    def content = column[UUID]("content")

    def index = column[Int]("index")
    def status = column[Int]("status")
    def title = column[String]("title")
    def overdue = column[Boolean]("overdue")

    override def * =
      (
        id,
        time,
        themeId,
        themeName,
        content,
        index,
        status,
        overdue,
        title.?
      ) <> (Piece.tupled, Piece.unapply)
  }

  class MissionMessages(tag: Tag)
      extends Table[MissionMessage](tag, "message")
      with ColumnTrait {
    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def createAt = column[DateTime]("createAt")
    def message = column[String]("message")

    override def * =
      (id, createAt, message) <> (MissionMessage.tupled, MissionMessage.unapply)
  }

  val themes = TableQuery[Themes]
  val pieces = TableQuery[Pieces]
//  val missionMessage = TableQuery[MissionMessages]

}

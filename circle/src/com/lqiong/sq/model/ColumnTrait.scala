package com.lqiong.sq.model

import com.lqiong.base.slick1.CustomSlickProfile.api._
import com.lqiong.sq.checklist.models.ConstantFormType
import slick.ast.BaseTypedType
import slick.jdbc.H2Profile.MappedColumnType
import slick.jdbc.JdbcType

trait ColumnTrait {

  implicit val checkFormType: JdbcType[ConstantFormType#Value]
    with BaseTypedType[ConstantFormType#Value] =
    MappedColumnType.base[ConstantFormType#Value, String](
      s => s.toString,
      string => ConstantFormType.withName(string)
    )

}

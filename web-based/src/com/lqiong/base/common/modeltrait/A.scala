package com.lqiong.base.common.modeltrait

import com.lqiong.base.common.model.{JsonDateTime, JsonGenic}
import com.lqiong.search.controller.JsonCircleTraitAutoDerivation

trait BaseJsonSupport extends JsonCircleTraitAutoDerivation with JsonGenic

trait BaseItemJsonSupport extends JsonCircleTraitAutoDerivation with JsonGenic with JsonDateTime


package com.lqiong.search.controller

import de.heikoseeberger.akkahttpcirce.{BaseCirceSupport, FailFastCirceSupport}

trait JsonCircleTraitAutoDerivation extends FailFastCirceSupport with BaseCirceSupport

object JsonCircleTraitAutoDerivation extends JsonCircleTraitAutoDerivation
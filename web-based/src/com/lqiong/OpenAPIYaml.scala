package com.lqiong

import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.lqiong.controller.TapirController
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.Info
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import sttp.tapir.swagger.SwaggerUI

import scala.concurrent.Future

object OpenAPIYaml extends RouteConcatenation {

  def getList(li1st: List[TapirController]) = {
    val a = li1st.flatMap(r => r.opiList)
    val openAPIYaml: String = OpenAPIDocsInterpreter().toOpenAPI(a, Info("title", "1.0")).toYaml
    val list: List[Route] = List(
      AkkaHttpServerInterpreter().toRoute(SwaggerUI[Future](openAPIYaml))
    ) ::: li1st.map(r => r.route)
    list.reduce((a, b) => a ~ b)
  }

}

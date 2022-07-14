package com.lqiong.controller

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Route
import com.lqiong.base.common.model.RetMsg
import com.lqiong.base.common.modeltrait.{BaseJsonSupport, ConstantMSG}
import com.lqiong.comment.JodaTimeSchemaTrait
import sttp.model.{Header, HeaderNames, StatusCode}
import sttp.tapir.Codec.JsonCodec
import sttp.tapir.generic.SchemaDerivation
import sttp.tapir.json.circe.TapirJsonCirce
import sttp.tapir.server.akkahttp.AkkaHttpServerOptions
import sttp.tapir.server.interceptor.DecodeFailureContext
import sttp.tapir.server.interceptor.decodefailure.DefaultDecodeFailureHandler
import sttp.tapir.server.interceptor.decodefailure.DefaultDecodeFailureHandler.FailureMessages
import sttp.tapir.{AnyEndpoint, DecodeResult, EndpointIO, EndpointInput, customJsonBody, server}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

trait LqiongErrorHandler {
  implicit val ex: ExecutionContext
  def handleErrors[T](f: Future[T]): Future[Either[RetMsg, T]] =
    f.transform {
      case Success(v) => Success(Right(v))
      case Failure(e: Throwable) =>
        println(s"Exception when running endpoint logic: $e")
        Success(Left(RetMsg(code = ConstantMSG.RET_FAIL, "server error")))
    }
}

trait TapirController
    extends BaseJsonSupport
    with SchemaDerivation
    with JodaTimeSchemaTrait

    with TapirJsonCirce {
  val route: Route
  val opiList: Iterable[AnyEndpoint]

  val logger: LoggingAdapter

  //  > get msg

  //  {"from_user_id":"2c11a460b04011e9be2d055852da15dd","msg":"dd","channel_id":"e1038ea0bcd711ecaf288fea73d4cf22"}

  def onlyStatus(status: StatusCode): (StatusCode, List[Header]) = {
    println(s"onlyStatus ${status}");
    logger.info(s"onlyStatus ${status}");
    (status, Nil)
  }

  def failingInput(ctx: DecodeFailureContext) = {
    import sttp.tapir.internal.RichEndpointInput
    ctx.failure match {
      case DecodeResult.Missing =>
        def missingAuth(i: EndpointInput[_]) =
          i.pathTo(ctx.failingInput).collectFirst { case a: EndpointInput.Auth[_, _] =>
            a
          }
        missingAuth(ctx.endpoint.securityInput)
          .orElse(missingAuth(ctx.endpoint.input))
          .getOrElse(ctx.failingInput)
      case _ => ctx.failingInput
    }
  }

  def d(
    ctx: DecodeFailureContext,
    badRequestOnPathErrorIfPathShapeMatches: Boolean,
    badRequestOnPathInvalidIfPathShapeMatches: Boolean
  ): Option[(StatusCode, List[Header])] = {
    val a = failingInput(ctx)
    println(s"a.getClass.getName ${a.getClass.getName}")
    println(s"a.getClass.getName ${a.getClass.getName}")
    logger.info(s"a.getClass.getName ${a.getClass.getName}")
    println(a.getClass.getName)
    println(a.getClass.getName)
    failingInput(ctx) match {
      case _: EndpointInput.Query[_]       => Some(onlyStatus(StatusCode.Unauthorized))
      case _: EndpointInput.QueryParams[_] => Some(onlyStatus(StatusCode.Unauthorized))
      case _: EndpointInput.Cookie[_]      => Some(onlyStatus(StatusCode.Unauthorized))
      case h: EndpointIO.Header[_]
          if ctx.failure.isInstanceOf[DecodeResult.Mismatch] && h.name == HeaderNames.ContentType =>
        Some(onlyStatus(StatusCode.UnsupportedMediaType))
      case _: EndpointIO.Header[_] => Some(onlyStatus(StatusCode.Unauthorized))
      case fh: EndpointIO.FixedHeader[_]
          if ctx.failure
            .isInstanceOf[DecodeResult.Mismatch] && fh.h.name == HeaderNames.ContentType =>
        Some(onlyStatus(StatusCode.UnsupportedMediaType))
      case _: EndpointIO.FixedHeader[_] => Some(onlyStatus(StatusCode.Unauthorized))
      case _: EndpointIO.Headers[_]     => Some(onlyStatus(StatusCode.Unauthorized))
      case _: EndpointIO.Body[_, _] => {
        println("xxxxxxxxxxxxxxxxxxxxxxx body")
        Some(onlyStatus(StatusCode.Unauthorized))
      }
      case _: EndpointIO.OneOfBody[_, _] if ctx.failure.isInstanceOf[DecodeResult.Mismatch] =>
        Some(onlyStatus(StatusCode.UnsupportedMediaType))
      case _: EndpointIO.StreamBodyWrapper[_, _] => Some(onlyStatus(StatusCode.Unauthorized))
      // we assume that the only decode failure that might happen during path segment decoding is an error
      // a non-standard path decoder might return Missing/Multiple/Mismatch, but that would be indistinguishable from
      // a path shape mismatch
      case _: EndpointInput.PathCapture[_]
          if (badRequestOnPathErrorIfPathShapeMatches && ctx.failure
            .isInstanceOf[DecodeResult.Error]) ||
            (badRequestOnPathInvalidIfPathShapeMatches && ctx.failure
              .isInstanceOf[DecodeResult.InvalidValue]) =>
        Some(onlyStatus(StatusCode.Unauthorized))
      case a: EndpointInput.Auth[_, _] =>
        Some((StatusCode.Ok, Header.wwwAuthenticate(a.challenge)))
//        Some(server.model.ValuedEndpointOutput(stringBody, "Incorrect format!!!"))
      // other basic endpoints - the request doesn't match, but not returning a response (trying other endpoints)
      case _: EndpointInput.Basic[_] => None
      // all other inputs (tuples, mapped) - responding with bad request
      case _ => Some(onlyStatus(StatusCode.Unauthorized))
    }
  }

  //  val myServerOptions: CustomInterceptors[Future, AkkaHttpServerOptions] =
//    AkkaHttpServerOptions.customInterceptors.decodeFailureHandler(
  val a = DefaultDecodeFailureHandler(
    d(_, false, false),
    FailureMessages.failureMessage,
    DefaultDecodeFailureHandler.failureResponse
  )

  val a1: JsonCodec[RetMsg] = circeCodec[RetMsg]

  implicit val customServerOptions: AkkaHttpServerOptions = AkkaHttpServerOptions.customInterceptors
    .decodeFailureHandler(ctx => {
      ctx.failingInput match {
        // when defining how a decode failure should be handled, we need to describe the output to be used, and
        // a value for this output
        case EndpointInput.Query(_, _, _) =>
//          Some(server.model.ValuedEndpointOutput(stringBody, "Incorrect format!!!"))
          Some(
            server.model.ValuedEndpointOutput(
              customJsonBody(a1),
              RetMsg(code = ConstantMSG.RET_FAIL, "query error")
            )
          )
        case _: EndpointInput.Auth[_, _] =>
          Some(
            server.model
              .ValuedEndpointOutput(customJsonBody(a1), RetMsg(code = ConstantMSG.RET_AUTH_FAIL))
          )
        case _: EndpointIO.Body[_, _] =>
          Some(
            server.model.ValuedEndpointOutput(
              customJsonBody(a1),
              RetMsg(code = ConstantMSG.RET_FAIL, "body error")
            )
          )
        case _: EndpointInput.Query[_] =>
          Some(
            server.model.ValuedEndpointOutput(
              customJsonBody(a1),
              RetMsg(code = ConstantMSG.RET_FAIL, "body query 111")
            )
          )
        case _ => {
          logger.info("xxxxxxxxxxxxxxxxxx {}", a)
          print(s"aaa ${ctx.failingInput}")
          a(ctx)
        } // DefaultDecodeFailureHandler.default(ctx)
      }
    })
    .options
}

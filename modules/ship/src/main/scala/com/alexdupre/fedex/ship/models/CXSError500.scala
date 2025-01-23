package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user accounts.
  * This includes code, message and parameter
  *
  * @param code
  *   Indicates the error code.<br>Example: INTERNAL.SERVER.ERROR
  * @param parameterList
  *   Specifies list of parameters.
  * @param message
  *   Indicates the description of API error alert message.<br>Example: We encountered an unexpected error and are working to resolve the
  *   issue. We apologize for any inconvenience. Please check back at a later time.
  */
case class CXSError500(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[io.circe.Json] = None
)

object CXSError500 {

  given Encoder[CXSError500] = new Encoder.AsObject[CXSError500] {
    final def encodeObject(o: CXSError500): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError500] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[io.circe.Json]]
    } yield CXSError500(code, parameterList, message)
  }
}

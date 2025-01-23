package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user accounts.
  * This includes code, message and parameter
  *
  * @param code
  *   Indicates the error code.<br>Example: NOT.FOUND.ERROR
  * @param parameterList
  *   Specifies list of parameters.
  * @param message
  *   Indicates the description of API error alert message.<br>Example: The resource you requested is no longer available. Please modify
  *   your request and try again.
  */
case class CXSError404(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[io.circe.Json] = None
)

object CXSError404 {

  given Encoder[CXSError404] = new Encoder.AsObject[CXSError404] {
    final def encodeObject(o: CXSError404): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError404] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[io.circe.Json]]
    } yield CXSError404(code, parameterList, message)
  }
}

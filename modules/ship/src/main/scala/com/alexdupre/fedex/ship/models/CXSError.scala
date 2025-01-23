package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user accounts.
  * This includes code, message and parameter
  *
  * @param code
  *   Indicates the error code.<br>Example: SHIPMENT.USER.UNAUTHORIZED
  * @param message
  *   Indicates the description of API error alert message.<br>Example: Requested user is not authorized to perform the operation.
  */
case class CXSError(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[String] = None
)

object CXSError {

  given Encoder[CXSError] = new Encoder.AsObject[CXSError] {
    final def encodeObject(o: CXSError): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[String]]
    } yield CXSError(code, parameterList, message)
  }
}

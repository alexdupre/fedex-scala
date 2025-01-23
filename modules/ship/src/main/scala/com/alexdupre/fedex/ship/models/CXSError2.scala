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
case class CXSError2(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[String] = None
)

object CXSError2 {

  given Encoder[CXSError2] = new Encoder.AsObject[CXSError2] {
    final def encodeObject(o: CXSError2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError2] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[String]]
    } yield CXSError2(code, parameterList, message)
  }
}

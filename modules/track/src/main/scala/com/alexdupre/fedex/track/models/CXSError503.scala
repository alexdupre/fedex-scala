package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user accounts.
  * This includes code, message and parameter
  *
  * @param code
  *   Indicates the error code.<br>Example: SERVICE.UNAVAILABLE.ERROR
  * @param parameterList
  *   List of parameters.
  * @param message
  *   Indicates the description of API error alert message.<br>Example: The service is currently unavailable and we are working to resolve
  *   the issue. We apologize for any inconvenience. Please check back at a later time.
  */
case class CXSError503(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[io.circe.Json] = None
)

object CXSError503 {

  given Encoder[CXSError503] = new Encoder.AsObject[CXSError503] {
    final def encodeObject(o: CXSError503): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError503] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[io.circe.Json]]
    } yield CXSError503(code, parameterList, message)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user accounts.
  * This includes code, message and parameter
  *
  * @param code
  *   Indicates the error code.<br>Example: NOT.AUTHORIZED.ERROR
  * @param parameterList
  *   List of parameters.
  * @param message
  *   Indicates the description of API error alert message.<br>Example: Access token expired. Please modify your request and try again.
  */
case class CXSError401(
    code: Option[String] = None,
    parameterList: Option[Seq[Parameter]] = None,
    message: Option[io.circe.Json] = None
)

object CXSError401 {

  given Encoder[CXSError401] = new Encoder.AsObject[CXSError401] {
    final def encodeObject(o: CXSError401): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"          -> o.code.asJson,
          "parameterList" -> o.parameterList.asJson,
          "message"       -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CXSError401] = (c: HCursor) => {
    for {
      code          <- c.downField("code").as[Option[String]]
      parameterList <- c.downField("parameterList").as[Option[Seq[Parameter]]]
      message       <- c.downField("message").as[Option[io.circe.Json]]
    } yield CXSError401(code, parameterList, message)
  }
}

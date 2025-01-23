package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Contains error details.
  *
  * @param code
  *   Error Code. <br> Example: TRACKING.TRACKINGNUMBER.EMPTY
  * @param parameterList
  *   List of parameters.
  * @param message
  *   Error Message. <br>Example: Please provide tracking number.
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

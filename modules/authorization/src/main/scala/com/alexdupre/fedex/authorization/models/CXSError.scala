package com.alexdupre.fedex.authorization.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** CXSError Model
  *
  * @param code
  *   Indicates the error code.<br>Example: NOT.FOUND.ERROR
  * @param parameterList
  *   List of parameters which indicates the properties of the alert message.
  * @param message
  *   Indicates the API error alert message.<br>Example: We are unable to process this request. Please try again later or contact FedEx
  *   Customer Service.
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

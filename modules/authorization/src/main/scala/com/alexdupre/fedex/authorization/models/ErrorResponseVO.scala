package com.alexdupre.fedex.authorization.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: bc95c0e4-b33e-42a2-80d2-334282b5d37a
  * @param errors
  *   Indicates error details when suspicious files, potential exploits and viruses are found while scanning files, directories and user
  *   accounts. This includes code, message and error parameters.
  */
case class ErrorResponseVO(
    transactionId: Option[String] = None,
    errors: Option[Seq[CXSError]] = None
)

object ErrorResponseVO {

  given Encoder[ErrorResponseVO] = new Encoder.AsObject[ErrorResponseVO] {
    final def encodeObject(o: ErrorResponseVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId" -> o.transactionId.asJson,
          "errors"        -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO] = (c: HCursor) => {
    for {
      transactionId <- c.downField("transactionId").as[Option[String]]
      errors        <- c.downField("errors").as[Option[Seq[CXSError]]]
    } yield ErrorResponseVO(transactionId, errors)
  }
}

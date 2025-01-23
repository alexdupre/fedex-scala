package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO404 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  */
case class ErrorResponseVO404(
    transactionId: Option[String] = None,
    errors: Option[Seq[CXSError404]] = None
)

object ErrorResponseVO404 {

  given Encoder[ErrorResponseVO404] = new Encoder.AsObject[ErrorResponseVO404] {
    final def encodeObject(o: ErrorResponseVO404): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId" -> o.transactionId.asJson,
          "errors"        -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO404] = (c: HCursor) => {
    for {
      transactionId <- c.downField("transactionId").as[Option[String]]
      errors        <- c.downField("errors").as[Option[Seq[CXSError404]]]
    } yield ErrorResponseVO404(transactionId, errors)
  }
}

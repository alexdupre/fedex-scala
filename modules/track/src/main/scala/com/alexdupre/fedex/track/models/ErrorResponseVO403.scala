package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO403 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  */
case class ErrorResponseVO403(
    transactionId: Option[String] = None,
    errors: Option[Seq[CXSError403]] = None
)

object ErrorResponseVO403 {

  given Encoder[ErrorResponseVO403] = new Encoder.AsObject[ErrorResponseVO403] {
    final def encodeObject(o: ErrorResponseVO403): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId" -> o.transactionId.asJson,
          "errors"        -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO403] = (c: HCursor) => {
    for {
      transactionId <- c.downField("transactionId").as[Option[String]]
      errors        <- c.downField("errors").as[Option[Seq[CXSError403]]]
    } yield ErrorResponseVO403(transactionId, errors)
  }
}

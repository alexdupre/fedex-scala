package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO404_2 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  */
case class ErrorResponseVO404_2(
    transactionId: Option[String] = None,
    errors: Option[Seq[CXSError404]] = None
)

object ErrorResponseVO404_2 {

  given Encoder[ErrorResponseVO404_2] = new Encoder.AsObject[ErrorResponseVO404_2] {
    final def encodeObject(o: ErrorResponseVO404_2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId" -> o.transactionId.asJson,
          "errors"        -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO404_2] = (c: HCursor) => {
    for {
      transactionId <- c.downField("transactionId").as[Option[String]]
      errors        <- c.downField("errors").as[Option[Seq[CXSError404]]]
    } yield ErrorResponseVO404_2(transactionId, errors)
  }
}

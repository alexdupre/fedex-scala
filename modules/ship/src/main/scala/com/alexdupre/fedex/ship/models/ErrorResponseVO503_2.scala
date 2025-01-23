package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO503_2 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  */
case class ErrorResponseVO503_2(
    transactionId: Option[String] = None,
    errors: Option[Seq[CXSError503]] = None
)

object ErrorResponseVO503_2 {

  given Encoder[ErrorResponseVO503_2] = new Encoder.AsObject[ErrorResponseVO503_2] {
    final def encodeObject(o: ErrorResponseVO503_2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId" -> o.transactionId.asJson,
          "errors"        -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO503_2] = (c: HCursor) => {
    for {
      transactionId <- c.downField("transactionId").as[Option[String]]
      errors        <- c.downField("errors").as[Option[Seq[CXSError503]]]
    } yield ErrorResponseVO503_2(transactionId, errors)
  }
}

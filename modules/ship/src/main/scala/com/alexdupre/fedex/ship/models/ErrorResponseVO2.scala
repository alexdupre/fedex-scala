package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO_2 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
  *   match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class ErrorResponseVO2(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    errors: Option[Seq[CXSError2]] = None
)

object ErrorResponseVO2 {

  given Encoder[ErrorResponseVO2] = new Encoder.AsObject[ErrorResponseVO2] {
    final def encodeObject(o: ErrorResponseVO2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "errors"                -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO2] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      errors                <- c.downField("errors").as[Option[Seq[CXSError2]]]
    } yield ErrorResponseVO2(transactionId, customerTransactionId, errors)
  }
}

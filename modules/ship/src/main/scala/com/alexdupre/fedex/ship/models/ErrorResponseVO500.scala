package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ErrorResponseVO500 Model
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
  *   match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class ErrorResponseVO500(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    errors: Option[Seq[CXSError500]] = None
)

object ErrorResponseVO500 {

  given Encoder[ErrorResponseVO500] = new Encoder.AsObject[ErrorResponseVO500] {
    final def encodeObject(o: ErrorResponseVO500): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "errors"                -> o.errors.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ErrorResponseVO500] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      errors                <- c.downField("errors").as[Option[Seq[CXSError500]]]
    } yield ErrorResponseVO500(transactionId, customerTransactionId, errors)
  }
}

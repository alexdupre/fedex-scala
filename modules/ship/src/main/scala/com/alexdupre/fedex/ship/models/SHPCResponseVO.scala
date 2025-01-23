package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Wrapper class for CancelTagOuputVO. It holds transactionId and output.
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
  *   match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class SHPCResponseVO(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    output: Option[BaseProcessOutputVO] = None
)

object SHPCResponseVO {

  given Encoder[SHPCResponseVO] = new Encoder.AsObject[SHPCResponseVO] {
    final def encodeObject(o: SHPCResponseVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "output"                -> o.output.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SHPCResponseVO] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      output                <- c.downField("output").as[Option[BaseProcessOutputVO]]
    } yield SHPCResponseVO(transactionId, customerTransactionId, output)
  }
}

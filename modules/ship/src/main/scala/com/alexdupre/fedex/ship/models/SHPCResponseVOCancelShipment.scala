package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is a wrapper class for outputVO
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
  *   match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class SHPCResponseVOCancelShipment(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    output: Option[BaseProcessOutputVOCancelShipment] = None
)

object SHPCResponseVOCancelShipment {

  given Encoder[SHPCResponseVOCancelShipment] = new Encoder.AsObject[SHPCResponseVOCancelShipment] {
    final def encodeObject(o: SHPCResponseVOCancelShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "output"                -> o.output.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SHPCResponseVOCancelShipment] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      output                <- c.downField("output").as[Option[BaseProcessOutputVOCancelShipment]]
    } yield SHPCResponseVOCancelShipment(transactionId, customerTransactionId, output)
  }
}

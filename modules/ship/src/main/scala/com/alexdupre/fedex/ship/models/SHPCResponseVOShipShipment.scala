package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Wrapper class for ShipShipmentOutputVO. It holds transactionId and output.
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element has a unique identifier added in your request, helps you match the request to the reply.<br>Example: XXXX_XXX123XXXXX
  */
case class SHPCResponseVOShipShipment(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    output: Option[BaseProcessOutputVOShipShipment] = None
)

object SHPCResponseVOShipShipment {

  given Encoder[SHPCResponseVOShipShipment] = new Encoder.AsObject[SHPCResponseVOShipShipment] {
    final def encodeObject(o: SHPCResponseVOShipShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "output"                -> o.output.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SHPCResponseVOShipShipment] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      output                <- c.downField("output").as[Option[BaseProcessOutputVOShipShipment]]
    } yield SHPCResponseVOShipShipment(transactionId, customerTransactionId, output)
  }
}

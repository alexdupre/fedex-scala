package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Wrapper class for GetOpenshipmentResultsOutputVo. It holds transactionId and output.
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624xxxxx-b709-470c-8c39-4b55112xxxxx
  * @param customerTransactionId
  *   This is a unique identifier to your transaction and helps you match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class SHPCResponseVOGetOpenShipmentResults(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    output: Option[BaseProcessOutputVOGetOpenShipmentResults] = None
)

object SHPCResponseVOGetOpenShipmentResults {

  given Encoder[SHPCResponseVOGetOpenShipmentResults] = new Encoder.AsObject[SHPCResponseVOGetOpenShipmentResults] {
    final def encodeObject(o: SHPCResponseVOGetOpenShipmentResults): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "output"                -> o.output.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SHPCResponseVOGetOpenShipmentResults] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      output                <- c.downField("output").as[Option[BaseProcessOutputVOGetOpenShipmentResults]]
    } yield SHPCResponseVOGetOpenShipmentResults(transactionId, customerTransactionId, output)
  }
}

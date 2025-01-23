package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Wrapper class for ShipShipmentOutputVO. It holds transactionId and output.
  *
  * @param transactionId
  *   The transaction ID is a special set of numbers that defines each transaction.<br>Example: 624deea6-b709-470c-8c39-4b5511281492
  * @param customerTransactionId
  *   This element allows you to assign a unique identifier to your transaction. This element is also returned in the reply and helps you
  *   match the request to the reply. <br> Example: AnyCo_order123456789
  */
case class SHPCResponseVOCreateTag(
    transactionId: Option[String] = None,
    customerTransactionId: Option[String] = None,
    output: Option[BaseProcessOutputVOCreateTag] = None
)

object SHPCResponseVOCreateTag {

  given Encoder[SHPCResponseVOCreateTag] = new Encoder.AsObject[SHPCResponseVOCreateTag] {
    final def encodeObject(o: SHPCResponseVOCreateTag): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionId"         -> o.transactionId.asJson,
          "customerTransactionId" -> o.customerTransactionId.asJson,
          "output"                -> o.output.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SHPCResponseVOCreateTag] = (c: HCursor) => {
    for {
      transactionId         <- c.downField("transactionId").as[Option[String]]
      customerTransactionId <- c.downField("customerTransactionId").as[Option[String]]
      output                <- c.downField("output").as[Option[BaseProcessOutputVOCreateTag]]
    } yield SHPCResponseVOCreateTag(transactionId, customerTransactionId, output)
  }
}

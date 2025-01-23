package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements required to create a shipment. */
case class FullSchemaVerifyShipment(
    requestedShipment: RequestedShipmentVerify,
    accountNumber: Option[ShipperAccountNumber] = None
)

object FullSchemaVerifyShipment {

  given Encoder[FullSchemaVerifyShipment] = new Encoder.AsObject[FullSchemaVerifyShipment] {
    final def encodeObject(o: FullSchemaVerifyShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "requestedShipment" -> o.requestedShipment.asJson,
          "accountNumber"     -> o.accountNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaVerifyShipment] = (c: HCursor) => {
    for {
      requestedShipment <- c.downField("requestedShipment").as[RequestedShipmentVerify]
      accountNumber     <- c.downField("accountNumber").as[Option[ShipperAccountNumber]]
    } yield FullSchemaVerifyShipment(requestedShipment, accountNumber)
  }
}

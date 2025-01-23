package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Full_Schema_Create_Tag Model
  *
  * @param requestedShipment
  *   The shipment data describing the shipment being tendered to FedEx.
  * @param accountNumber
  *   The specific FedEx customer account number (account value) associated with the shipment.
  */
case class FullSchemaCreateTag(
    requestedShipment: CreateTagRequestedShipment,
    accountNumber: AccountNumber
)

object FullSchemaCreateTag {

  given Encoder[FullSchemaCreateTag] = new Encoder.AsObject[FullSchemaCreateTag] {
    final def encodeObject(o: FullSchemaCreateTag): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "requestedShipment" -> o.requestedShipment.asJson,
          "accountNumber"     -> o.accountNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaCreateTag] = (c: HCursor) => {
    for {
      requestedShipment <- c.downField("requestedShipment").as[CreateTagRequestedShipment]
      accountNumber     <- c.downField("accountNumber").as[AccountNumber]
    } yield FullSchemaCreateTag(requestedShipment, accountNumber)
  }
}

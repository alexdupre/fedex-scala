package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the Shipper contact details for this shipment.
  *
  * @param tins
  *   This is the tax identification number details.
  */
case class ShipperParty(
    address: PartyAddress2,
    contact: PartyContact,
    tins: Option[Seq[TaxpayerIdentification]] = None
)

object ShipperParty {

  given Encoder[ShipperParty] = new Encoder.AsObject[ShipperParty] {
    final def encodeObject(o: ShipperParty): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address" -> o.address.asJson,
          "contact" -> o.contact.asJson,
          "tins"    -> o.tins.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipperParty] = (c: HCursor) => {
    for {
      address <- c.downField("address").as[PartyAddress2]
      contact <- c.downField("contact").as[PartyContact]
      tins    <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
    } yield ShipperParty(address, contact, tins)
  }
}

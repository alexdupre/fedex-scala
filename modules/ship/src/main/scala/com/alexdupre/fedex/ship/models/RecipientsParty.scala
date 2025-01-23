package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The descriptive information of the recipient for the shipment and the physical location for the package destination.
  *
  * @param tins
  *   This is the tax identification number details.
  * @param deliveryInstructions
  *   Specify the delivery instructions to be added with the shipment. Use with Ground Home Delivery.<br>Example: Delivery Instructions
  */
case class RecipientsParty(
    address: PartyAddress2,
    contact: PartyContact,
    tins: Option[Seq[TaxpayerIdentification]] = None,
    deliveryInstructions: Option[String] = None
)

object RecipientsParty {

  given Encoder[RecipientsParty] = new Encoder.AsObject[RecipientsParty] {
    final def encodeObject(o: RecipientsParty): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address"              -> o.address.asJson,
          "contact"              -> o.contact.asJson,
          "tins"                 -> o.tins.asJson,
          "deliveryInstructions" -> o.deliveryInstructions.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RecipientsParty] = (c: HCursor) => {
    for {
      address              <- c.downField("address").as[PartyAddress2]
      contact              <- c.downField("contact").as[PartyContact]
      tins                 <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
      deliveryInstructions <- c.downField("deliveryInstructions").as[Option[String]]
    } yield RecipientsParty(address, contact, tins, deliveryInstructions)
  }
}

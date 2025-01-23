package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Party_3 Model
  *
  * @param tins
  *   This is the tax identification number details.
  */
case class Party3(
    address: Option[PartyAddress1] = None,
    contact: Option[PartyContact1] = None,
    tins: Option[Seq[TaxpayerIdentification]] = None
)

object Party3 {

  given Encoder[Party3] = new Encoder.AsObject[Party3] {
    final def encodeObject(o: Party3): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address" -> o.address.asJson,
          "contact" -> o.contact.asJson,
          "tins"    -> o.tins.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Party3] = (c: HCursor) => {
    for {
      address <- c.downField("address").as[Option[PartyAddress1]]
      contact <- c.downField("contact").as[Option[PartyContact1]]
      tins    <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
    } yield Party3(address, contact, tins)
  }
}

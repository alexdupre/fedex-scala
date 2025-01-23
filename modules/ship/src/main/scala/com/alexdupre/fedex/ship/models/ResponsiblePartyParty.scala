package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the payer Information responsible for paying for the shipment. <br>Note: ResponsibleParty accountNumber is required for ACCOUNT
  * based services.
  */
case class ResponsiblePartyParty(
    accountNumber: PartyAccountNumber,
    address: Option[PartyAddress] = None,
    contact: Option[PartyContact] = None
)

object ResponsiblePartyParty {

  given Encoder[ResponsiblePartyParty] = new Encoder.AsObject[ResponsiblePartyParty] {
    final def encodeObject(o: ResponsiblePartyParty): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "accountNumber" -> o.accountNumber.asJson,
          "address"       -> o.address.asJson,
          "contact"       -> o.contact.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ResponsiblePartyParty] = (c: HCursor) => {
    for {
      accountNumber <- c.downField("accountNumber").as[PartyAccountNumber]
      address       <- c.downField("address").as[Option[PartyAddress]]
      contact       <- c.downField("contact").as[Option[PartyContact]]
    } yield ResponsiblePartyParty(accountNumber, address, contact)
  }
}

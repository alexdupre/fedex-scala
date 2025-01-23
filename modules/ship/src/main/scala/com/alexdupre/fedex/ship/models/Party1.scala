package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Party_1 Model
  *
  * @param tins
  *   This is the tax identification number details.
  */
case class Party1(
    contact: PartyContact,
    address: Option[PartyAddress] = None,
    accountNumber: Option[PartyAccountNumber] = None,
    tins: Option[Seq[TaxpayerIdentification]] = None
)

object Party1 {

  given Encoder[Party1] = new Encoder.AsObject[Party1] {
    final def encodeObject(o: Party1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contact"       -> o.contact.asJson,
          "address"       -> o.address.asJson,
          "accountNumber" -> o.accountNumber.asJson,
          "tins"          -> o.tins.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Party1] = (c: HCursor) => {
    for {
      contact       <- c.downField("contact").as[PartyContact]
      address       <- c.downField("address").as[Option[PartyAddress]]
      accountNumber <- c.downField("accountNumber").as[Option[PartyAccountNumber]]
      tins          <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
    } yield Party1(contact, address, accountNumber, tins)
  }
}

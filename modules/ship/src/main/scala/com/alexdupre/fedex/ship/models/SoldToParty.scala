package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Will indicate the party responsible for purchasing the goods shipped from the shipper to the recipient. The sold to party is not
  * necessarily the recipient or the importer of record. The sold to party is relevant when the purchaser, rather than the recipient
  * determines when certain customs regulations apply.
  *
  * @param tins
  *   Used for adding the tax id
  */
case class SoldToParty(
    address: Option[PartyAddress] = None,
    contact: Option[PartyContact] = None,
    tins: Option[Seq[TaxpayerIdentification]] = None,
    accountNumber: Option[AccountNumber] = None
)

object SoldToParty {

  given Encoder[SoldToParty] = new Encoder.AsObject[SoldToParty] {
    final def encodeObject(o: SoldToParty): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address"       -> o.address.asJson,
          "contact"       -> o.contact.asJson,
          "tins"          -> o.tins.asJson,
          "accountNumber" -> o.accountNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SoldToParty] = (c: HCursor) => {
    for {
      address       <- c.downField("address").as[Option[PartyAddress]]
      contact       <- c.downField("contact").as[Option[PartyContact]]
      tins          <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
      accountNumber <- c.downField("accountNumber").as[Option[AccountNumber]]
    } yield SoldToParty(address, contact, tins, accountNumber)
  }
}

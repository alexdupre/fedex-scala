package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to provide the attributes such as physical address, contact information and account number information.
  *
  * @param tins
  *   This is the tax identification number details.
  */
case class Party2(
    address: Option[Address1] = None,
    contact: Option[Contact1] = None,
    accountNumber: Option[PartyAccountNumber] = None,
    tins: Option[Seq[TaxpayerIdentification]] = None
)

object Party2 {

  given Encoder[Party2] = new Encoder.AsObject[Party2] {
    final def encodeObject(o: Party2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address"       -> o.address.asJson,
          "contact"       -> o.contact.asJson,
          "accountNumber" -> o.accountNumber.asJson,
          "tins"          -> o.tins.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Party2] = (c: HCursor) => {
    for {
      address       <- c.downField("address").as[Option[Address1]]
      contact       <- c.downField("contact").as[Option[Contact1]]
      accountNumber <- c.downField("accountNumber").as[Option[PartyAccountNumber]]
      tins          <- c.downField("tins").as[Option[Seq[TaxpayerIdentification]]]
    } yield Party2(address, contact, accountNumber, tins)
  }
}

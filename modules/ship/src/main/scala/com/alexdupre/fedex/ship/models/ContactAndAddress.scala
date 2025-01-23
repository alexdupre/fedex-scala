package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the contact and address details of a location. */
case class ContactAndAddress(
    contact: Option[Contact1] = None,
    address: Option[Address1] = None
)

object ContactAndAddress {

  given Encoder[ContactAndAddress] = new Encoder.AsObject[ContactAndAddress] {
    final def encodeObject(o: ContactAndAddress): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contact" -> o.contact.asJson,
          "address" -> o.address.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContactAndAddress] = (c: HCursor) => {
    for {
      contact <- c.downField("contact").as[Option[Contact1]]
      address <- c.downField("address").as[Option[Address1]]
    } yield ContactAndAddress(contact, address)
  }
}

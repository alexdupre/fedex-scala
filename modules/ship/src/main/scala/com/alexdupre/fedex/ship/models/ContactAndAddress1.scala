package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the contact and address details of a location. */
case class ContactAndAddress1(
    contact: Option[Contact2] = None,
    address: Option[Address1] = None
)

object ContactAndAddress1 {

  given Encoder[ContactAndAddress1] = new Encoder.AsObject[ContactAndAddress1] {
    final def encodeObject(o: ContactAndAddress1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contact" -> o.contact.asJson,
          "address" -> o.address.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContactAndAddress1] = (c: HCursor) => {
    for {
      contact <- c.downField("contact").as[Option[Contact2]]
      address <- c.downField("address").as[Option[Address1]]
    } yield ContactAndAddress1(contact, address)
  }
}

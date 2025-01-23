package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the contact and address details of a location. */
case class ContactAndAddressVerify(
    contact: Option[ContactVerify] = None,
    address: Option[Address1] = None
)

object ContactAndAddressVerify {

  given Encoder[ContactAndAddressVerify] = new Encoder.AsObject[ContactAndAddressVerify] {
    final def encodeObject(o: ContactAndAddressVerify): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contact" -> o.contact.asJson,
          "address" -> o.address.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContactAndAddressVerify] = (c: HCursor) => {
    for {
      contact <- c.downField("contact").as[Option[ContactVerify]]
      address <- c.downField("address").as[Option[Address1]]
    } yield ContactAndAddressVerify(contact, address)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

case class JustContactAndAddress(
    address: Option[Address] = None,
    contact: Option[Contact] = None
)

object JustContactAndAddress {

  given Encoder[JustContactAndAddress] = new Encoder.AsObject[JustContactAndAddress] {
    final def encodeObject(o: JustContactAndAddress): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address" -> o.address.asJson,
          "contact" -> o.contact.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[JustContactAndAddress] = (c: HCursor) => {
    for {
      address <- c.downField("address").as[Option[Address]]
      contact <- c.downField("contact").as[Option[Contact]]
    } yield JustContactAndAddress(address, contact)
  }
}

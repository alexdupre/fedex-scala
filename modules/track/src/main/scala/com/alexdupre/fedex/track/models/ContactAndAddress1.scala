package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Location Contact And Address.
  *
  * @param address
  *   Required.
  *
  * Descriptive data for a physical location. may be used as an actual physical address(place to which one could go), or a container of
  * 'address parts' which should be handled as a unit(such as a city-state-zip combination within the US).
  *
  * Conditional when used with Payor object. Required if entering using RECIPIENT or THIRD_PARTY. Required if not-authenticated and SENDER
  * is selected
  */
case class ContactAndAddress1(
    address: Option[AddressVO1] = None
)

object ContactAndAddress1 {

  given Encoder[ContactAndAddress1] = new Encoder.AsObject[ContactAndAddress1] {
    final def encodeObject(o: ContactAndAddress1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "address" -> o.address.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContactAndAddress1] = (c: HCursor) => {
    for {
      address <- c.downField("address").as[Option[AddressVO1]]
    } yield ContactAndAddress1(address)
  }
}

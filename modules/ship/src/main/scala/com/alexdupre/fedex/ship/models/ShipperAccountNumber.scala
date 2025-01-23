package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The account number associated with the shipment.
  *
  * @param value
  *   The account number value. Maximum length is 9 .<br>Example: Your account number
  */
case class ShipperAccountNumber(
    value: String
)

object ShipperAccountNumber {

  given Encoder[ShipperAccountNumber] = new Encoder.AsObject[ShipperAccountNumber] {
    final def encodeObject(o: ShipperAccountNumber): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipperAccountNumber] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[String]
    } yield ShipperAccountNumber(value)
  }
}

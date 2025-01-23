package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The account number of the recipient.
  *
  * @param value
  *   Conditional.<br> The account number value. Max Length is 9.<br>Example: 123456789
  */
case class PartyAccountNumber2(
    value: Option[String] = None
)

object PartyAccountNumber2 {

  given Encoder[PartyAccountNumber2] = new Encoder.AsObject[PartyAccountNumber2] {
    final def encodeObject(o: PartyAccountNumber2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PartyAccountNumber2] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Option[String]]
    } yield PartyAccountNumber2(value)
  }
}

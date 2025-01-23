package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is FedEx Account number details.
  *
  * @param value
  *   Conditional.<br> The account number value.<br> Max Length is 9.<br>Example: 12XXXXX89
  */
case class PartyAccountNumber(
    value: Option[String] = None
)

object PartyAccountNumber {

  given Encoder[PartyAccountNumber] = new Encoder.AsObject[PartyAccountNumber] {
    final def encodeObject(o: PartyAccountNumber): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PartyAccountNumber] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Option[String]]
    } yield PartyAccountNumber(value)
  }
}

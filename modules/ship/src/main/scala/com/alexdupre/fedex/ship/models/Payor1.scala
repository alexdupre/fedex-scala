package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Information about the person who is paying for the shipment. Not applicable for credit card payment. */
case class Payor1(
    responsibleParty: Option[Party2] = None
)

object Payor1 {

  given Encoder[Payor1] = new Encoder.AsObject[Payor1] {
    final def encodeObject(o: Payor1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "responsibleParty" -> o.responsibleParty.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Payor1] = (c: HCursor) => {
    for {
      responsibleParty <- c.downField("responsibleParty").as[Option[Party2]]
    } yield Payor1(responsibleParty)
  }
}

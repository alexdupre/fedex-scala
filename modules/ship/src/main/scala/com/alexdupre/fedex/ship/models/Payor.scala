package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Payor is mandatory when the paymentType is RECIPIENT, THIRD_PARTY or COLLECT. */
case class Payor(
    responsibleParty: ResponsiblePartyParty
)

object Payor {

  given Encoder[Payor] = new Encoder.AsObject[Payor] {
    final def encodeObject(o: Payor): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "responsibleParty" -> o.responsibleParty.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Payor] = (c: HCursor) => {
    for {
      responsibleParty <- c.downField("responsibleParty").as[ResponsiblePartyParty]
    } yield Payor(responsibleParty)
  }
}

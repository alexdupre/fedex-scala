package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the total mass of the contained explosive substances, without the mass of any casings, bullets, shells, etc.
  *
  * @param amount
  *   Specifies amount.<br>Example: 10.0
  * @param units
  *   Specifies net explosive units.<br>Example: units
  * @param `type`
  *   Specifies net explosive classification type.<br>Example: NET_EXPLOSIVE_WEIGHT
  */
case class NetExplosiveDetail(
    amount: Option[Double] = None,
    units: Option[String] = None,
    `type`: Option[String] = None
)

object NetExplosiveDetail {

  given Encoder[NetExplosiveDetail] = new Encoder.AsObject[NetExplosiveDetail] {
    final def encodeObject(o: NetExplosiveDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount" -> o.amount.asJson,
          "units"  -> o.units.asJson,
          "type"   -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[NetExplosiveDetail] = (c: HCursor) => {
    for {
      amount <- c.downField("amount").as[Option[Double]]
      units  <- c.downField("units").as[Option[String]]
      `type` <- c.downField("type").as[Option[String]]
    } yield NetExplosiveDetail(amount, units, `type`)
  }
}

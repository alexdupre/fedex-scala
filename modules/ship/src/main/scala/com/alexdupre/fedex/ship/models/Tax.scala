package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the tax for the shipment.
  *
  * @param amount
  *   Indicates the amount of tax.<br>Example: 10.0
  * @param level
  *   Indicates the Level of Tax.<br>Example: level
  * @param description
  *   Placeholder for the tax description.<br>Example: descrption
  * @param `type`
  *   Placeholder for the Type of the Tax.<br>Example:type
  */
case class Tax(
    amount: Option[Double] = None,
    level: Option[String] = None,
    description: Option[String] = None,
    `type`: Option[String] = None
)

object Tax {

  given Encoder[Tax] = new Encoder.AsObject[Tax] {
    final def encodeObject(o: Tax): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "amount"      -> o.amount.asJson,
          "level"       -> o.level.asJson,
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Tax] = (c: HCursor) => {
    for {
      amount      <- c.downField("amount").as[Option[Double]]
      level       <- c.downField("level").as[Option[String]]
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[String]]
    } yield Tax(amount, level, description, `type`)
  }
}

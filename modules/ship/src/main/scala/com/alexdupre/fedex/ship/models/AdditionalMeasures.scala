package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** AdditionalMeasures Model
  *
  * @param quantity
  *   Specify commodity quantity.
  * @param units
  *   Unit of measure used to express the quantity of this commodity line item.
  */
case class AdditionalMeasures(
    quantity: Option[Double] = None,
    units: Option[String] = None
)

object AdditionalMeasures {

  given Encoder[AdditionalMeasures] = new Encoder.AsObject[AdditionalMeasures] {
    final def encodeObject(o: AdditionalMeasures): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "quantity" -> o.quantity.asJson,
          "units"    -> o.units.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AdditionalMeasures] = (c: HCursor) => {
    for {
      quantity <- c.downField("quantity").as[Option[Double]]
      units    <- c.downField("units").as[Option[String]]
    } yield AdditionalMeasures(quantity, units)
  }
}

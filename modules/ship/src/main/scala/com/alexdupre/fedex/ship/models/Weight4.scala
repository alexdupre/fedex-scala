package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** It is the total weight of the commodity.<br>Note: Weight is not required for One rate shipments
  *
  * @param units
  *   Indicate the weight unit type. The package and commodity weight unit should be the same else the request will result in an error.
  * @param value
  *   Weight Value.<br> Example: 68.25
  */
case class Weight4(
    units: Weight4.Units,
    value: Double
)

object Weight4 {
  enum Units {
    case KG
    case UNKNOWN_DEFAULT
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.map(s => scala.util.Try(Units.valueOf(s)).getOrElse(Units.UNKNOWN_DEFAULT))
  }
  given Encoder[Weight4] = new Encoder.AsObject[Weight4] {
    final def encodeObject(o: Weight4): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "units" -> o.units.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Weight4] = (c: HCursor) => {
    for {
      units <- c.downField("units").as[Units]
      value <- c.downField("value").as[Double]
    } yield Weight4(units, value)
  }
}

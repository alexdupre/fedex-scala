package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are the package weight details.<br>Note: Weight is not required for One rate shipments
  *
  * @param units
  *   Specifies the package weight unit type.<br>Example:KG
  * @param value
  *   Weight Value.<br> Example: 68.25<br><a href='https://developer.fedex.com/api/en-us/guides/api-reference.html#packagetypes'
  *   target='_blank'>Click here to see Weight Values</a>.
  */
case class Weight(
    units: Weight.Units,
    value: Double
)

object Weight {
  enum Units {
    case KG
    case LB
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.emapTry(s => scala.util.Try(Units.valueOf(s)))
  }
  given Encoder[Weight] = new Encoder.AsObject[Weight] {
    final def encodeObject(o: Weight): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "units" -> o.units.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Weight] = (c: HCursor) => {
    for {
      units <- c.downField("units").as[Units]
      value <- c.downField("value").as[Double]
    } yield Weight(units, value)
  }
}

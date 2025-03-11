package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are the package weight details.<br>Note: Weight is not required for One rate shipments
  *
  * @param units
  *   Indicate the weight unit type. The package and commodity weight unit should be the same else the request will result in an
  *   error.<br>Example:KG
  * @param value
  *   Weight Value.<br> Example: 68.25<br>
  */
case class Weight3(
    units: Weight3.Units,
    value: Double
)

object Weight3 {
  enum Units {
    case KG
    case LB
    case UNKNOWN_DEFAULT
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.map(s => scala.util.Try(Units.valueOf(s)).getOrElse(Units.UNKNOWN_DEFAULT))
  }
  given Encoder[Weight3] = new Encoder.AsObject[Weight3] {
    final def encodeObject(o: Weight3): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "units" -> o.units.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Weight3] = (c: HCursor) => {
    for {
      units <- c.downField("units").as[Units]
      value <- c.downField("value").as[Double]
    } yield Weight3(units, value)
  }
}

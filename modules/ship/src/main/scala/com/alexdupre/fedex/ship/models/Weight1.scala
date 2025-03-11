package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the total dry ice weight in all the packages of the shipment.
  *
  * @param units
  *   For the Dry Ice weight in the shipment the unit of measure must be KG.
  * @param value
  *   Weight Value.<br> Example: 68.25<br><a href='https://developer.fedex.com/api/en-us/guides/api-reference.html#packagetypes'
  *   target='_blank'>Click here to see Weight Values</a>.
  */
case class Weight1(
    units: Option[Weight1.Units] = None,
    value: Option[Double] = None
)

object Weight1 {
  enum Units {
    case KG
    case LB
    case UNKNOWN_DEFAULT
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.map(s => scala.util.Try(Units.valueOf(s)).getOrElse(Units.UNKNOWN_DEFAULT))
  }
  given Encoder[Weight1] = new Encoder.AsObject[Weight1] {
    final def encodeObject(o: Weight1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "units" -> o.units.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Weight1] = (c: HCursor) => {
    for {
      units <- c.downField("units").as[Option[Units]]
      value <- c.downField("value").as[Option[Double]]
    } yield Weight1(units, value)
  }
}

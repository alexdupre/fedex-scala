package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Distance remaining to the destination. Only returned for FedEx Custom Critical shipments.
  *
  * @param units
  *   Field which holds the distance unit type.
  * @param value
  *   Field which holds the value for the distance.<br> Example: 685.78
  */
case class Distance(
    units: Option[Distance.Units] = None,
    value: Option[Double] = None
)

object Distance {
  enum Units {
    case KM
    case MI
  }
  object Units {
    given Encoder[Units] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Units] = Decoder.decodeString.emapTry(s => scala.util.Try(Units.valueOf(s)))
  }
  given Encoder[Distance] = new Encoder.AsObject[Distance] {
    final def encodeObject(o: Distance): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "units" -> o.units.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Distance] = (c: HCursor) => {
    for {
      units <- c.downField("units").as[Option[Units]]
      value <- c.downField("value").as[Option[Double]]
    } yield Distance(units, value)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are the package weight details.
  *
  * @param unit
  *   This is package weight type.
  * @param value
  *   This is package weight. Max. Length is 99999. <br> Example: 22222.0
  */
case class Weight(
    unit: Option[Weight.Unit] = None,
    value: Option[String] = None
)

object Weight {
  enum Unit {
    case KG
    case LB
    case UNKNOWN_DEFAULT
  }
  object Unit {
    given Encoder[Unit] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Unit] = Decoder.decodeString.map(s => scala.util.Try(Unit.valueOf(s)).getOrElse(Unit.UNKNOWN_DEFAULT))
  }
  given Encoder[Weight] = new Encoder.AsObject[Weight] {
    final def encodeObject(o: Weight): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "unit"  -> o.unit.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Weight] = (c: HCursor) => {
    for {
      unit  <- c.downField("unit").as[Option[Unit]]
      value <- c.downField("value").as[Option[String]]
    } yield Weight(unit, value)
  }
}

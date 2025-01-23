package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Each instance of this data type represents a barcode whose content must be represented as binary data (i.e. not ASCII text).
  *
  * @param `type`
  *   The kind of barcode data in this instance.<br> Example: COMMON-2D
  * @param value
  *   This is the value.
  */
case class BinaryBarcode(
    `type`: Option[String] = None,
    value: Option[String] = None
)

object BinaryBarcode {

  given Encoder[BinaryBarcode] = new Encoder.AsObject[BinaryBarcode] {
    final def encodeObject(o: BinaryBarcode): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"  -> o.`type`.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[BinaryBarcode] = (c: HCursor) => {
    for {
      `type` <- c.downField("type").as[Option[String]]
      value  <- c.downField("value").as[Option[String]]
    } yield BinaryBarcode(`type`, value)
  }
}

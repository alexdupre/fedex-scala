package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Each instance of this data type represents a barcode whose content must be represented as ASCII text (i.e. not binary data).
  *
  * @param `type`
  *   The kind of barcode data in this instance. example valid values are:<br>ADDRESS - Represents the recipient address<br>GROUND - FedEx
  *   Ground parcel barcode<br>Example: ADDRESS
  * @param value
  *   This is the value.<br>Example: 1010062512241535917900794953544894
  */
case class StringBarcode(
    `type`: Option[String] = None,
    value: Option[String] = None
)

object StringBarcode {

  given Encoder[StringBarcode] = new Encoder.AsObject[StringBarcode] {
    final def encodeObject(o: StringBarcode): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"  -> o.`type`.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[StringBarcode] = (c: HCursor) => {
    for {
      `type` <- c.downField("type").as[Option[String]]
      value  <- c.downField("value").as[Option[String]]
    } yield StringBarcode(`type`, value)
  }
}

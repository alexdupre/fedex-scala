package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Product Name information.
  *
  * @param `type`
  *   The type of name (long, medium, short, etc.) to which this value refers.<br>Example: long
  * @param encoding
  *   The character encoding used to represent this product name. <br>Example: UTF-8
  * @param value
  *   Specifies the value of the Product.<br>Example: F-2
  */
case class ProductName(
    `type`: Option[String] = None,
    encoding: Option[String] = None,
    value: Option[String] = None
)

object ProductName {

  given Encoder[ProductName] = new Encoder.AsObject[ProductName] {
    final def encodeObject(o: ProductName): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"     -> o.`type`.asJson,
          "encoding" -> o.encoding.asJson,
          "value"    -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ProductName] = (c: HCursor) => {
    for {
      `type`   <- c.downField("type").as[Option[String]]
      encoding <- c.downField("encoding").as[Option[String]]
      value    <- c.downField("value").as[Option[String]]
    } yield ProductName(`type`, encoding, value)
  }
}

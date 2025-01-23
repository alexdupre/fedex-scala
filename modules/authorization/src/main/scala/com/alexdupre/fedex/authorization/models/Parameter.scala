package com.alexdupre.fedex.authorization.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Parameter Model
  *
  * @param value
  *   Indicates the error option to be applied.
  * @param key
  *   Indicates the value associated with the key.
  */
case class Parameter(
    value: Option[String] = None,
    key: Option[String] = None
)

object Parameter {

  given Encoder[Parameter] = new Encoder.AsObject[Parameter] {
    final def encodeObject(o: Parameter): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "value" -> o.value.asJson,
          "key"   -> o.key.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Parameter] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Option[String]]
      key   <- c.downField("key").as[Option[String]]
    } yield Parameter(value, key)
  }
}

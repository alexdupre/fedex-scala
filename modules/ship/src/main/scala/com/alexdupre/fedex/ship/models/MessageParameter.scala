package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** MessageParameter Model
  *
  * @param id
  *   Specifies the message parameter code.<br>Example: message ID
  * @param value
  *   Specifies the message parameter value of the code.<br>Example: value
  */
case class MessageParameter(
    id: Option[String] = None,
    value: Option[String] = None
)

object MessageParameter {

  given Encoder[MessageParameter] = new Encoder.AsObject[MessageParameter] {
    final def encodeObject(o: MessageParameter): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "id"    -> o.id.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[MessageParameter] = (c: HCursor) => {
    for {
      id    <- c.downField("id").as[Option[String]]
      value <- c.downField("value").as[Option[String]]
    } yield MessageParameter(id, value)
  }
}

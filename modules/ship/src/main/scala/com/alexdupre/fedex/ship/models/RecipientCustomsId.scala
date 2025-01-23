package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this element to provide valid identification details. Used for populating brazil tax id.
  *
  * @param `type`
  *   This is ID Type.
  * @param value
  *   This is the ID number.
  */
case class RecipientCustomsId(
    `type`: Option[RecipientCustomsId.Type] = None,
    value: Option[String] = None
)

object RecipientCustomsId {
  enum Type {
    case COMPANY
    case INDIVIDUAL
    case PASSPORT
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[RecipientCustomsId] = new Encoder.AsObject[RecipientCustomsId] {
    final def encodeObject(o: RecipientCustomsId): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"  -> o.`type`.asJson,
          "value" -> o.value.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RecipientCustomsId] = (c: HCursor) => {
    for {
      `type` <- c.downField("type").as[Option[Type]]
      value  <- c.downField("value").as[Option[String]]
    } yield RecipientCustomsId(`type`, value)
  }
}

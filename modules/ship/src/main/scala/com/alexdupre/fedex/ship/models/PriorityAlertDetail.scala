package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the Priority Alert Detail.
  *
  * @param enhancementTypes
  *   The types of all enhancement for the Priority Alert.<br>Example: PRIORITY_ALERT_PLUS
  * @param content
  *   Specifies Content for the Priority Alert Detail.<br>Example:string
  */
case class PriorityAlertDetail(
    enhancementTypes: Option[Seq[String]] = None,
    content: Option[Seq[String]] = None
)

object PriorityAlertDetail {

  given Encoder[PriorityAlertDetail] = new Encoder.AsObject[PriorityAlertDetail] {
    final def encodeObject(o: PriorityAlertDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "enhancementTypes" -> o.enhancementTypes.asJson,
          "content"          -> o.content.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PriorityAlertDetail] = (c: HCursor) => {
    for {
      enhancementTypes <- c.downField("enhancementTypes").as[Option[Seq[String]]]
      content          <- c.downField("content").as[Option[Seq[String]]]
    } yield PriorityAlertDetail(enhancementTypes, content)
  }
}

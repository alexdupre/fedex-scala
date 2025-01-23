package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This object contains reason description and type.
  *
  * @param description
  *   Field which holds the reason description. <br> Example: Wrong color
  * @param `type`
  *   Field which holds the reason type.<br> Example: REJECTED
  */
case class ReasonDetail(
    description: Option[String] = None,
    `type`: Option[String] = None
)

object ReasonDetail {

  given Encoder[ReasonDetail] = new Encoder.AsObject[ReasonDetail] {
    final def encodeObject(o: ReasonDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReasonDetail] = (c: HCursor) => {
    for {
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[String]]
    } yield ReasonDetail(description, `type`)
  }
}

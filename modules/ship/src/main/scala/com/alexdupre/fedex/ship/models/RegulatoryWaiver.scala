package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the regulatory waiver.
  *
  * @param advisories
  *   Indicates the advisories list.
  * @param description
  *   Indicates the regulatory prohibitions description.<br>Example: description
  * @param id
  *   Indicates the prohibitory ID.<br>Example: id
  */
case class RegulatoryWaiver(
    advisories: Option[Seq[Message]] = None,
    description: Option[String] = None,
    id: Option[String] = None
)

object RegulatoryWaiver {

  given Encoder[RegulatoryWaiver] = new Encoder.AsObject[RegulatoryWaiver] {
    final def encodeObject(o: RegulatoryWaiver): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "advisories"  -> o.advisories.asJson,
          "description" -> o.description.asJson,
          "id"          -> o.id.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RegulatoryWaiver] = (c: HCursor) => {
    for {
      advisories  <- c.downField("advisories").as[Option[Seq[Message]]]
      description <- c.downField("description").as[Option[String]]
      id          <- c.downField("id").as[Option[String]]
    } yield RegulatoryWaiver(advisories, description, id)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Description of the packaging used for this shipment.
  *
  * @param description
  *   Indicate the packaging type description.<br> Example: FedEx Pak
  * @param `type`
  *   Indicate the packaging type.<br><a onclick='loadDocReference("packagetypes")'>Click here to see Package Types</a>
  */
case class PackagingDescription(
    description: Option[String] = None,
    `type`: Option[String] = None
)

object PackagingDescription {

  given Encoder[PackagingDescription] = new Encoder.AsObject[PackagingDescription] {
    final def encodeObject(o: PackagingDescription): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description" -> o.description.asJson,
          "type"        -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackagingDescription] = (c: HCursor) => {
    for {
      description <- c.downField("description").as[Option[String]]
      `type`      <- c.downField("type").as[Option[String]]
    } yield PackagingDescription(description, `type`)
  }
}

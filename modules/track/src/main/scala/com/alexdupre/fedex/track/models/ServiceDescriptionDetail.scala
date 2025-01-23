package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This object contains service description details for the package.
  *
  * @param description
  *   Field which holds the text description of the service type of this package.<br> Example: FedEx Freight Economy
  * @param shortDescription
  *   Field which holds the abbreviated text description of the service type of this package.<br> Example: FL
  * @param `type`
  *   This is the service type.<br> Example: FEDEX_FREIGHT_ECONOMY<br><a onclick='loadDocReference("servicetypes")'>Click here to see
  *   Service Types</a>
  */
case class ServiceDescriptionDetail(
    description: Option[String] = None,
    shortDescription: Option[String] = None,
    `type`: Option[String] = None
)

object ServiceDescriptionDetail {

  given Encoder[ServiceDescriptionDetail] = new Encoder.AsObject[ServiceDescriptionDetail] {
    final def encodeObject(o: ServiceDescriptionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description"      -> o.description.asJson,
          "shortDescription" -> o.shortDescription.asJson,
          "type"             -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ServiceDescriptionDetail] = (c: HCursor) => {
    for {
      description      <- c.downField("description").as[Option[String]]
      shortDescription <- c.downField("shortDescription").as[Option[String]]
      `type`           <- c.downField("type").as[Option[String]]
    } yield ServiceDescriptionDetail(description, shortDescription, `type`)
  }
}

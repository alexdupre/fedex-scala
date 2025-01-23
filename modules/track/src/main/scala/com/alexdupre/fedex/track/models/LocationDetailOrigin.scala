package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Location details for the fedex facility.
  *
  * @param locationId
  *   Location Identification for facilities identified by an alpha numeric location code. Passing Location Id of the Hold at Location (HAL)
  *   address is strongly recommended to ensure packages are delivered to the correct address.<br> Example: SEA
  */
case class LocationDetailOrigin(
    locationId: Option[String] = None,
    locationContactAndAddress: Option[ContactAndAddress1] = None
)

object LocationDetailOrigin {

  given Encoder[LocationDetailOrigin] = new Encoder.AsObject[LocationDetailOrigin] {
    final def encodeObject(o: LocationDetailOrigin): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "locationId"                -> o.locationId.asJson,
          "locationContactAndAddress" -> o.locationContactAndAddress.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[LocationDetailOrigin] = (c: HCursor) => {
    for {
      locationId                <- c.downField("locationId").as[Option[String]]
      locationContactAndAddress <- c.downField("locationContactAndAddress").as[Option[ContactAndAddress1]]
    } yield LocationDetailOrigin(locationId, locationContactAndAddress)
  }
}

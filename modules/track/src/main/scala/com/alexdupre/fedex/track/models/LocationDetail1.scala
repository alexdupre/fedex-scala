package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Location details for the fedex facility.
  *
  * @param locationId
  *   Location Identification for facilities identified by an alpha numeric location code. Passing Location Id of the Hold at Location (HAL)
  *   address is strongly recommended to ensure packages are delivered to the correct address.<br> Example: SEA
  * @param locationType
  *   This field holds FedEx Location Type. If Location Type not available we will get empty value.
  */
case class LocationDetail1(
    locationId: Option[String] = None,
    locationContactAndAddress: Option[ContactAndAddress1] = None,
    locationType: Option[LocationDetail1.LocationType] = None
)

object LocationDetail1 {
  enum LocationType {
    case FEDEX_AUTHORIZED_SHIP_CENTER
    case FEDEX_OFFICE
    case FEDEX_SELF_SERVICE_LOCATION
    case FEDEX_GROUND_TERMINAL
    case FEDEX_ONSITE
    case FEDEX_EXPRESS_STATION
    case FEDEX_FACILITY
    case FEDEX_FREIGHT_SERVICE_CENTER
    case FEDEX_HOME_DELIVERY_STATION
    case FEDEX_SHIP_AND_GET
    case FEDEX_SHIPSITE
    case FEDEX_SMART_POST_HUB
    case UNKNOWN_DEFAULT
  }
  object LocationType {
    given Encoder[LocationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LocationType] =
      Decoder.decodeString.map(s => scala.util.Try(LocationType.valueOf(s)).getOrElse(LocationType.UNKNOWN_DEFAULT))
  }
  given Encoder[LocationDetail1] = new Encoder.AsObject[LocationDetail1] {
    final def encodeObject(o: LocationDetail1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "locationId"                -> o.locationId.asJson,
          "locationContactAndAddress" -> o.locationContactAndAddress.asJson,
          "locationType"              -> o.locationType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[LocationDetail1] = (c: HCursor) => {
    for {
      locationId                <- c.downField("locationId").as[Option[String]]
      locationContactAndAddress <- c.downField("locationContactAndAddress").as[Option[ContactAndAddress1]]
      locationType              <- c.downField("locationType").as[Option[LocationType]]
    } yield LocationDetail1(locationId, locationContactAndAddress, locationType)
  }
}

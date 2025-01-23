package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify required information for a shipment to be held at destination FedEx location.<br><i>Note: This object
  * HoldAtLocationDetail is Required, when HOLD_AT_LOCATION is chosen in the specialServiceTypes.</i>
  *
  * @param locationId
  *   This is an alphanumeric identifier used for Location/Facility Identification.<br>Example: YBZA<br>Note: <ul><li>For HAL Shipment,
  *   Location ID is <b>REQUIRED</b> to ensure packages are delivered to the right location.</li><li>Use endpoint [<b>Find Location</b>] in
  *   [<b>Location Search API</b>], to find the correct location ID for your shipment.</li></ul>
  * @param locationType
  *   Type of facility at which package/shipment is to be held.<br> Example: FEDEX_ONSITE
  */
case class HoldAtLocationDetail(
    locationId: String,
    locationContactAndAddress: Option[ContactAndAddress] = None,
    locationType: Option[HoldAtLocationDetail.LocationType] = None
)

object HoldAtLocationDetail {
  enum LocationType {
    case FEDEX_AUTHORIZED_SHIP_CENTER
    case FEDEX_OFFICE
    case FEDEX_SELF_SERVICE_LOCATION
    case FEDEX_STAFFED
    case RETAIL_ALLICANCE_LOCATION
    case FEDEX_GROUND_TERMINAL
    case FEDEX_ONSITE
  }
  object LocationType {
    given Encoder[LocationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LocationType] = Decoder.decodeString.emapTry(s => scala.util.Try(LocationType.valueOf(s)))
  }
  given Encoder[HoldAtLocationDetail] = new Encoder.AsObject[HoldAtLocationDetail] {
    final def encodeObject(o: HoldAtLocationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "locationId"                -> o.locationId.asJson,
          "locationContactAndAddress" -> o.locationContactAndAddress.asJson,
          "locationType"              -> o.locationType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[HoldAtLocationDetail] = (c: HCursor) => {
    for {
      locationId                <- c.downField("locationId").as[String]
      locationContactAndAddress <- c.downField("locationContactAndAddress").as[Option[ContactAndAddress]]
      locationType              <- c.downField("locationType").as[Option[LocationType]]
    } yield HoldAtLocationDetail(locationId, locationContactAndAddress, locationType)
  }
}

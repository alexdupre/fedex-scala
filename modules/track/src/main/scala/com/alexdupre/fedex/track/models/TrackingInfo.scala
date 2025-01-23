package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Tracking details for the shipment to be tracked
  *
  * @param shipDateBegin
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number within a specific date range. <br>Format: YYYY-MM-DD<br> Example: 2020-03-29
  * @param shipDateEnd
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number within a specific date range. <br>Format: YYYY-MM-DD<br> Example: 2020-04-01
  */
case class TrackingInfo(
    trackingNumberInfo: TrackingNumberInfo,
    shipDateBegin: Option[String] = None,
    shipDateEnd: Option[String] = None
)

object TrackingInfo {

  given Encoder[TrackingInfo] = new Encoder.AsObject[TrackingInfo] {
    final def encodeObject(o: TrackingInfo): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumberInfo" -> o.trackingNumberInfo.asJson,
          "shipDateBegin"      -> o.shipDateBegin.asJson,
          "shipDateEnd"        -> o.shipDateEnd.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingInfo] = (c: HCursor) => {
    for {
      trackingNumberInfo <- c.downField("trackingNumberInfo").as[TrackingNumberInfo]
      shipDateBegin      <- c.downField("shipDateBegin").as[Option[String]]
      shipDateEnd        <- c.downField("shipDateEnd").as[Option[String]]
    } yield TrackingInfo(trackingNumberInfo, shipDateBegin, shipDateEnd)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements for Tracking by Tracking Number.
  *
  * @param includeDetailedScans
  *   Indicates if detailed scans are requested or not. <br/>Valid values are True, or False.
  * @param trackingInfo
  *   The tracking information of the shipment to be tracked. At least one occurrence of TrackingInfo is required. Maximum limit is 30.
  */
case class FullSchemaTrackingNumbers(
    includeDetailedScans: Boolean,
    trackingInfo: Seq[TrackingInfo]
)

object FullSchemaTrackingNumbers {

  given Encoder[FullSchemaTrackingNumbers] = new Encoder.AsObject[FullSchemaTrackingNumbers] {
    final def encodeObject(o: FullSchemaTrackingNumbers): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "includeDetailedScans" -> o.includeDetailedScans.asJson,
          "trackingInfo"         -> o.trackingInfo.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaTrackingNumbers] = (c: HCursor) => {
    for {
      includeDetailedScans <- c.downField("includeDetailedScans").as[Boolean]
      trackingInfo         <- c.downField("trackingInfo").as[Seq[TrackingInfo]]
    } yield FullSchemaTrackingNumbers(includeDetailedScans, trackingInfo)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The response elements for Tracking by Associated Shipment request.
  *
  * @param completeTrackResults
  *   Contains the detailed tracking entry information.
  * @param alerts
  *   The cxs alert type, alert code, and alert messages.<br>Example: example: TRACKING.DATA.NOTFOUND - Tracking data unavailable
  */
case class TrackingMPSResponse(
    completeTrackResults: Option[Seq[CompleteTrackResult]] = None,
    alerts: Option[Seq[Alert]] = None
)

object TrackingMPSResponse {

  given Encoder[TrackingMPSResponse] = new Encoder.AsObject[TrackingMPSResponse] {
    final def encodeObject(o: TrackingMPSResponse): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "completeTrackResults" -> o.completeTrackResults.asJson,
          "alerts"               -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingMPSResponse] = (c: HCursor) => {
    for {
      completeTrackResults <- c.downField("completeTrackResults").as[Option[Seq[CompleteTrackResult]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield TrackingMPSResponse(completeTrackResults, alerts)
  }
}

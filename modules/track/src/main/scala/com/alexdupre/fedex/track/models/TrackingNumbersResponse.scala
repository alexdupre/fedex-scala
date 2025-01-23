package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The response elements for Tracking by tracking number request
  *
  * @param completeTrackResults
  *   Contains detailed tracking entry information. <br>Valid values are- ACTUAL_DELIVERY, ACTUAL_PICKUP, ACTUAL_TENDER, ANTICIPATED_TENDER,
  *   APPOINTMENT_DELIVERY, ATTEMPTED_DELIVERY, COMMITMENT, ESTIMATED_ARRIVAL_AT_GATEWAY, ESTIMATED_DELIVERY, ESTIMATED_PICKUP,
  *   ESTIMATED_RETURN_TO_STATION, SHIP, SHIPMENT_DATA_RECEIVED.
  * @param alerts
  *   The cxs alert type, alert code and alert message<br>Example: TRACKING.DATA.NOTFOUND - Tracking data unavailable
  */
case class TrackingNumbersResponse(
    completeTrackResults: Option[Seq[CompleteTrackResult]] = None,
    alerts: Option[Seq[Alert]] = None
)

object TrackingNumbersResponse {

  given Encoder[TrackingNumbersResponse] = new Encoder.AsObject[TrackingNumbersResponse] {
    final def encodeObject(o: TrackingNumbersResponse): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "completeTrackResults" -> o.completeTrackResults.asJson,
          "alerts"               -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingNumbersResponse] = (c: HCursor) => {
    for {
      completeTrackResults <- c.downField("completeTrackResults").as[Option[Seq[CompleteTrackResult]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield TrackingNumbersResponse(completeTrackResults, alerts)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The response elements for the Track by TCN request.
  *
  * @param completeTrackResults
  *   Contains detailed tracking entry information.
  * @param alerts
  *   alert type, alert code, and alert message<br>Example: TRACKING.DATA.NOTFOUND - Tracking data unavailable
  */
case class TrackingNumbersResponseTCN(
    completeTrackResults: Option[Seq[CompleteTrackResult]] = None,
    alerts: Option[Seq[Alert]] = None
)

object TrackingNumbersResponseTCN {

  given Encoder[TrackingNumbersResponseTCN] = new Encoder.AsObject[TrackingNumbersResponseTCN] {
    final def encodeObject(o: TrackingNumbersResponseTCN): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "completeTrackResults" -> o.completeTrackResults.asJson,
          "alerts"               -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingNumbersResponseTCN] = (c: HCursor) => {
    for {
      completeTrackResults <- c.downField("completeTrackResults").as[Option[Seq[CompleteTrackResult]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield TrackingNumbersResponseTCN(completeTrackResults, alerts)
  }
}

package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the response elements for Track by alternate reference request.
  *
  * @param cxsErrors
  *   Indicates error alert when suspicious files, potential exploits and viruses found while scanning files , directories and user
  *   accounts. This includes code, message and parameter.
  * @param completeTrackResults
  *   Contains detailed tracking entry information. <br>Valid values: ACTUAL_DELIVERY, ACTUAL_PICKKUP, ACTUAL_TENDER, ANTICIPATED_TENDER,
  *   APPOINTMENT_DELIVERY, ATTEMPTED_DELIVERY, COMMITMENT, ESTIMATED_ARRIVAL_AT_GATEWAY, ESTIMATED_DELIVERY, ESTIMATED_PICKUP,
  *   ESTIMATED_RETURN_TO_STATION, SHIP, SHIPMENT_DATE_RECEIVED
  * @param alerts
  *   The cxs alert type, alert code, and alert message that is received.<br>example: TRACKING.DATA.NOTFOUND - Tracking data unavailable
  * @param successful
  *   Indicates whether the tracking is successful.
  */
case class TrackingReferencesResponse(
    cxsErrors: Option[Seq[CXSError]] = None,
    completeTrackResults: Option[Seq[CompleteTrackResult]] = None,
    alerts: Option[Seq[Alert]] = None,
    successful: Option[Boolean] = None
)

object TrackingReferencesResponse {

  given Encoder[TrackingReferencesResponse] = new Encoder.AsObject[TrackingReferencesResponse] {
    final def encodeObject(o: TrackingReferencesResponse): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "cxsErrors"            -> o.cxsErrors.asJson,
          "completeTrackResults" -> o.completeTrackResults.asJson,
          "alerts"               -> o.alerts.asJson,
          "successful"           -> o.successful.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingReferencesResponse] = (c: HCursor) => {
    for {
      cxsErrors            <- c.downField("cxsErrors").as[Option[Seq[CXSError]]]
      completeTrackResults <- c.downField("completeTrackResults").as[Option[Seq[CompleteTrackResult]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
      successful           <- c.downField("successful").as[Option[Boolean]]
    } yield TrackingReferencesResponse(cxsErrors, completeTrackResults, alerts, successful)
  }
}

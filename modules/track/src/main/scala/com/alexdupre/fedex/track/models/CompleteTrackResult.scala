package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Detailed and complete track results
  *
  * @param trackingNumber
  *   A tracking number to identify a package.<br> Example: 123456789012
  * @param trackResults
  *   An array of detailed tracking information for the requested packages(s). In case of duplicate shipments, multiple track details will
  *   be populated.
  */
case class CompleteTrackResult(
    trackingNumber: Option[String] = None,
    trackResults: Option[Seq[TrackResult]] = None
)

object CompleteTrackResult {

  given Encoder[CompleteTrackResult] = new Encoder.AsObject[CompleteTrackResult] {
    final def encodeObject(o: CompleteTrackResult): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumber" -> o.trackingNumber.asJson,
          "trackResults"   -> o.trackResults.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompleteTrackResult] = (c: HCursor) => {
    for {
      trackingNumber <- c.downField("trackingNumber").as[Option[String]]
      trackResults   <- c.downField("trackResults").as[Option[Seq[TrackResult]]]
    } yield CompleteTrackResult(trackingNumber, trackResults)
  }
}

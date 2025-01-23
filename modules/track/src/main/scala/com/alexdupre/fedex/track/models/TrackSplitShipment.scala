package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Holds the information about split shipments.
  *
  * @param pieceCount
  *   Field which holds the number of pieces in the part.<br> Example: 10
  * @param statusDescription
  *   Field which holds human-readable description of the status. <br> Example: status
  * @param timestamp
  *   Field which holds the date and time the status began.<br>Example: '2019-05-07T08:00:07'
  * @param statusCode
  *   Field which holds the status code. <br> Example: statusCode
  */
case class TrackSplitShipment(
    pieceCount: Option[String] = None,
    statusDescription: Option[String] = None,
    timestamp: Option[String] = None,
    statusCode: Option[String] = None
)

object TrackSplitShipment {

  given Encoder[TrackSplitShipment] = new Encoder.AsObject[TrackSplitShipment] {
    final def encodeObject(o: TrackSplitShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "pieceCount"        -> o.pieceCount.asJson,
          "statusDescription" -> o.statusDescription.asJson,
          "timestamp"         -> o.timestamp.asJson,
          "statusCode"        -> o.statusCode.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackSplitShipment] = (c: HCursor) => {
    for {
      pieceCount        <- c.downField("pieceCount").as[Option[String]]
      statusDescription <- c.downField("statusDescription").as[Option[String]]
      timestamp         <- c.downField("timestamp").as[Option[String]]
      statusCode        <- c.downField("statusCode").as[Option[String]]
    } yield TrackSplitShipment(pieceCount, statusDescription, timestamp, statusCode)
  }
}

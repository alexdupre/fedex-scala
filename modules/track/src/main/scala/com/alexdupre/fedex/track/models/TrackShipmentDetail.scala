package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Shipment level details for the shipment being tracked. Includes overall shipment weight, contents etc.
  *
  * @param contents
  *   Field which holds information about contents of the shipment. Populated for secure users only.
  * @param beforePossessionStatus
  *   Indicates the shipment is not yet in FedEx possession, but is still in shipper's possession.<br> Example: false
  * @param weight
  *   Array of package level weights, which represent the total weight of the shipment.
  * @param contentPieceCount
  *   Field which holds information about content piece count of the shipment.<br> Example: 3333
  * @param splitShipments
  *   Field which holds information about split shipments.
  */
case class TrackShipmentDetail(
    contents: Option[Seq[ShipmentContent]] = None,
    beforePossessionStatus: Option[Boolean] = None,
    weight: Option[Seq[Weight]] = None,
    contentPieceCount: Option[String] = None,
    splitShipments: Option[Seq[TrackSplitShipment]] = None
)

object TrackShipmentDetail {

  given Encoder[TrackShipmentDetail] = new Encoder.AsObject[TrackShipmentDetail] {
    final def encodeObject(o: TrackShipmentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contents"               -> o.contents.asJson,
          "beforePossessionStatus" -> o.beforePossessionStatus.asJson,
          "weight"                 -> o.weight.asJson,
          "contentPieceCount"      -> o.contentPieceCount.asJson,
          "splitShipments"         -> o.splitShipments.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackShipmentDetail] = (c: HCursor) => {
    for {
      contents               <- c.downField("contents").as[Option[Seq[ShipmentContent]]]
      beforePossessionStatus <- c.downField("beforePossessionStatus").as[Option[Boolean]]
      weight                 <- c.downField("weight").as[Option[Seq[Weight]]]
      contentPieceCount      <- c.downField("contentPieceCount").as[Option[String]]
      splitShipments         <- c.downField("splitShipments").as[Option[Seq[TrackSplitShipment]]]
    } yield TrackShipmentDetail(contents, beforePossessionStatus, weight, contentPieceCount, splitShipments)
  }
}

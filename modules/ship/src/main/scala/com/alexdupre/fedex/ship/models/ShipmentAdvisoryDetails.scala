package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are shipment advisory details. */
case class ShipmentAdvisoryDetails(
    regulatoryAdvisory: Option[RegulatoryAdvisoryDetail] = None
)

object ShipmentAdvisoryDetails {

  given Encoder[ShipmentAdvisoryDetails] = new Encoder.AsObject[ShipmentAdvisoryDetails] {
    final def encodeObject(o: ShipmentAdvisoryDetails): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "regulatoryAdvisory" -> o.regulatoryAdvisory.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentAdvisoryDetails] = (c: HCursor) => {
    for {
      regulatoryAdvisory <- c.downField("regulatoryAdvisory").as[Option[RegulatoryAdvisoryDetail]]
    } yield ShipmentAdvisoryDetails(regulatoryAdvisory)
  }
}

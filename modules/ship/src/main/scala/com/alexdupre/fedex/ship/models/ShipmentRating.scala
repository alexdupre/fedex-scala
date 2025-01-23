package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** All shipment-level rating data for this shipment, which may include data for multiple rate types.
  *
  * @param actualRateType
  *   This rate type identifies which entry in the following array is considered as presenting the "actual" rates for the
  *   shipment.<br>Example: PAYOR_LIST_SHIPMENT
  * @param shipmentRateDetails
  *   Each element of this field provides shipment-level rate totals for a specific rate type.
  */
case class ShipmentRating(
    actualRateType: Option[String] = None,
    shipmentRateDetails: Option[Seq[ShipmentRateDetail]] = None
)

object ShipmentRating {

  given Encoder[ShipmentRating] = new Encoder.AsObject[ShipmentRating] {
    final def encodeObject(o: ShipmentRating): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "actualRateType"      -> o.actualRateType.asJson,
          "shipmentRateDetails" -> o.shipmentRateDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentRating] = (c: HCursor) => {
    for {
      actualRateType      <- c.downField("actualRateType").as[Option[String]]
      shipmentRateDetails <- c.downField("shipmentRateDetails").as[Option[Seq[ShipmentRateDetail]]]
    } yield ShipmentRating(actualRateType, shipmentRateDetails)
  }
}

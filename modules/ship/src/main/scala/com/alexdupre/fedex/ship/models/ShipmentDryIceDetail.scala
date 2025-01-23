package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the shipment level totals of dry ice data across all packages.
  *
  * @param packageCount
  *   Specifies the package Count for the shipment<br>Example: 10
  * @param totalWeight
  *   Specify total dry ice weight for the shipment.
  */
case class ShipmentDryIceDetail(
    packageCount: Int,
    totalWeight: Option[Weight] = None,
    processingOptions: Option[ShipmentDryIceProcessingOptionsRequested] = None
)

object ShipmentDryIceDetail {

  given Encoder[ShipmentDryIceDetail] = new Encoder.AsObject[ShipmentDryIceDetail] {
    final def encodeObject(o: ShipmentDryIceDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "packageCount"      -> o.packageCount.asJson,
          "totalWeight"       -> o.totalWeight.asJson,
          "processingOptions" -> o.processingOptions.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentDryIceDetail] = (c: HCursor) => {
    for {
      packageCount      <- c.downField("packageCount").as[Int]
      totalWeight       <- c.downField("totalWeight").as[Option[Weight]]
      processingOptions <- c.downField("processingOptions").as[Option[ShipmentDryIceProcessingOptionsRequested]]
    } yield ShipmentDryIceDetail(packageCount, totalWeight, processingOptions)
  }
}

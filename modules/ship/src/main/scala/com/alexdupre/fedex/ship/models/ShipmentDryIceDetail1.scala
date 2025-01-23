package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the descriptive data required for a FedEx shipment containing dangerous materials. This element is required when
  * SpecialServiceType DRY_ICE is selected.<p><i>Note:<ul><li>Dry Ice is a Package level Special Service for Domestic and International
  * shipments.</li><li>Dry Ice must be declared at both Shipment and Package level for International MPS shipments to print the compliance
  * statement on Airway Bill labels.</li></ul></i></p>
  *
  * @param packageCount
  *   Indicates the total number of packages in the shipment that contain dry ice.<br>Example: 12
  */
case class ShipmentDryIceDetail1(
    totalWeight: Option[Weight1] = None,
    packageCount: Option[Int] = None
)

object ShipmentDryIceDetail1 {

  given Encoder[ShipmentDryIceDetail1] = new Encoder.AsObject[ShipmentDryIceDetail1] {
    final def encodeObject(o: ShipmentDryIceDetail1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "totalWeight"  -> o.totalWeight.asJson,
          "packageCount" -> o.packageCount.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentDryIceDetail1] = (c: HCursor) => {
    for {
      totalWeight  <- c.downField("totalWeight").as[Option[Weight1]]
      packageCount <- c.downField("packageCount").as[Option[Int]]
    } yield ShipmentDryIceDetail1(totalWeight, packageCount)
  }
}

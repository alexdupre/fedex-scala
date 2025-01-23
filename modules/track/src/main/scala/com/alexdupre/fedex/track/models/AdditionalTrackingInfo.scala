package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Additional Tracking number information like nickname, notes, shipment attributes etc.
  *
  * @param hasAssociatedShipments
  *   Field which indicates if the current shipment has associated shipments.<br> Example: false
  * @param nickname
  *   Field which holds information about nickname of the shipment. <br> Example: Shipment nickname
  * @param packageIdentifiers
  *   Other related identifiers for this package such as reference numbers, purchase order number etc. Provides identifiers other than
  *   tracking number that can be used in order to track the shipment.
  * @param shipmentNotes
  *   Field which holds customer assigned notes for a package.<br> Example: shipment notes
  */
case class AdditionalTrackingInfo(
    hasAssociatedShipments: Option[Boolean] = None,
    nickname: Option[String] = None,
    packageIdentifiers: Option[Seq[PackageIdentifier]] = None,
    shipmentNotes: Option[String] = None
)

object AdditionalTrackingInfo {

  given Encoder[AdditionalTrackingInfo] = new Encoder.AsObject[AdditionalTrackingInfo] {
    final def encodeObject(o: AdditionalTrackingInfo): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "hasAssociatedShipments" -> o.hasAssociatedShipments.asJson,
          "nickname"               -> o.nickname.asJson,
          "packageIdentifiers"     -> o.packageIdentifiers.asJson,
          "shipmentNotes"          -> o.shipmentNotes.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AdditionalTrackingInfo] = (c: HCursor) => {
    for {
      hasAssociatedShipments <- c.downField("hasAssociatedShipments").as[Option[Boolean]]
      nickname               <- c.downField("nickname").as[Option[String]]
      packageIdentifiers     <- c.downField("packageIdentifiers").as[Option[Seq[PackageIdentifier]]]
      shipmentNotes          <- c.downField("shipmentNotes").as[Option[String]]
    } yield AdditionalTrackingInfo(hasAssociatedShipments, nickname, packageIdentifiers, shipmentNotes)
  }
}

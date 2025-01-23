package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This information describes how and when a online email return label shipment may be accessed for completion.
  *
  * @param accessorDetails
  *   Indicates the details about the users who can access the shipment.
  */
case class PendingShipmentAccessDetail(
    accessorDetails: Option[Seq[PendingShipmentAccessorDetail]] = None
)

object PendingShipmentAccessDetail {

  given Encoder[PendingShipmentAccessDetail] = new Encoder.AsObject[PendingShipmentAccessDetail] {
    final def encodeObject(o: PendingShipmentAccessDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "accessorDetails" -> o.accessorDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PendingShipmentAccessDetail] = (c: HCursor) => {
    for {
      accessorDetails <- c.downField("accessorDetails").as[Option[Seq[PendingShipmentAccessorDetail]]]
    } yield PendingShipmentAccessDetail(accessorDetails)
  }
}

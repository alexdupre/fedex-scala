package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the details of an outbound shipment in order to associate the return shipment to it.
  *
  * @param trackingNumber
  *   This is the tracking number associated with this package.<br>Example: 49XXX0000XXX20032835
  * @param shipDatestamp
  *   This is the ship date for the outbound shipment associated with a return shipment. The format is YYYY-MM-DD.<br> Example: 2019-10-01
  */
case class ReturnAssociationDetail(
    trackingNumber: String,
    shipDatestamp: Option[String] = None
)

object ReturnAssociationDetail {

  given Encoder[ReturnAssociationDetail] = new Encoder.AsObject[ReturnAssociationDetail] {
    final def encodeObject(o: ReturnAssociationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumber" -> o.trackingNumber.asJson,
          "shipDatestamp"  -> o.shipDatestamp.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnAssociationDetail] = (c: HCursor) => {
    for {
      trackingNumber <- c.downField("trackingNumber").as[String]
      shipDatestamp  <- c.downField("shipDatestamp").as[Option[String]]
    } yield ReturnAssociationDetail(trackingNumber, shipDatestamp)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Completed shipment level hazardous commodity information. */
case class CompletedHazardousShipmentDetail(
    hazardousSummaryDetail: Option[CompletedHazardousSummaryDetail] = None,
    adrLicense: Option[AdrLicenseDetail] = None,
    dryIceDetail: Option[ShipmentDryIceDetail] = None
)

object CompletedHazardousShipmentDetail {

  given Encoder[CompletedHazardousShipmentDetail] = new Encoder.AsObject[CompletedHazardousShipmentDetail] {
    final def encodeObject(o: CompletedHazardousShipmentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "hazardousSummaryDetail" -> o.hazardousSummaryDetail.asJson,
          "adrLicense"             -> o.adrLicense.asJson,
          "dryIceDetail"           -> o.dryIceDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedHazardousShipmentDetail] = (c: HCursor) => {
    for {
      hazardousSummaryDetail <- c.downField("hazardousSummaryDetail").as[Option[CompletedHazardousSummaryDetail]]
      adrLicense             <- c.downField("adrLicense").as[Option[AdrLicenseDetail]]
      dryIceDetail           <- c.downField("dryIceDetail").as[Option[ShipmentDryIceDetail]]
    } yield CompletedHazardousShipmentDetail(hazardousSummaryDetail, adrLicense, dryIceDetail)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the details around the ADR license required for shipping. */
case class AdrLicenseDetail(
    licenseOrPermitDetail: Option[LicenseOrPermitDetail] = None
)

object AdrLicenseDetail {

  given Encoder[AdrLicenseDetail] = new Encoder.AsObject[AdrLicenseDetail] {
    final def encodeObject(o: AdrLicenseDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "licenseOrPermitDetail" -> o.licenseOrPermitDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AdrLicenseDetail] = (c: HCursor) => {
    for {
      licenseOrPermitDetail <- c.downField("licenseOrPermitDetail").as[Option[LicenseOrPermitDetail]]
    } yield AdrLicenseDetail(licenseOrPermitDetail)
  }
}

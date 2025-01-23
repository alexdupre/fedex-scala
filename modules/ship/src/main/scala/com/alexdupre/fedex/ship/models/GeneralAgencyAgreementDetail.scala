package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify details to generate general agency agreement detail. */
case class GeneralAgencyAgreementDetail(
    documentFormat: Option[ShippingDocumentFormat] = None
)

object GeneralAgencyAgreementDetail {

  given Encoder[GeneralAgencyAgreementDetail] = new Encoder.AsObject[GeneralAgencyAgreementDetail] {
    final def encodeObject(o: GeneralAgencyAgreementDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "documentFormat" -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[GeneralAgencyAgreementDetail] = (c: HCursor) => {
    for {
      documentFormat <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
    } yield GeneralAgencyAgreementDetail(documentFormat)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The instructions indicating how to print the Certificate of Origin ( e.g. whether or not to include the instructions, image type, etc
  * ...)
  *
  * @param customerImageUsages
  *   Specifies the usage and identification of customer supplied images to be used on this document.
  */
case class CertificateOfOriginDetail(
    customerImageUsages: Option[Seq[CustomerImageUsage]] = None,
    documentFormat: Option[ShippingDocumentFormat] = None
)

object CertificateOfOriginDetail {

  given Encoder[CertificateOfOriginDetail] = new Encoder.AsObject[CertificateOfOriginDetail] {
    final def encodeObject(o: CertificateOfOriginDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customerImageUsages" -> o.customerImageUsages.asJson,
          "documentFormat"      -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CertificateOfOriginDetail] = (c: HCursor) => {
    for {
      customerImageUsages <- c.downField("customerImageUsages").as[Option[Seq[CustomerImageUsage]]]
      documentFormat      <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
    } yield CertificateOfOriginDetail(customerImageUsages, documentFormat)
  }
}

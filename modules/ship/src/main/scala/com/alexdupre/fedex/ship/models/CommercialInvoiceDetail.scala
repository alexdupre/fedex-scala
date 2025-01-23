package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The instructions indicating how to print the Commercial Invoice( e.g. image type) Specifies characteristics of a shipping document to be
  * produced.
  *
  * @param customerImageUsages
  *   Specifies the usage and identification of customer supplied images to be used on this document.
  */
case class CommercialInvoiceDetail(
    customerImageUsages: Option[Seq[CustomerImageUsage]] = None,
    documentFormat: Option[ShippingDocumentFormat] = None
)

object CommercialInvoiceDetail {

  given Encoder[CommercialInvoiceDetail] = new Encoder.AsObject[CommercialInvoiceDetail] {
    final def encodeObject(o: CommercialInvoiceDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customerImageUsages" -> o.customerImageUsages.asJson,
          "documentFormat"      -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CommercialInvoiceDetail] = (c: HCursor) => {
    for {
      customerImageUsages <- c.downField("customerImageUsages").as[Option[Seq[CustomerImageUsage]]]
      documentFormat      <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
    } yield CommercialInvoiceDetail(customerImageUsages, documentFormat)
  }
}

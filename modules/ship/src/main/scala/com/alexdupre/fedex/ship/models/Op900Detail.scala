package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify details to generate the OP-900 document for hazardous material packages.
  *
  * @param customerImageUsages
  *   Specify the use and identification of supplied images to be used on document.
  * @param signatureName
  *   Indicates the name to be printed as signature on the document instead of (or in addition to) a signature image.<br>Example: John Hill
  */
case class Op900Detail(
    customerImageUsages: Option[Seq[CustomerImageUsage]] = None,
    signatureName: Option[String] = None,
    documentFormat: Option[ShippingDocumentFormat] = None
)

object Op900Detail {

  given Encoder[Op900Detail] = new Encoder.AsObject[Op900Detail] {
    final def encodeObject(o: Op900Detail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customerImageUsages" -> o.customerImageUsages.asJson,
          "signatureName"       -> o.signatureName.asJson,
          "documentFormat"      -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Op900Detail] = (c: HCursor) => {
    for {
      customerImageUsages <- c.downField("customerImageUsages").as[Option[Seq[CustomerImageUsage]]]
      signatureName       <- c.downField("signatureName").as[Option[String]]
      documentFormat      <- c.downField("documentFormat").as[Option[ShippingDocumentFormat]]
    } yield Op900Detail(customerImageUsages, signatureName, documentFormat)
  }
}

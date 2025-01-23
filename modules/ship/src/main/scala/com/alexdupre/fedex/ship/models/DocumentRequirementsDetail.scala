package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the document requirements detail.
  *
  * @param requiredDocuments
  *   Indicates the required documents information.<br>Example: ["COMMERCIAL_OR_PRO_FORMA_INVOICE","AIR_WAYBILL"]
  * @param prohibitedDocuments
  *   Indicates the prohibited Documents info.<br>Example: ["CERTIFICATE_OF_ORIGIN "]
  * @param generationDetails
  *   Specifies the generation details.
  */
case class DocumentRequirementsDetail(
    requiredDocuments: Option[Seq[String]] = None,
    prohibitedDocuments: Option[Seq[String]] = None,
    generationDetails: Option[Seq[DocumentGenerationDetail]] = None
)

object DocumentRequirementsDetail {

  given Encoder[DocumentRequirementsDetail] = new Encoder.AsObject[DocumentRequirementsDetail] {
    final def encodeObject(o: DocumentRequirementsDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "requiredDocuments"   -> o.requiredDocuments.asJson,
          "prohibitedDocuments" -> o.prohibitedDocuments.asJson,
          "generationDetails"   -> o.generationDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocumentRequirementsDetail] = (c: HCursor) => {
    for {
      requiredDocuments   <- c.downField("requiredDocuments").as[Option[Seq[String]]]
      prohibitedDocuments <- c.downField("prohibitedDocuments").as[Option[Seq[String]]]
      generationDetails   <- c.downField("generationDetails").as[Option[Seq[DocumentGenerationDetail]]]
    } yield DocumentRequirementsDetail(requiredDocuments, prohibitedDocuments, generationDetails)
  }
}

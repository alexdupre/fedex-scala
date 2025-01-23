package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are completed ETD details when ELECTRONIC_TRADE_DOCUMENTS Special service type is requested
  *
  * @param folderId
  *   Returns the folder id where the documents is uploaded <br> Example: "0b0493e580dc1a1b"
  * @param `type`
  *   Returns the type of the document that is being uploaded <br> Example: "COMMERCIAL_INVOICE"
  * @param uploadDocumentReferenceDetails
  *   Specify the document upload reference details.
  */
case class CompletedEtdDetail(
    folderId: Option[String] = None,
    `type`: Option[String] = None,
    uploadDocumentReferenceDetails: Option[Seq[UploadDocumentReferenceDetail]] = None
)

object CompletedEtdDetail {

  given Encoder[CompletedEtdDetail] = new Encoder.AsObject[CompletedEtdDetail] {
    final def encodeObject(o: CompletedEtdDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "folderId"                       -> o.folderId.asJson,
          "type"                           -> o.`type`.asJson,
          "uploadDocumentReferenceDetails" -> o.uploadDocumentReferenceDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedEtdDetail] = (c: HCursor) => {
    for {
      folderId                       <- c.downField("folderId").as[Option[String]]
      `type`                         <- c.downField("type").as[Option[String]]
      uploadDocumentReferenceDetails <- c.downField("uploadDocumentReferenceDetails").as[Option[Seq[UploadDocumentReferenceDetail]]]
    } yield CompletedEtdDetail(folderId, `type`, uploadDocumentReferenceDetails)
  }
}

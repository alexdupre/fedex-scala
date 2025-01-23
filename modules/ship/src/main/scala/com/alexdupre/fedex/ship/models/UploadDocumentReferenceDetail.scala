package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the document upload reference details.
  *
  * @param documentType
  *   Returns the type of document (if any) specified in the ship shipment request.<br>Example: PRO_FORMA_INVOICE
  * @param documentReference
  *   Specify the reference for the uploaded document.This is for the customer to reference their uploaded docs when they retrieve them.
  *   Could be anything, order number, po number, whatever the customer used to tie the document to something they would use.<br>Note:
  *   Ensure to supply document references in case of Pre-Shipment document upload.</br><br>Example: Reference
  * @param description
  *   Specify additional information about the uploaded document for better understanding.<br>Example: Certificate of Origin is uploaded for
  *   country of manufacturing verification.
  * @param documentId
  *   This is the uploaded document ID value.<br>Example: 090927d680038c61
  */
case class UploadDocumentReferenceDetail(
    documentType: Option[UploadDocumentReferenceDetail.DocumentType] = None,
    documentReference: Option[String] = None,
    description: Option[String] = None,
    documentId: Option[String] = None
)

object UploadDocumentReferenceDetail {
  enum DocumentType {
    case CERTIFICATE_OF_ORIGIN
    case NET_RATE_SHEET
    case COMMERCIAL_INVOICE
    case ETD_LABEL
    case USMCA_CERTIFICATION_OF_ORIGIN
    case OTHER
    case PRO_FORMA_INVOICE
    case USMCA_COMMERCIAL_INVOICE_CERTIFICATION_OF_ORIGIN
  }
  object DocumentType {
    given Encoder[DocumentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DocumentType] = Decoder.decodeString.emapTry(s => scala.util.Try(DocumentType.valueOf(s)))
  }
  given Encoder[UploadDocumentReferenceDetail] = new Encoder.AsObject[UploadDocumentReferenceDetail] {
    final def encodeObject(o: UploadDocumentReferenceDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "documentType"      -> o.documentType.asJson,
          "documentReference" -> o.documentReference.asJson,
          "description"       -> o.description.asJson,
          "documentId"        -> o.documentId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[UploadDocumentReferenceDetail] = (c: HCursor) => {
    for {
      documentType      <- c.downField("documentType").as[Option[DocumentType]]
      documentReference <- c.downField("documentReference").as[Option[String]]
      description       <- c.downField("description").as[Option[String]]
      documentId        <- c.downField("documentId").as[Option[String]]
    } yield UploadDocumentReferenceDetail(documentType, documentReference, description, documentId)
  }
}

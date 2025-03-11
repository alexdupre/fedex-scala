package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the document upload reference details.
  *
  * @param documentType
  *   This is the uploaded document type.
  * @param documentReference
  *   Specify the reference for the uploaded document.<br>Example: Reference
  * @param description
  *   This is the document description of the attached document.<br>Example: PRO FORMA INVOICE
  * @param documentId
  *   This is the uploaded document ID value.<br>Example: 090927d680038c61
  */
case class UploadDocumentReferenceDetail1(
    documentType: Option[UploadDocumentReferenceDetail1.DocumentType] = None,
    documentReference: Option[String] = None,
    description: Option[String] = None,
    documentId: Option[String] = None
)

object UploadDocumentReferenceDetail1 {
  enum DocumentType {
    case CERTIFICATE_OF_ORIGIN
    case COMMERCIAL_INVOICE
    case ETD_LABEL
    case USMCA_CERTIFICATION_OF_ORIGIN
    case NET_RATE_SHEET
    case OTHER
    case PRO_FORMA_INVOICE
    case USMCA_COMMERCIAL_INVOICE_CERTIFICATION_OF_ORIGIN
    case UNKNOWN_DEFAULT
  }
  object DocumentType {
    given Encoder[DocumentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DocumentType] =
      Decoder.decodeString.map(s => scala.util.Try(DocumentType.valueOf(s)).getOrElse(DocumentType.UNKNOWN_DEFAULT))
  }
  given Encoder[UploadDocumentReferenceDetail1] = new Encoder.AsObject[UploadDocumentReferenceDetail1] {
    final def encodeObject(o: UploadDocumentReferenceDetail1): JsonObject = {
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
  given Decoder[UploadDocumentReferenceDetail1] = (c: HCursor) => {
    for {
      documentType      <- c.downField("documentType").as[Option[DocumentType]]
      documentReference <- c.downField("documentReference").as[Option[String]]
      description       <- c.downField("description").as[Option[String]]
      documentId        <- c.downField("documentId").as[Option[String]]
    } yield UploadDocumentReferenceDetail1(documentType, documentReference, description, documentId)
  }
}

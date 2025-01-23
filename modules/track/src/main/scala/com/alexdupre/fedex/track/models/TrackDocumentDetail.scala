package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the tracking document details.
  *
  * @param documentType
  *   Indicate the Tracking Document. <br>Valid values are SIGNATURE_PROOF_OF_DELIVERY, BILL_OF_LADING and
  *   FREIGHT_BILLING_DOCUMENT.<br>Example: SIGNATURE_PROOF_OF_DELIVERY.<br><i>Note: The SPOD information will be presented as a byte array
  *   instead of an image. The byte array is a base64 encoded string, which should be decoded to get the final signature image in PDF or PNG
  *   format</i>
  * @param documentFormat
  *   Specifies the format of tracking document. <br>Valid values are PDF or PNG.<br>The values are key sensitive.<br>Note: documentTypes
  *   BILL_OF_LADING and FREIGHT_BILLING_DOCUMENT does not support PNG.
  */
case class TrackDocumentDetail(
    documentType: String,
    documentFormat: Option[String] = None
)

object TrackDocumentDetail {

  given Encoder[TrackDocumentDetail] = new Encoder.AsObject[TrackDocumentDetail] {
    final def encodeObject(o: TrackDocumentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "documentType"   -> o.documentType.asJson,
          "documentFormat" -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackDocumentDetail] = (c: HCursor) => {
    for {
      documentType   <- c.downField("documentType").as[String]
      documentFormat <- c.downField("documentFormat").as[Option[String]]
    } yield TrackDocumentDetail(documentType, documentFormat)
  }
}

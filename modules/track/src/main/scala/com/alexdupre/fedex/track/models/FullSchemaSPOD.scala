package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Full_Schema_SPOD Model
  *
  * @param trackDocumentDetail
  *   This object specifies the tracking document details such as types of documents, for example, SIGNATURE_PROOF_OF_DELIVERY and also the
  *   format of tracking document. Supported values are PDF and PNG. Default is PDF.
  * @param trackDocumentSpecification
  *   This is the placeholder for document specification details required to identify the shipment being tracked. This includes tracking
  *   information such as tracking qualifier, carrier code, and tracking number.<br>At least one trackDocumentSpecification is required.
  *   Maximum limit is 30.
  */
case class FullSchemaSPOD(
    trackDocumentDetail: TrackDocumentDetail,
    trackDocumentSpecification: Seq[TrackDocumentSpecification]
)

object FullSchemaSPOD {

  given Encoder[FullSchemaSPOD] = new Encoder.AsObject[FullSchemaSPOD] {
    final def encodeObject(o: FullSchemaSPOD): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackDocumentDetail"        -> o.trackDocumentDetail.asJson,
          "trackDocumentSpecification" -> o.trackDocumentSpecification.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaSPOD] = (c: HCursor) => {
    for {
      trackDocumentDetail        <- c.downField("trackDocumentDetail").as[TrackDocumentDetail]
      trackDocumentSpecification <- c.downField("trackDocumentSpecification").as[Seq[TrackDocumentSpecification]]
    } yield FullSchemaSPOD(trackDocumentDetail, trackDocumentSpecification)
  }
}

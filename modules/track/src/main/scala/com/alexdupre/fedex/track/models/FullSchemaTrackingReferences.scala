package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the request elements for Track by alternate reference.<br> For a valid request there are two combinations:<br> 1.) A
  * referenceValue and accountNumber is required OR <br>2.) referenceType & carrierCode, shipdateBegin and shipDateEnd,
  * destinationPostalCode and destinationCountryCode are required.
  *
  * @param includeDetailedScans
  *   Indicates if the detailed scans are being requested or not. If true, the detailed scans will be included in the response returned.
  *   <br>Valid values are True or False.
  */
case class FullSchemaTrackingReferences(
    referencesInformation: ReferenceInformation,
    includeDetailedScans: Option[Boolean] = None
)

object FullSchemaTrackingReferences {

  given Encoder[FullSchemaTrackingReferences] = new Encoder.AsObject[FullSchemaTrackingReferences] {
    final def encodeObject(o: FullSchemaTrackingReferences): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "referencesInformation" -> o.referencesInformation.asJson,
          "includeDetailedScans"  -> o.includeDetailedScans.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaTrackingReferences] = (c: HCursor) => {
    for {
      referencesInformation <- c.downField("referencesInformation").as[ReferenceInformation]
      includeDetailedScans  <- c.downField("includeDetailedScans").as[Option[Boolean]]
    } yield FullSchemaTrackingReferences(referencesInformation, includeDetailedScans)
  }
}

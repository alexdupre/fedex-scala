package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements for Tracking by TCN request type.
  *
  * @param tcnInfo
  *   The information associated with the transportation control number.<br>Only 1 TCN is supported per request.
  * @param includeDetailedScans
  *   Indicates if detailed scans are requested or not. <br/>Valid values are True, or False.
  */
case class FullSchemaTCN(
    tcnInfo: TCNInfo,
    includeDetailedScans: Option[Boolean] = None
)

object FullSchemaTCN {

  given Encoder[FullSchemaTCN] = new Encoder.AsObject[FullSchemaTCN] {
    final def encodeObject(o: FullSchemaTCN): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "tcnInfo"              -> o.tcnInfo.asJson,
          "includeDetailedScans" -> o.includeDetailedScans.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaTCN] = (c: HCursor) => {
    for {
      tcnInfo              <- c.downField("tcnInfo").as[TCNInfo]
      includeDetailedScans <- c.downField("includeDetailedScans").as[Option[Boolean]]
    } yield FullSchemaTCN(tcnInfo, includeDetailedScans)
  }
}

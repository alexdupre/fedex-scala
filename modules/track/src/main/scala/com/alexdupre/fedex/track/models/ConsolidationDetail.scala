package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the consolidation details for packages within a shipment identified as CONSOLIDATED.
  *
  * @param timeStamp
  *   The timestamp for the consolidation. <br> Example: 2020-10-13T03:54:44-06:00
  * @param consolidationID
  *   The identifier for the consolidation. <br> Example: 47936927
  * @param reasonDetail
  *   Specifies the reason details for the consolidation event for a package.
  * @param packageCount
  *   Specifies the package count for the consolidation. <br> Example: 25
  * @param eventType
  *   Specifies the consolidation event type for a package. A package can be ADDED to, REMOVED from, or EXCLUDED from a consolidation. <br>
  *   Example: PACKAGE_ADDED_TO_CONSOLIDATION
  */
case class ConsolidationDetail(
    timeStamp: Option[String] = None,
    consolidationID: Option[String] = None,
    reasonDetail: Option[ReasonDetail] = None,
    packageCount: Option[Int] = None,
    eventType: Option[String] = None
)

object ConsolidationDetail {

  given Encoder[ConsolidationDetail] = new Encoder.AsObject[ConsolidationDetail] {
    final def encodeObject(o: ConsolidationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "timeStamp"       -> o.timeStamp.asJson,
          "consolidationID" -> o.consolidationID.asJson,
          "reasonDetail"    -> o.reasonDetail.asJson,
          "packageCount"    -> o.packageCount.asJson,
          "eventType"       -> o.eventType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ConsolidationDetail] = (c: HCursor) => {
    for {
      timeStamp       <- c.downField("timeStamp").as[Option[String]]
      consolidationID <- c.downField("consolidationID").as[Option[String]]
      reasonDetail    <- c.downField("reasonDetail").as[Option[ReasonDetail]]
      packageCount    <- c.downField("packageCount").as[Option[Int]]
      eventType       <- c.downField("eventType").as[Option[String]]
    } yield ConsolidationDetail(timeStamp, consolidationID, reasonDetail, packageCount, eventType)
  }
}

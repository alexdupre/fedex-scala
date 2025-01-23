package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies Completed Hazardous Summary Detail.
  *
  * @param smallQuantityExceptionPackageCount
  *   Specifies the total number of packages containing hazardous commodities in small exceptions.<br>Example: 10
  */
case class CompletedHazardousSummaryDetail(
    smallQuantityExceptionPackageCount: Option[Int] = None
)

object CompletedHazardousSummaryDetail {

  given Encoder[CompletedHazardousSummaryDetail] = new Encoder.AsObject[CompletedHazardousSummaryDetail] {
    final def encodeObject(o: CompletedHazardousSummaryDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "smallQuantityExceptionPackageCount" -> o.smallQuantityExceptionPackageCount.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedHazardousSummaryDetail] = (c: HCursor) => {
    for {
      smallQuantityExceptionPackageCount <- c.downField("smallQuantityExceptionPackageCount").as[Option[Int]]
    } yield CompletedHazardousSummaryDetail(smallQuantityExceptionPackageCount)
  }
}

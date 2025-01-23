package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Date Range for custom delivery request; only used if type is BETWEEN.
  *
  * @param begins
  *   Indicates the start date.
  * @param ends
  *   Indicates the end date.
  */
case class RetrieveDateRange(
    begins: Option[String] = None,
    ends: Option[String] = None
)

object RetrieveDateRange {

  given Encoder[RetrieveDateRange] = new Encoder.AsObject[RetrieveDateRange] {
    final def encodeObject(o: RetrieveDateRange): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "begins" -> o.begins.asJson,
          "ends"   -> o.ends.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RetrieveDateRange] = (c: HCursor) => {
    for {
      begins <- c.downField("begins").as[Option[String]]
      ends   <- c.downField("ends").as[Option[String]]
    } yield RetrieveDateRange(begins, ends)
  }
}

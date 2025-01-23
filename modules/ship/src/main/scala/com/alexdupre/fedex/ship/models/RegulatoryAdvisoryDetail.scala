package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the regulatory advisory details.
  *
  * @param prohibitions
  *   It is a regulatory probitions.
  */
case class RegulatoryAdvisoryDetail(
    prohibitions: Option[Seq[RegulatoryProhibition]] = None
)

object RegulatoryAdvisoryDetail {

  given Encoder[RegulatoryAdvisoryDetail] = new Encoder.AsObject[RegulatoryAdvisoryDetail] {
    final def encodeObject(o: RegulatoryAdvisoryDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "prohibitions" -> o.prohibitions.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RegulatoryAdvisoryDetail] = (c: HCursor) => {
    for {
      prohibitions <- c.downField("prohibitions").as[Option[Seq[RegulatoryProhibition]]]
    } yield RegulatoryAdvisoryDetail(prohibitions)
  }
}

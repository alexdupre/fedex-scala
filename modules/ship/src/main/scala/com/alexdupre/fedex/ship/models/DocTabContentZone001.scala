package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the doc tab specification for different zones on the label. The specification includes zone number, header and data field to be
  * displayed on the label.
  *
  * @param docTabZoneSpecifications
  *   Indicate the doc tab specifications for the individual doc tab zone on the label.
  */
case class DocTabContentZone001(
    docTabZoneSpecifications: Option[Seq[DocTabZoneSpecification]] = None
)

object DocTabContentZone001 {

  given Encoder[DocTabContentZone001] = new Encoder.AsObject[DocTabContentZone001] {
    final def encodeObject(o: DocTabContentZone001): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "docTabZoneSpecifications" -> o.docTabZoneSpecifications.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocTabContentZone001] = (c: HCursor) => {
    for {
      docTabZoneSpecifications <- c.downField("docTabZoneSpecifications").as[Option[Seq[DocTabZoneSpecification]]]
    } yield DocTabContentZone001(docTabZoneSpecifications)
  }
}

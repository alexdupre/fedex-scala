package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies details of doc tab content.It is only applicable only with imageType as ZPLII.
  *
  * @param docTabContentType
  *   Indicates the content type of the doc tab.
  */
case class DocTabContent(
    docTabContentType: Option[DocTabContent.DocTabContentType] = None,
    zone001: Option[DocTabContentZone001] = None,
    barcoded: Option[DocTabContentBarcoded] = None
)

object DocTabContent {
  enum DocTabContentType {
    case BARCODED
    case CUSTOM
    case MINIMUM
    case STANDARD
    case ZONE001
  }
  object DocTabContentType {
    given Encoder[DocTabContentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DocTabContentType] = Decoder.decodeString.emapTry(s => scala.util.Try(DocTabContentType.valueOf(s)))
  }
  given Encoder[DocTabContent] = new Encoder.AsObject[DocTabContent] {
    final def encodeObject(o: DocTabContent): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "docTabContentType" -> o.docTabContentType.asJson,
          "zone001"           -> o.zone001.asJson,
          "barcoded"          -> o.barcoded.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocTabContent] = (c: HCursor) => {
    for {
      docTabContentType <- c.downField("docTabContentType").as[Option[DocTabContentType]]
      zone001           <- c.downField("zone001").as[Option[DocTabContentZone001]]
      barcoded          <- c.downField("barcoded").as[Option[DocTabContentBarcoded]]
    } yield DocTabContent(docTabContentType, zone001, barcoded)
  }
}

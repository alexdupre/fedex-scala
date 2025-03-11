package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** It is a doc tab content type which is in barcoded format.
  *
  * @param symbology
  *   Indicates the type of barcode symbology used on FedEx documents and labels.
  */
case class DocTabContentBarcoded(
    symbology: Option[DocTabContentBarcoded.Symbology] = None,
    specification: Option[DocTabZoneSpecification] = None
)

object DocTabContentBarcoded {
  enum Symbology {
    case CODABAR
    case CODE128
    case CODE128_WIDEBAR
    case CODE128B
    case CODE128C
    case CODE39
    case CODE93
    case I2OF5
    case MANUAL
    case PDF417
    case POSTNET
    case QR_CODE
    case UCC128
    case UNKNOWN_DEFAULT
  }
  object Symbology {
    given Encoder[Symbology] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Symbology] = Decoder.decodeString.map(s => scala.util.Try(Symbology.valueOf(s)).getOrElse(Symbology.UNKNOWN_DEFAULT))
  }
  given Encoder[DocTabContentBarcoded] = new Encoder.AsObject[DocTabContentBarcoded] {
    final def encodeObject(o: DocTabContentBarcoded): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "symbology"     -> o.symbology.asJson,
          "specification" -> o.specification.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocTabContentBarcoded] = (c: HCursor) => {
    for {
      symbology     <- c.downField("symbology").as[Option[Symbology]]
      specification <- c.downField("specification").as[Option[DocTabZoneSpecification]]
    } yield DocTabContentBarcoded(symbology, specification)
  }
}

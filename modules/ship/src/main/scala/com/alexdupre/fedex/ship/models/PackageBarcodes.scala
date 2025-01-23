package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are package barcode details. Each instance of this data type represents the set of barcodes (of all types) which are associated
  * with a specific package.
  *
  * @param binaryBarcodes
  *   This is binary-style barcodes used for the package identification.
  * @param stringBarcodes
  *   This is string-style barcodes used for package identification.
  */
case class PackageBarcodes(
    binaryBarcodes: Option[Seq[BinaryBarcode]] = None,
    stringBarcodes: Option[Seq[StringBarcode]] = None
)

object PackageBarcodes {

  given Encoder[PackageBarcodes] = new Encoder.AsObject[PackageBarcodes] {
    final def encodeObject(o: PackageBarcodes): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "binaryBarcodes" -> o.binaryBarcodes.asJson,
          "stringBarcodes" -> o.stringBarcodes.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageBarcodes] = (c: HCursor) => {
    for {
      binaryBarcodes <- c.downField("binaryBarcodes").as[Option[Seq[BinaryBarcode]]]
      stringBarcodes <- c.downField("stringBarcodes").as[Option[Seq[StringBarcode]]]
    } yield PackageBarcodes(binaryBarcodes, stringBarcodes)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Package-level data required for labeling and/or movement.
  *
  * @param astraHandlingText
  *   Human-readable text for pre-January 2011 clients.<br>Example: astraHandlingText
  * @param operationalInstructions
  *   These are operational instruction such as Ground Information printed on a given area of the label, one-dimensional barcode with only
  *   x-axis that contains the details of the shipment in encrypted form, COD Return operational instructions data such as COD amount,
  *   SECURED or UNSECURED.
  */
case class PackageOperationalDetail(
    astraHandlingText: Option[String] = None,
    barcodes: Option[PackageBarcodes] = None,
    operationalInstructions: Option[Seq[OperationalInstructions]] = None
)

object PackageOperationalDetail {

  given Encoder[PackageOperationalDetail] = new Encoder.AsObject[PackageOperationalDetail] {
    final def encodeObject(o: PackageOperationalDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "astraHandlingText"       -> o.astraHandlingText.asJson,
          "barcodes"                -> o.barcodes.asJson,
          "operationalInstructions" -> o.operationalInstructions.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageOperationalDetail] = (c: HCursor) => {
    for {
      astraHandlingText       <- c.downField("astraHandlingText").as[Option[String]]
      barcodes                <- c.downField("barcodes").as[Option[PackageBarcodes]]
      operationalInstructions <- c.downField("operationalInstructions").as[Option[Seq[OperationalInstructions]]]
    } yield PackageOperationalDetail(astraHandlingText, barcodes, operationalInstructions)
  }
}

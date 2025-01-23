package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the document generation detail information.
  *
  * @param letterhead
  *   Indicates the letterhead requirement type.<br>Example: OPTIONAL
  * @param electronicSignature
  *   Indicates electronic signature requirement type.<br>Example: OPTIONAL
  * @param minimumCopiesRequired
  *   It is a non-Negative Integer.<br>Example: 3
  * @param `type`
  *   It is an Enterprise Document Type.<br>Example: COMMERCIAL_INVOICE
  */
case class DocumentGenerationDetail(
    letterhead: Option[String] = None,
    electronicSignature: Option[String] = None,
    minimumCopiesRequired: Option[Int] = None,
    `type`: Option[String] = None
)

object DocumentGenerationDetail {

  given Encoder[DocumentGenerationDetail] = new Encoder.AsObject[DocumentGenerationDetail] {
    final def encodeObject(o: DocumentGenerationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "letterhead"            -> o.letterhead.asJson,
          "electronicSignature"   -> o.electronicSignature.asJson,
          "minimumCopiesRequired" -> o.minimumCopiesRequired.asJson,
          "type"                  -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocumentGenerationDetail] = (c: HCursor) => {
    for {
      letterhead            <- c.downField("letterhead").as[Option[String]]
      electronicSignature   <- c.downField("electronicSignature").as[Option[String]]
      minimumCopiesRequired <- c.downField("minimumCopiesRequired").as[Option[Int]]
      `type`                <- c.downField("type").as[Option[String]]
    } yield DocumentGenerationDetail(letterhead, electronicSignature, minimumCopiesRequired, `type`)
  }
}

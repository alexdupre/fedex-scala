package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are return instruction details which will be returned in the transaction to be printed on Return Label.
  *
  * @param customText
  *   Specify additional information (text) to be inserted into the return document.<br>Example: This is additional text printed on Return
  *   Label
  */
case class ReturnInstructionsDetail(
    customText: Option[String] = None,
    documentFormat: Option[ReturnShippingDocumentFormat] = None
)

object ReturnInstructionsDetail {

  given Encoder[ReturnInstructionsDetail] = new Encoder.AsObject[ReturnInstructionsDetail] {
    final def encodeObject(o: ReturnInstructionsDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customText"     -> o.customText.asJson,
          "documentFormat" -> o.documentFormat.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnInstructionsDetail] = (c: HCursor) => {
    for {
      customText     <- c.downField("customText").as[Option[String]]
      documentFormat <- c.downField("documentFormat").as[Option[ReturnShippingDocumentFormat]]
    } yield ReturnInstructionsDetail(customText, documentFormat)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** RegulatoryLabelContentDetail Model
  *
  * @param generationOptions
  *   Specify the regulatory content preference to be displayed on the label.
  * @param `type`
  *   Specify the type of regulatory content to be added on the label.
  */
case class RegulatoryLabelContentDetail(
    generationOptions: Option[RegulatoryLabelContentDetail.GenerationOptions] = None,
    `type`: Option[RegulatoryLabelContentDetail.Type] = None
)

object RegulatoryLabelContentDetail {
  enum GenerationOptions {
    case CONTENT_ON_SHIPPING_LABEL_PREFERRED
    case CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY
    case CONTENT_ON_SHIPPING_LABEL_ONLY
  }
  object GenerationOptions {
    given Encoder[GenerationOptions] = Encoder.encodeString.contramap(_.toString)
    given Decoder[GenerationOptions] = Decoder.decodeString.emapTry(s => scala.util.Try(GenerationOptions.valueOf(s)))
  }

  enum Type {
    case ALCOHOL_SHIPMENT_LABEL
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[RegulatoryLabelContentDetail] = new Encoder.AsObject[RegulatoryLabelContentDetail] {
    final def encodeObject(o: RegulatoryLabelContentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "generationOptions" -> o.generationOptions.asJson,
          "type"              -> o.`type`.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RegulatoryLabelContentDetail] = (c: HCursor) => {
    for {
      generationOptions <- c.downField("generationOptions").as[Option[GenerationOptions]]
      `type`            <- c.downField("type").as[Option[Type]]
    } yield RegulatoryLabelContentDetail(generationOptions, `type`)
  }
}

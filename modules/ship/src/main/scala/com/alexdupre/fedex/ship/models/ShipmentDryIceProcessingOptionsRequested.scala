package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify that dry ice information is only applicable at the shipment level. Package level dry ice information would not apply.
  *
  * @param options
  *   Specifies the options.<br>Example: ["options"]
  */
case class ShipmentDryIceProcessingOptionsRequested(
    options: Option[Seq[String]] = None
)

object ShipmentDryIceProcessingOptionsRequested {

  given Encoder[ShipmentDryIceProcessingOptionsRequested] = new Encoder.AsObject[ShipmentDryIceProcessingOptionsRequested] {
    final def encodeObject(o: ShipmentDryIceProcessingOptionsRequested): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "options" -> o.options.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentDryIceProcessingOptionsRequested] = (c: HCursor) => {
    for {
      options <- c.downField("options").as[Option[Seq[String]]]
    } yield ShipmentDryIceProcessingOptionsRequested(options)
  }
}

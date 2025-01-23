package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to allow the Email Label shipment originator, specify if the Email label shipment completer can make modifications to
  * editable shipment data.
  *
  * @param options
  *   Pending Shipment Processing Option Type<br>Example: ALLOW_MODIFICATIONS
  */
case class PendingShipmentProcessingOptionsRequested(
    options: Option[Seq[PendingShipmentProcessingOptionsRequested.Options]] = None
)

object PendingShipmentProcessingOptionsRequested {
  enum Options {
    case ALLOW_MODIFICATIONS
  }
  object Options {
    given Encoder[Options] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Options] = Decoder.decodeString.emapTry(s => scala.util.Try(Options.valueOf(s)))
  }
  given Encoder[PendingShipmentProcessingOptionsRequested] = new Encoder.AsObject[PendingShipmentProcessingOptionsRequested] {
    final def encodeObject(o: PendingShipmentProcessingOptionsRequested): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "options" -> o.options.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PendingShipmentProcessingOptionsRequested] = (c: HCursor) => {
    for {
      options <- c.downField("options").as[Option[Seq[Options]]]
    } yield PendingShipmentProcessingOptionsRequested(options)
  }
}

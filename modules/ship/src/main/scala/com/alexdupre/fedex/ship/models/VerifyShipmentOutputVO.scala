package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The response elements received when a shipment is created.
  *
  * @param alerts
  *   The alerts received when a Shipment Package Validate is processed. This includes the alert code, alert type, and alert message.
  */
case class VerifyShipmentOutputVO(
    alerts: Option[Seq[Alert]] = None
)

object VerifyShipmentOutputVO {

  given Encoder[VerifyShipmentOutputVO] = new Encoder.AsObject[VerifyShipmentOutputVO] {
    final def encodeObject(o: VerifyShipmentOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "alerts" -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[VerifyShipmentOutputVO] = (c: HCursor) => {
    for {
      alerts <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield VerifyShipmentOutputVO(alerts)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The response elements received when a shipment is cancelled.
  *
  * @param cancelledShipment
  *   Indicates whether the shipment has been cancelled or not. If the value is True, then it indicates that the shipment has been
  *   cancelled.<br>Example: true
  * @param cancelledHistory
  *   Indicates whether the shipment has been deleted from history or not. If the value is True, then it indicates that the shipment has
  *   been deleted.<br>Example: true
  * @param successMessage
  *   The success message generated during cancellation request for Shipment.<br>Example: Success
  * @param alerts
  *   This is a cancellation request alert. This alert includes information such as alert code, alert type, and alert message.
  */
case class CancelShipmentOutputVO(
    cancelledShipment: Option[Boolean] = None,
    cancelledHistory: Option[Boolean] = None,
    successMessage: Option[String] = None,
    alerts: Option[Seq[Alert]] = None
)

object CancelShipmentOutputVO {

  given Encoder[CancelShipmentOutputVO] = new Encoder.AsObject[CancelShipmentOutputVO] {
    final def encodeObject(o: CancelShipmentOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "cancelledShipment" -> o.cancelledShipment.asJson,
          "cancelledHistory"  -> o.cancelledHistory.asJson,
          "successMessage"    -> o.successMessage.asJson,
          "alerts"            -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CancelShipmentOutputVO] = (c: HCursor) => {
    for {
      cancelledShipment <- c.downField("cancelledShipment").as[Option[Boolean]]
      cancelledHistory  <- c.downField("cancelledHistory").as[Option[Boolean]]
      successMessage    <- c.downField("successMessage").as[Option[String]]
      alerts            <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield CancelShipmentOutputVO(cancelledShipment, cancelledHistory, successMessage, alerts)
  }
}

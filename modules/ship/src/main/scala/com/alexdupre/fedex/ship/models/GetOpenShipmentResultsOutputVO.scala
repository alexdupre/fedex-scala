package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** GetOpenShipmentResultsOutputVO Model
  *
  * @param transactionShipments
  *   These are shipping transaction details, such as master tracking number, service type, and ship date and time.
  * @param alerts
  *   object indicate the alert details received in the output.
  */
case class GetOpenShipmentResultsOutputVO(
    transactionShipments: Option[Seq[TransactionShipmentOutputVO]] = None,
    alerts: Option[Seq[Alert]] = None
)

object GetOpenShipmentResultsOutputVO {

  given Encoder[GetOpenShipmentResultsOutputVO] = new Encoder.AsObject[GetOpenShipmentResultsOutputVO] {
    final def encodeObject(o: GetOpenShipmentResultsOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionShipments" -> o.transactionShipments.asJson,
          "alerts"               -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[GetOpenShipmentResultsOutputVO] = (c: HCursor) => {
    for {
      transactionShipments <- c.downField("transactionShipments").as[Option[Seq[TransactionShipmentOutputVO]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield GetOpenShipmentResultsOutputVO(transactionShipments, alerts)
  }
}

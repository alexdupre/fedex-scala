package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the response received when a shipment is requested.
  *
  * @param transactionShipments
  *   These are shipping transaction details, such as master tracking number, service type, and ship date and time.
  * @param alerts
  *   The alerts received when processing a shipment request.
  * @param jobId
  *   Unique identifier for a Job. Example: abc123456
  */
case class ShipShipmentOutputVO(
    transactionShipments: Option[Seq[TransactionShipmentOutputVO]] = None,
    alerts: Option[Seq[Alert]] = None,
    jobId: Option[String] = None
)

object ShipShipmentOutputVO {

  given Encoder[ShipShipmentOutputVO] = new Encoder.AsObject[ShipShipmentOutputVO] {
    final def encodeObject(o: ShipShipmentOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "transactionShipments" -> o.transactionShipments.asJson,
          "alerts"               -> o.alerts.asJson,
          "jobId"                -> o.jobId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipShipmentOutputVO] = (c: HCursor) => {
    for {
      transactionShipments <- c.downField("transactionShipments").as[Option[Seq[TransactionShipmentOutputVO]]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
      jobId                <- c.downField("jobId").as[Option[String]]
    } yield ShipShipmentOutputVO(transactionShipments, alerts, jobId)
  }
}

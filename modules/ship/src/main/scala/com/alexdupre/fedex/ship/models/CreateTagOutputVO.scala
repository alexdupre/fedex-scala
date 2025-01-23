package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the output details when a tag is created.
  *
  * @param masterTrackingNumber
  *   Specifies the Master Tracking Number for the requested shipment.<br>Example: 997338100007320
  * @param serviceType
  *   Specifies the service type for this shipment.<br>Example: GROUND_HOME_DELIVERY<br><a onclick='loadDocReference("servicetypes")'>Click
  *   here to see Service Types</a>
  * @param shipTimestamp
  *   Specifies the shipment date and time. The default timestamp is the current date-time. Format is MMM-dd-yyyy.<br>Example: 2019-10-04
  * @param alerts
  *   Specifies the alerts received when a tag is created. This includes the alert code, alert type, and alert message.
  */
case class CreateTagOutputVO(
    masterTrackingNumber: Option[String] = None,
    serviceType: Option[String] = None,
    shipTimestamp: Option[String] = None,
    completedTagDetail: Option[CompletedTagDetail] = None,
    alerts: Option[Seq[Alert]] = None
)

object CreateTagOutputVO {

  given Encoder[CreateTagOutputVO] = new Encoder.AsObject[CreateTagOutputVO] {
    final def encodeObject(o: CreateTagOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "masterTrackingNumber" -> o.masterTrackingNumber.asJson,
          "serviceType"          -> o.serviceType.asJson,
          "shipTimestamp"        -> o.shipTimestamp.asJson,
          "completedTagDetail"   -> o.completedTagDetail.asJson,
          "alerts"               -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CreateTagOutputVO] = (c: HCursor) => {
    for {
      masterTrackingNumber <- c.downField("masterTrackingNumber").as[Option[String]]
      serviceType          <- c.downField("serviceType").as[Option[String]]
      shipTimestamp        <- c.downField("shipTimestamp").as[Option[String]]
      completedTagDetail   <- c.downField("completedTagDetail").as[Option[CompletedTagDetail]]
      alerts               <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield CreateTagOutputVO(masterTrackingNumber, serviceType, shipTimestamp, completedTagDetail, alerts)
  }
}

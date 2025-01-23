package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** SendNotificationOutputVO Model
  *
  * @param trackingNumberInfo
  *   Tracking number information which uniquely identifies a package. Tracking number information includes tracking number, carrier code
  *   and a unique qualifier.
  * @param destinationAddress
  *   Address where the package was actually delivered. Contrast with destination Address, which is the location to which the package was
  *   intended to be delivered. Addresses may differ due to delivery to a neighbor, hold at FedEx location, etc.
  * @param recipientDetails
  *   Details of the recipient notification events. Possible events are - ON_DELIVERY, ON_ESTIMATED_DELIVERY, ON_EXCEPTION, ON_TENDER.
  * @param alerts
  *   The cxs shipment alerts. This includes the alert type, code, and message.<br>example: TRACKING.DATA.NOTFOUND - Tracking data
  *   unavailable
  */
case class SendNotificationOutputVO(
    trackingNumberInfo: Option[TrackingNumberInfo2] = None,
    destinationAddress: Option[AddressVO] = None,
    recipientDetails: Option[Seq[NotificationEventTypes]] = None,
    alerts: Option[Seq[Alert]] = None
)

object SendNotificationOutputVO {

  given Encoder[SendNotificationOutputVO] = new Encoder.AsObject[SendNotificationOutputVO] {
    final def encodeObject(o: SendNotificationOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "TrackingNumberInfo" -> o.trackingNumberInfo.asJson,
          "destinationAddress" -> o.destinationAddress.asJson,
          "recipientDetails"   -> o.recipientDetails.asJson,
          "alerts"             -> o.alerts.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SendNotificationOutputVO] = (c: HCursor) => {
    for {
      trackingNumberInfo <- c.downField("TrackingNumberInfo").as[Option[TrackingNumberInfo2]]
      destinationAddress <- c.downField("destinationAddress").as[Option[AddressVO]]
      recipientDetails   <- c.downField("recipientDetails").as[Option[Seq[NotificationEventTypes]]]
      alerts             <- c.downField("alerts").as[Option[Seq[Alert]]]
    } yield SendNotificationOutputVO(trackingNumberInfo, destinationAddress, recipientDetails, alerts)
  }
}

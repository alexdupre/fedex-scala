package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Identifies a list of details for Tracking Notifications
  *
  * @param notificationEventTypes
  *   Identifies the events for which the client is requesting notifications. <br>Possible Values are: ON_DELIVERY, ON_ESTIMATED_DELIVERY,
  *   ON_EXCEPTION, ON_TENDER
  * @param role
  *   This is to specify Recipient_Role in the shipment. <br>Possible values - BROKER, OTHER, RECIPIENT, SHIPPER <br> Example: SHIPPER
  * @param currentResultRequestedFlag
  *   If value is 'true' the current tracking results for the shipment along with notification details will be provided to the client. If
  *   'false' only results for the notification request is provided.<br>Defaults to 'false'<br>Example: true
  */
case class TrackingNotification(
    notificationDetail: TrackingNotificationDetail,
    notificationEventTypes: Seq[String],
    role: Option[String] = None,
    currentResultRequestedFlag: Option[Boolean] = None
)

object TrackingNotification {

  given Encoder[TrackingNotification] = new Encoder.AsObject[TrackingNotification] {
    final def encodeObject(o: TrackingNotification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "notificationDetail"         -> o.notificationDetail.asJson,
          "notificationEventTypes"     -> o.notificationEventTypes.asJson,
          "role"                       -> o.role.asJson,
          "currentResultRequestedFlag" -> o.currentResultRequestedFlag.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingNotification] = (c: HCursor) => {
    for {
      notificationDetail         <- c.downField("notificationDetail").as[TrackingNotificationDetail]
      notificationEventTypes     <- c.downField("notificationEventTypes").as[Seq[String]]
      role                       <- c.downField("role").as[Option[String]]
      currentResultRequestedFlag <- c.downField("currentResultRequestedFlag").as[Option[Boolean]]
    } yield TrackingNotification(notificationDetail, notificationEventTypes, role, currentResultRequestedFlag)
  }
}

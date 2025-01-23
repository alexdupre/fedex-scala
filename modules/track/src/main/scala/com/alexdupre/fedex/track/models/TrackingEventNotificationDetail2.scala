package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Tracking Event Notification details.
  *
  * @param trackingNotifications
  *   List of Tracking notifications requested for events like ON_DELIVERY, ON_ESTIMATED_DELIVERY, ON_EXCEPTION, ON_TENDER.
  * @param personalMessage
  *   An optional message which will be included in the body of the email.
  * @param supportHTML
  *   If value is 'true' then html tags are included in the response date. If 'false' they are not provided in the response.
  */
case class TrackingEventNotificationDetail2(
    trackingNotifications: Seq[TrackingNotification],
    personalMessage: Option[String] = None,
    supportHTML: Option[io.circe.Json] = None
)

object TrackingEventNotificationDetail2 {

  given Encoder[TrackingEventNotificationDetail2] = new Encoder.AsObject[TrackingEventNotificationDetail2] {
    final def encodeObject(o: TrackingEventNotificationDetail2): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNotifications" -> o.trackingNotifications.asJson,
          "personalMessage"       -> o.personalMessage.asJson,
          "supportHTML"           -> o.supportHTML.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingEventNotificationDetail2] = (c: HCursor) => {
    for {
      trackingNotifications <- c.downField("trackingNotifications").as[Seq[TrackingNotification]]
      personalMessage       <- c.downField("personalMessage").as[Option[String]]
      supportHTML           <- c.downField("supportHTML").as[Option[io.circe.Json]]
    } yield TrackingEventNotificationDetail2(trackingNotifications, personalMessage, supportHTML)
  }
}

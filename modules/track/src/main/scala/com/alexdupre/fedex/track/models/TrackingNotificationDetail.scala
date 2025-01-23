package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Information about the notification email and the language of the notification requested.
  *
  * @param notificationType
  *   Identifies the format of the notification. <br>valid values are 'HTML' or 'TEXT'.
  */
case class TrackingNotificationDetail(
    localization: Localization,
    emailDetail: EmailDetail,
    notificationType: String
)

object TrackingNotificationDetail {

  given Encoder[TrackingNotificationDetail] = new Encoder.AsObject[TrackingNotificationDetail] {
    final def encodeObject(o: TrackingNotificationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "localization"     -> o.localization.asJson,
          "emailDetail"      -> o.emailDetail.asJson,
          "notificationType" -> o.notificationType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingNotificationDetail] = (c: HCursor) => {
    for {
      localization     <- c.downField("localization").as[Localization]
      emailDetail      <- c.downField("emailDetail").as[EmailDetail]
      notificationType <- c.downField("notificationType").as[String]
    } yield TrackingNotificationDetail(localization, emailDetail, notificationType)
  }
}

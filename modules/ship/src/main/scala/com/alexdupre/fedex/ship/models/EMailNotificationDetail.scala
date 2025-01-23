package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are email disposition details. Provides the type and email addresses of e-mail recipients. If returnedDispositionDetail in
  * labelSpecification is set as true then email will be send with label and documents copy.
  *
  * @param aggregationType
  *   Shipment Notification Aggregation Type.<br>Example:PER_PACKAGE
  * @param emailNotificationRecipients
  *   These are email notification recipient details.
  * @param personalMessage
  *   This is your personal message for the email.<br>Note: The maximum personal message character limit depends on the element
  *   emailNotificationDetail\emailNotificationRecipients\notificationFormatType values:<ul><li>If notificationFormatType is TEXT, then only
  *   120 characters printed on the email</li><li>If notificationFormatType is HTML, then 500 characters printed on the
  *   email</li></ul><br>Example: This is concerning the order 123456 of 26 July 2021 - art no 34324-23 Teddy Bear, brown
  */
case class EMailNotificationDetail(
    aggregationType: Option[EMailNotificationDetail.AggregationType] = None,
    emailNotificationRecipients: Option[Seq[EmailNotificationRecipient]] = None,
    personalMessage: Option[String] = None
)

object EMailNotificationDetail {
  enum AggregationType {
    case PER_PACKAGE
    case PER_SHIPMENT
  }
  object AggregationType {
    given Encoder[AggregationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AggregationType] = Decoder.decodeString.emapTry(s => scala.util.Try(AggregationType.valueOf(s)))
  }
  given Encoder[EMailNotificationDetail] = new Encoder.AsObject[EMailNotificationDetail] {
    final def encodeObject(o: EMailNotificationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "aggregationType"             -> o.aggregationType.asJson,
          "emailNotificationRecipients" -> o.emailNotificationRecipients.asJson,
          "personalMessage"             -> o.personalMessage.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[EMailNotificationDetail] = (c: HCursor) => {
    for {
      aggregationType             <- c.downField("aggregationType").as[Option[AggregationType]]
      emailNotificationRecipients <- c.downField("emailNotificationRecipients").as[Option[Seq[EmailNotificationRecipient]]]
      personalMessage             <- c.downField("personalMessage").as[Option[String]]
    } yield EMailNotificationDetail(aggregationType, emailNotificationRecipients, personalMessage)
  }
}

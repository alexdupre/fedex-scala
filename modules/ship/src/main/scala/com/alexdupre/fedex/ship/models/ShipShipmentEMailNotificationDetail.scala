package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is used to provide eMail notification information..
  *
  * @param aggregationType
  *   Shipment Notification Aggregation Type.<br>Example:PER_PACKAGE
  * @param emailNotificationRecipients
  *   These are email notification recipient details.
  * @param personalMessage
  *   This is your personal message for the email.<br>Note: The maximum personal message character limit depends on the element
  *   notificationFormatType values:<ul><li>If notificationFormatType is TEXT, then only 120 characters printed on the email</li><li>If
  *   notificationFormatType is HTML, then 500 characters printed on the email</li></ul><br>Example: This is concerning the order 123456 of
  *   26 July 2021 - art no 34324-23 Teddy Bear, brown
  */
case class ShipShipmentEMailNotificationDetail(
    aggregationType: Option[ShipShipmentEMailNotificationDetail.AggregationType] = None,
    emailNotificationRecipients: Option[Seq[ShipShipmentEmailNotificationRecipient]] = None,
    personalMessage: Option[String] = None
)

object ShipShipmentEMailNotificationDetail {
  enum AggregationType {
    case PER_PACKAGE
    case PER_SHIPMENT
    case UNKNOWN_DEFAULT
  }
  object AggregationType {
    given Encoder[AggregationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AggregationType] =
      Decoder.decodeString.map(s => scala.util.Try(AggregationType.valueOf(s)).getOrElse(AggregationType.UNKNOWN_DEFAULT))
  }
  given Encoder[ShipShipmentEMailNotificationDetail] = new Encoder.AsObject[ShipShipmentEMailNotificationDetail] {
    final def encodeObject(o: ShipShipmentEMailNotificationDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "aggregationType"             -> o.aggregationType.asJson,
          "emailNotificationRecipients" -> o.emailNotificationRecipients.asJson,
          "personalMessage"             -> o.personalMessage.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipShipmentEMailNotificationDetail] = (c: HCursor) => {
    for {
      aggregationType             <- c.downField("aggregationType").as[Option[AggregationType]]
      emailNotificationRecipients <- c.downField("emailNotificationRecipients").as[Option[Seq[ShipShipmentEmailNotificationRecipient]]]
      personalMessage             <- c.downField("personalMessage").as[Option[String]]
    } yield ShipShipmentEMailNotificationDetail(aggregationType, emailNotificationRecipients, personalMessage)
  }
}

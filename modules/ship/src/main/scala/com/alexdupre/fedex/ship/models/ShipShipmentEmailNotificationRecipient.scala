package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are recipient details for receiving email notification.
  *
  * @param emailNotificationRecipientType
  *   This is the email notification recipient type.<br>Example: SHIPPER
  * @param emailAddress
  *   Specify the recipient email address.<br>Example: xyz@aol.com
  * @param name
  *   Specify the recipient name.<br>Example: Joe Smith
  * @param notificationFormatType
  *   This is the format for the email notification. Either HTML or plain text can be provided.<br>Example: TEXT
  * @param notificationType
  *   Indicate the type of notification that will be sent as an email<br>Example: EMAIL
  * @param locale
  *   These are the locale details for email.<br>Example: en_US, fr_CA, es_MX,.etc.<br><a onclick='loadDocReference("locales")'>click here
  *   to see Locales</a><br>Note: If the locale is left blank or an invalid locale is entered, an error message is returned in response.
  * @param notificationEventType
  *   These are to specify the notification event types.<br> Example: [ON_PICKUP_DRIVER_ARRIVED, ON_SHIPMENT]<br><a
  *   onclick='loadDocReference("notificationeventtypes")'>Click here for more information on Notification Event Types.</a>
  */
case class ShipShipmentEmailNotificationRecipient(
    emailNotificationRecipientType: ShipShipmentEmailNotificationRecipient.EmailNotificationRecipientType,
    emailAddress: String,
    name: Option[String] = None,
    notificationFormatType: Option[ShipShipmentEmailNotificationRecipient.NotificationFormatType] = None,
    notificationType: Option[ShipShipmentEmailNotificationRecipient.NotificationType] = None,
    locale: Option[String] = None,
    notificationEventType: Option[Seq[ShipShipmentEmailNotificationRecipient.NotificationEventType]] = None
)

object ShipShipmentEmailNotificationRecipient {
  enum NotificationEventType {
    case ON_DELIVERY
    case ON_EXCEPTION
    case ON_SHIPMENT
    case ON_TENDER
    case ON_ESTIMATED_DELIVERY
    case ON_PICKUP_DRIVER_ARRIVED
    case ON_PICKUP_DRIVER_ASSIGNED
    case ON_PICKUP_DRIVER_DEPARTED
    case ON_PICKUP_DRIVER_EN_ROUTE
    case UNKNOWN_DEFAULT
  }
  object NotificationEventType {
    given Encoder[NotificationEventType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[NotificationEventType] =
      Decoder.decodeString.map(s => scala.util.Try(NotificationEventType.valueOf(s)).getOrElse(NotificationEventType.UNKNOWN_DEFAULT))
  }

  enum NotificationFormatType {
    case HTML
    case TEXT
    case UNKNOWN_DEFAULT
  }
  object NotificationFormatType {
    given Encoder[NotificationFormatType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[NotificationFormatType] =
      Decoder.decodeString.map(s => scala.util.Try(NotificationFormatType.valueOf(s)).getOrElse(NotificationFormatType.UNKNOWN_DEFAULT))
  }

  enum EmailNotificationRecipientType {
    case BROKER
    case OTHER
    case RECIPIENT
    case SHIPPER
    case THIRD_PARTY
    case UNKNOWN_DEFAULT
  }
  object EmailNotificationRecipientType {
    given Encoder[EmailNotificationRecipientType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[EmailNotificationRecipientType] = Decoder.decodeString.map(s =>
      scala.util.Try(EmailNotificationRecipientType.valueOf(s)).getOrElse(EmailNotificationRecipientType.UNKNOWN_DEFAULT)
    )
  }

  enum NotificationType {
    case EMAIL
    case UNKNOWN_DEFAULT
  }
  object NotificationType {
    given Encoder[NotificationType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[NotificationType] =
      Decoder.decodeString.map(s => scala.util.Try(NotificationType.valueOf(s)).getOrElse(NotificationType.UNKNOWN_DEFAULT))
  }
  given Encoder[ShipShipmentEmailNotificationRecipient] = new Encoder.AsObject[ShipShipmentEmailNotificationRecipient] {
    final def encodeObject(o: ShipShipmentEmailNotificationRecipient): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "emailNotificationRecipientType" -> o.emailNotificationRecipientType.asJson,
          "emailAddress"                   -> o.emailAddress.asJson,
          "name"                           -> o.name.asJson,
          "notificationFormatType"         -> o.notificationFormatType.asJson,
          "notificationType"               -> o.notificationType.asJson,
          "locale"                         -> o.locale.asJson,
          "notificationEventType"          -> o.notificationEventType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipShipmentEmailNotificationRecipient] = (c: HCursor) => {
    for {
      emailNotificationRecipientType <- c.downField("emailNotificationRecipientType").as[EmailNotificationRecipientType]
      emailAddress                   <- c.downField("emailAddress").as[String]
      name                           <- c.downField("name").as[Option[String]]
      notificationFormatType         <- c.downField("notificationFormatType").as[Option[NotificationFormatType]]
      notificationType               <- c.downField("notificationType").as[Option[NotificationType]]
      locale                         <- c.downField("locale").as[Option[String]]
      notificationEventType          <- c.downField("notificationEventType").as[Option[Seq[NotificationEventType]]]
    } yield ShipShipmentEmailNotificationRecipient(
      emailNotificationRecipientType,
      emailAddress,
      name,
      notificationFormatType,
      notificationType,
      locale,
      notificationEventType
    )
  }
}

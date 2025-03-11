package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** ShippingDocumentEmailRecipient Model
  *
  * @param recipientType
  *   Specify the email notification recipient type.
  * @param emailAddress
  *   Specifies the email address.<br> Example: email@fedex.com
  */
case class ShippingDocumentEmailRecipient(
    recipientType: ShippingDocumentEmailRecipient.RecipientType,
    emailAddress: Option[String] = None
)

object ShippingDocumentEmailRecipient {
  enum RecipientType {
    case BROKER
    case OTHER
    case RECIPIENT
    case SHIPPER
    case THIRD_PARTY
    case OTHER1
    case OTHER2
    case UNKNOWN_DEFAULT
  }
  object RecipientType {
    given Encoder[RecipientType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RecipientType] =
      Decoder.decodeString.map(s => scala.util.Try(RecipientType.valueOf(s)).getOrElse(RecipientType.UNKNOWN_DEFAULT))
  }
  given Encoder[ShippingDocumentEmailRecipient] = new Encoder.AsObject[ShippingDocumentEmailRecipient] {
    final def encodeObject(o: ShippingDocumentEmailRecipient): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "recipientType" -> o.recipientType.asJson,
          "emailAddress"  -> o.emailAddress.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShippingDocumentEmailRecipient] = (c: HCursor) => {
    for {
      recipientType <- c.downField("recipientType").as[RecipientType]
      emailAddress  <- c.downField("emailAddress").as[Option[String]]
    } yield ShippingDocumentEmailRecipient(recipientType, emailAddress)
  }
}

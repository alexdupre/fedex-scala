package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are specific information about the pending email label.<br>Required when PendingShipmentType is EMAIL.<br>Not applicable for
  * CreateTag.
  *
  * @param recipients
  *   This is Email label recipient email address, shipment role, & language locale details. Atleast one entry must be specified.
  * @param message
  *   Specifies an optional personalized message to be included in the email to the email label recipient.<br>Example: YOUR OPTIONAL MESSAGE
  */
case class EmailLabelDetail(
    recipients: Option[Seq[EmailRecipient]] = None,
    message: Option[String] = None
)

object EmailLabelDetail {

  given Encoder[EmailLabelDetail] = new Encoder.AsObject[EmailLabelDetail] {
    final def encodeObject(o: EmailLabelDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "recipients" -> o.recipients.asJson,
          "message"    -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[EmailLabelDetail] = (c: HCursor) => {
    for {
      recipients <- c.downField("recipients").as[Option[Seq[EmailRecipient]]]
      message    <- c.downField("message").as[Option[String]]
    } yield EmailLabelDetail(recipients, message)
  }
}

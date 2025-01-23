package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the Delivery On Invoice Acceptance detail. Recipient is required for Delivery On Invoice Special service.
  *
  * @param recipient
  *   The descriptive data for the recipient of the shipment and the physical location for the shipment destination.
  */
case class DeliveryOnInvoiceAcceptanceDetail(
    recipient: Option[RecipientsParty] = None
)

object DeliveryOnInvoiceAcceptanceDetail {

  given Encoder[DeliveryOnInvoiceAcceptanceDetail] = new Encoder.AsObject[DeliveryOnInvoiceAcceptanceDetail] {
    final def encodeObject(o: DeliveryOnInvoiceAcceptanceDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "recipient" -> o.recipient.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DeliveryOnInvoiceAcceptanceDetail] = (c: HCursor) => {
    for {
      recipient <- c.downField("recipient").as[Option[RecipientsParty]]
    } yield DeliveryOnInvoiceAcceptanceDetail(recipient)
  }
}

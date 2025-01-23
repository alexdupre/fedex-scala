package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is a payment type, basically indicates who is the payor for the shipment.Conditional required for International Shipments
  *
  * @param paymentType
  *   Indicates who and how the shipment will be paid for.Required for Express and Ground.<br>Example: SENDER
  */
case class Payment1(
    payor: Option[Payor1] = None,
    billingDetails: Option[BillingDetails] = None,
    paymentType: Option[Payment1.PaymentType] = None
)

object Payment1 {
  enum PaymentType {
    case SENDER
    case RECIPIENT
    case THIRD_PARTY
    case COLLECT
  }
  object PaymentType {
    given Encoder[PaymentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[PaymentType] = Decoder.decodeString.emapTry(s => scala.util.Try(PaymentType.valueOf(s)))
  }
  given Encoder[Payment1] = new Encoder.AsObject[Payment1] {
    final def encodeObject(o: Payment1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "payor"          -> o.payor.asJson,
          "billingDetails" -> o.billingDetails.asJson,
          "paymentType"    -> o.paymentType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Payment1] = (c: HCursor) => {
    for {
      payor          <- c.downField("payor").as[Option[Payor1]]
      billingDetails <- c.downField("billingDetails").as[Option[BillingDetails]]
      paymentType    <- c.downField("paymentType").as[Option[PaymentType]]
    } yield Payment1(payor, billingDetails, paymentType)
  }
}

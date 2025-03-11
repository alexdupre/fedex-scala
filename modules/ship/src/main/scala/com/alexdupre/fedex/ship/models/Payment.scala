package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the payment details specifying the method and means of payment to FedEx for providing shipping services.
  *
  * @param paymentType
  *   Indicates who and how the shipment will be paid for.Required for Express and Ground.<br>Example: SENDER
  */
case class Payment(
    paymentType: Payment.PaymentType,
    payor: Option[Payor] = None
)

object Payment {
  enum PaymentType {
    case SENDER
    case RECIPIENT
    case THIRD_PARTY
    case COLLECT
    case UNKNOWN_DEFAULT
  }
  object PaymentType {
    given Encoder[PaymentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[PaymentType] =
      Decoder.decodeString.map(s => scala.util.Try(PaymentType.valueOf(s)).getOrElse(PaymentType.UNKNOWN_DEFAULT))
  }
  given Encoder[Payment] = new Encoder.AsObject[Payment] {
    final def encodeObject(o: Payment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "paymentType" -> o.paymentType.asJson,
          "payor"       -> o.payor.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Payment] = (c: HCursor) => {
    for {
      paymentType <- c.downField("paymentType").as[PaymentType]
      payor       <- c.downField("payor").as[Option[Payor]]
    } yield Payment(paymentType, payor)
  }
}

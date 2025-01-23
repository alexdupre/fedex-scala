package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the api alerts.
  *
  * @param code
  *   Specifies the api alert code.<br>Example: RECIPIENTCONTACT.PHONENUMBER.INVALID
  * @param message
  *   Specifies the api alert message.<br>Example: Recipient’s phone number format is not matching with recipient's country code; hence,
  *   recipient will not receive Convenient Delivery Options. Moving forward, please provide valid mobile phone number.
  */
case class Alert3P(
    code: Option[String] = None,
    message: Option[String] = None
)

object Alert3P {

  given Encoder[Alert3P] = new Encoder.AsObject[Alert3P] {
    final def encodeObject(o: Alert3P): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"    -> o.code.asJson,
          "message" -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Alert3P] = (c: HCursor) => {
    for {
      code    <- c.downField("code").as[Option[String]]
      message <- c.downField("message").as[Option[String]]
    } yield Alert3P(code, message)
  }
}

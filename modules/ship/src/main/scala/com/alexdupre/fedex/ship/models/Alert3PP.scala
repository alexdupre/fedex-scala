package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the api alerts.
  *
  * @param code
  *   Specifies the api alert code.<br>Example: RECIPIENTCONTACT.PHONENUMBER.NOTSUPPORTED
  * @param message
  *   Convenient Delivery Options will not be provided with recipient’s landline number. Moving forward, please provide valid mobile phone
  *   number.
  */
case class Alert3PP(
    code: Option[String] = None,
    message: Option[String] = None
)

object Alert3PP {

  given Encoder[Alert3PP] = new Encoder.AsObject[Alert3PP] {
    final def encodeObject(o: Alert3PP): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"    -> o.code.asJson,
          "message" -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Alert3PP] = (c: HCursor) => {
    for {
      code    <- c.downField("code").as[Option[String]]
      message <- c.downField("message").as[Option[String]]
    } yield Alert3PP(code, message)
  }
}

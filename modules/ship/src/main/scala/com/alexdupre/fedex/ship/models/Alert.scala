package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are alert details received in the response.
  *
  * @param code
  *   Specifies the api alert code.<br>Example: SHIPMENT.VALIDATION.SUCCESS
  * @param alertType
  *   Specifies the api alert type.
  * @param message
  *   Specifies the api alert message.<br>Example: Shipment validated successfully. No errors found.
  */
case class Alert(
    code: Option[String] = None,
    alertType: Option[Alert.AlertType] = None,
    message: Option[String] = None
)

object Alert {
  enum AlertType {
    case NOTE
    case WARNING
    case UNKNOWN_DEFAULT
  }
  object AlertType {
    given Encoder[AlertType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AlertType] = Decoder.decodeString.map(s => scala.util.Try(AlertType.valueOf(s)).getOrElse(AlertType.UNKNOWN_DEFAULT))
  }
  given Encoder[Alert] = new Encoder.AsObject[Alert] {
    final def encodeObject(o: Alert): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "code"      -> o.code.asJson,
          "alertType" -> o.alertType.asJson,
          "message"   -> o.message.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Alert] = (c: HCursor) => {
    for {
      code      <- c.downField("code").as[Option[String]]
      alertType <- c.downField("alertType").as[Option[AlertType]]
      message   <- c.downField("message").as[Option[String]]
    } yield Alert(code, alertType, message)
  }
}

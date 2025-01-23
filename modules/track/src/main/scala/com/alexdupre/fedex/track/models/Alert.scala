package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the api alerts.
  *
  * @param code
  *   Specifies the api alert code.
  * @param alertType
  *   Specifies the api alert type.
  * @param message
  *   Specifies the api alert message.
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
  }
  object AlertType {
    given Encoder[AlertType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AlertType] = Decoder.decodeString.emapTry(s => scala.util.Try(AlertType.valueOf(s)))
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

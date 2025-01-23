package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the Email Notification Details.
  *
  * @param emailAddress
  *   Specifies email address on which user wants to get notified for various registered events.<br>Example: p1@fedex.com
  * @param name
  *   Specifies the name of the notification recipient.<br>Example: Sam Smith
  */
case class EmailDetail(
    emailAddress: String,
    name: Option[String] = None
)

object EmailDetail {

  given Encoder[EmailDetail] = new Encoder.AsObject[EmailDetail] {
    final def encodeObject(o: EmailDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "emailAddress" -> o.emailAddress.asJson,
          "name"         -> o.name.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[EmailDetail] = (c: HCursor) => {
    for {
      emailAddress <- c.downField("emailAddress").as[String]
      name         <- c.downField("name").as[Option[String]]
    } yield EmailDetail(emailAddress, name)
  }
}

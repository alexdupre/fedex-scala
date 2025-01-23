package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies details for how to access the pending email return label.
  *
  * @param password
  *   Specifies the accessor password.<br>Example: password
  * @param role
  *   Specifies the accessor role.<br>Example: role
  * @param emailLabelUrl
  *   Specifies the URL for the email label.<br>Example: emailLabelUrl
  * @param userId
  *   Specifies the accessor User ID.<br>Example: userId
  */
case class PendingShipmentAccessorDetail(
    password: Option[String] = None,
    role: Option[String] = None,
    emailLabelUrl: Option[String] = None,
    userId: Option[String] = None
)

object PendingShipmentAccessorDetail {

  given Encoder[PendingShipmentAccessorDetail] = new Encoder.AsObject[PendingShipmentAccessorDetail] {
    final def encodeObject(o: PendingShipmentAccessorDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "password"      -> o.password.asJson,
          "role"          -> o.role.asJson,
          "emailLabelUrl" -> o.emailLabelUrl.asJson,
          "userId"        -> o.userId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PendingShipmentAccessorDetail] = (c: HCursor) => {
    for {
      password      <- c.downField("password").as[Option[String]]
      role          <- c.downField("role").as[Option[String]]
      emailLabelUrl <- c.downField("emailLabelUrl").as[Option[String]]
      userId        <- c.downField("userId").as[Option[String]]
    } yield PendingShipmentAccessorDetail(password, role, emailLabelUrl, userId)
  }
}

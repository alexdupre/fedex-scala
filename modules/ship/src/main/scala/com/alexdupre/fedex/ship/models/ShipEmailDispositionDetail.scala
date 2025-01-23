package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are email disposition details. Provides the type and email addresses of e-mail recipients. If returnedDispositionDetail in
  * labelSpecification is set as true then email will be send with label and documents copy.
  *
  * @param emailAddress
  *   This is email Address.<br>Example: xxxx@xxx.com
  * @param `type`
  *   Specify the email status.<br>Example: EMAILED
  * @param recipientType
  *   Specify the recipient Type.<br>Example: SHIPPER/BROKER
  */
case class ShipEmailDispositionDetail(
    emailAddress: Option[String] = None,
    `type`: Option[String] = None,
    recipientType: Option[String] = None
)

object ShipEmailDispositionDetail {

  given Encoder[ShipEmailDispositionDetail] = new Encoder.AsObject[ShipEmailDispositionDetail] {
    final def encodeObject(o: ShipEmailDispositionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "emailAddress"  -> o.emailAddress.asJson,
          "type"          -> o.`type`.asJson,
          "recipientType" -> o.recipientType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipEmailDispositionDetail] = (c: HCursor) => {
    for {
      emailAddress  <- c.downField("emailAddress").as[Option[String]]
      `type`        <- c.downField("type").as[Option[String]]
      recipientType <- c.downField("recipientType").as[Option[String]]
    } yield ShipEmailDispositionDetail(emailAddress, `type`, recipientType)
  }
}

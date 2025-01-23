package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is a Return Merchant Authorization (RMA) for the return shipment.<br> Reason for the requirement is mandatory.
  *
  * @param reason
  *   Specify the reason for the return.<br> Note: There is no validation for reason. Recommended length is 60 alpha-numeric
  *   characters<br>Example: Wrong color or size.
  */
case class ReturnMerchandiseAuthorization(
    reason: Option[String] = None
)

object ReturnMerchandiseAuthorization {

  given Encoder[ReturnMerchandiseAuthorization] = new Encoder.AsObject[ReturnMerchandiseAuthorization] {
    final def encodeObject(o: ReturnMerchandiseAuthorization): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "reason" -> o.reason.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnMerchandiseAuthorization] = (c: HCursor) => {
    for {
      reason <- c.downField("reason").as[Option[String]]
    } yield ReturnMerchandiseAuthorization(reason)
  }
}

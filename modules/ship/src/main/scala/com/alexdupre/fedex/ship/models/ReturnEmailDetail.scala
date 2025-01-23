package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are email details for the return shipment.
  *
  * @param merchantPhoneNumber
  *   This is the merchant phone number and required for Email Return Labels.<br>Example: 19012635656
  * @param allowedSpecialService
  *   Indicate the allowed (merchant-authorized) special services which may be selected when the subsequent shipment is created.<br>Only
  *   services represented in EmailLabelAllowedSpecialServiceType will be controlled by this list.
  */
case class ReturnEmailDetail(
    merchantPhoneNumber: String,
    allowedSpecialService: Seq[ReturnEmailDetail.AllowedSpecialService]
)

object ReturnEmailDetail {
  enum AllowedSpecialService {
    case SATURDAY_DELIVERY
    case SATURDAY_PICKUP
  }
  object AllowedSpecialService {
    given Encoder[AllowedSpecialService] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AllowedSpecialService] = Decoder.decodeString.emapTry(s => scala.util.Try(AllowedSpecialService.valueOf(s)))
  }
  given Encoder[ReturnEmailDetail] = new Encoder.AsObject[ReturnEmailDetail] {
    final def encodeObject(o: ReturnEmailDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "merchantPhoneNumber"   -> o.merchantPhoneNumber.asJson,
          "allowedSpecialService" -> o.allowedSpecialService.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnEmailDetail] = (c: HCursor) => {
    for {
      merchantPhoneNumber   <- c.downField("merchantPhoneNumber").as[String]
      allowedSpecialService <- c.downField("allowedSpecialService").as[Seq[AllowedSpecialService]]
    } yield ReturnEmailDetail(merchantPhoneNumber, allowedSpecialService)
  }
}

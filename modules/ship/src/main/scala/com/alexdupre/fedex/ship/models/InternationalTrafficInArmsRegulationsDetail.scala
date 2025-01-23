package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are International Traffic In Arms Regulations shipment service details.
  *
  * @param licenseOrExemptionNumber
  *   The export or license number for the ITAR shipment.<br>Minimum length is 5 characters.<br>Maximum length is 21 characters.<br>Example:
  *   9871234
  */
case class InternationalTrafficInArmsRegulationsDetail(
    licenseOrExemptionNumber: String
)

object InternationalTrafficInArmsRegulationsDetail {

  given Encoder[InternationalTrafficInArmsRegulationsDetail] = new Encoder.AsObject[InternationalTrafficInArmsRegulationsDetail] {
    final def encodeObject(o: InternationalTrafficInArmsRegulationsDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "licenseOrExemptionNumber" -> o.licenseOrExemptionNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[InternationalTrafficInArmsRegulationsDetail] = (c: HCursor) => {
    for {
      licenseOrExemptionNumber <- c.downField("licenseOrExemptionNumber").as[String]
    } yield InternationalTrafficInArmsRegulationsDetail(licenseOrExemptionNumber)
  }
}

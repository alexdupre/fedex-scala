package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This contains the ADR license information, which identifies the license number, the effective date and the expiration date under which
  * the customer is allowed to ship.
  *
  * @param number
  *   Specifies license or permit detail number.<br>Example: 12345
  * @param effectiveDate
  *   Specifies the effective date of the license.<br>The format is [YYYY-MM-DD].<br>Example: 2019-08-09
  * @param expirationDate
  *   Specifies the expiration date of the license.<br>The format is [YYYY-MM-DD].<br>Example: 2019-04-09
  */
case class LicenseOrPermitDetail(
    number: Option[String] = None,
    effectiveDate: Option[String] = None,
    expirationDate: Option[String] = None
)

object LicenseOrPermitDetail {

  given Encoder[LicenseOrPermitDetail] = new Encoder.AsObject[LicenseOrPermitDetail] {
    final def encodeObject(o: LicenseOrPermitDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "number"         -> o.number.asJson,
          "effectiveDate"  -> o.effectiveDate.asJson,
          "expirationDate" -> o.expirationDate.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[LicenseOrPermitDetail] = (c: HCursor) => {
    for {
      number         <- c.downField("number").as[Option[String]]
      effectiveDate  <- c.downField("effectiveDate").as[Option[String]]
      expirationDate <- c.downField("expirationDate").as[Option[String]]
    } yield LicenseOrPermitDetail(number, effectiveDate, expirationDate)
  }
}

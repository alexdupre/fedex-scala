package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** TaxpayerIdentification Model
  *
  * @param number
  *   Specify tax ID number. Maximum length is 18. <br>Example: 123567
  * @param tinType
  *   Identifies the type of Tax Identification Number in Shipment processing.<br>Example: FEDERAL
  * @param usage
  *   Identifies the usage of Tax Identification Number in Shipment processing.<br>Example: usage
  * @param effectiveDate
  *   Effective Date. FORMAT[YYYY-MM-DD] <br>Example: 2024-06-13
  * @param expirationDate
  *   Expiration Date. FORMAT[YYYY-MM-DD]<br>Example: 2024-06-13
  */
case class TaxpayerIdentification(
    number: String,
    tinType: TaxpayerIdentification.TinType,
    usage: Option[String] = None,
    effectiveDate: Option[java.time.LocalDate] = None,
    expirationDate: Option[java.time.LocalDate] = None
)

object TaxpayerIdentification {
  enum TinType {
    case PERSONAL_NATIONAL
    case PERSONAL_STATE
    case FEDERAL
    case BUSINESS_NATIONAL
    case BUSINESS_STATE
    case BUSINESS_UNION
  }
  object TinType {
    given Encoder[TinType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[TinType] = Decoder.decodeString.emapTry(s => scala.util.Try(TinType.valueOf(s)))
  }
  given Encoder[TaxpayerIdentification] = new Encoder.AsObject[TaxpayerIdentification] {
    final def encodeObject(o: TaxpayerIdentification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "number"         -> o.number.asJson,
          "tinType"        -> o.tinType.asJson,
          "usage"          -> o.usage.asJson,
          "effectiveDate"  -> o.effectiveDate.asJson,
          "expirationDate" -> o.expirationDate.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TaxpayerIdentification] = (c: HCursor) => {
    for {
      number         <- c.downField("number").as[String]
      tinType        <- c.downField("tinType").as[TinType]
      usage          <- c.downField("usage").as[Option[String]]
      effectiveDate  <- c.downField("effectiveDate").as[Option[java.time.LocalDate]]
      expirationDate <- c.downField("expirationDate").as[Option[java.time.LocalDate]]
    } yield TaxpayerIdentification(number, tinType, usage, effectiveDate, expirationDate)
  }
}

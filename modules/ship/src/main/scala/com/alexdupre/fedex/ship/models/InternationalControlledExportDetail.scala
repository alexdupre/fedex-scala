package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify International Controlled Export shipment Details.<br>Note: licenseOrPermitExpirationDate and
  * licenseOrPermitNumber are not required when type is WAREHOUSE_WITHDRAWAL.
  *
  * @param `type`
  *   International Controlled Export Type<br>Example: WAREHOUSE_WITHDRAWAL
  * @param licenseOrPermitExpirationDate
  *   Indicate the expiration date for the license or permit. The format is YYYY-MM-DD.<br>Example: "2019-12-03"
  * @param licenseOrPermitNumber
  *   Indicate License Or Permit Number for the commodity being exported.<br>Example: 11
  * @param entryNumber
  *   Indicate Entry Number for the export.<br>Example: 125
  * @param foreignTradeZoneCode
  *   Indicate the Foreign Trade Zone Code.<br>Example: US
  */
case class InternationalControlledExportDetail(
    `type`: InternationalControlledExportDetail.Type,
    licenseOrPermitExpirationDate: Option[String] = None,
    licenseOrPermitNumber: Option[String] = None,
    entryNumber: Option[String] = None,
    foreignTradeZoneCode: Option[String] = None
)

object InternationalControlledExportDetail {
  enum Type {
    case DEA_036
    case DEA_236
    case DSP_05
    case DSP_61
    case DSP_73
    case DSP_85
    case DSP_LICENSE_AGREEMENT
    case WAREHOUSE_WITHDRAWAL
    case FROM_FOREIGN_TRADE_ZONE
    case DEA_486
    case DSP_94
  }
  object Type {
    given Encoder[Type] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Type] = Decoder.decodeString.emapTry(s => scala.util.Try(Type.valueOf(s)))
  }
  given Encoder[InternationalControlledExportDetail] = new Encoder.AsObject[InternationalControlledExportDetail] {
    final def encodeObject(o: InternationalControlledExportDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "type"                          -> o.`type`.asJson,
          "licenseOrPermitExpirationDate" -> o.licenseOrPermitExpirationDate.asJson,
          "licenseOrPermitNumber"         -> o.licenseOrPermitNumber.asJson,
          "entryNumber"                   -> o.entryNumber.asJson,
          "foreignTradeZoneCode"          -> o.foreignTradeZoneCode.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[InternationalControlledExportDetail] = (c: HCursor) => {
    for {
      `type`                        <- c.downField("type").as[Type]
      licenseOrPermitExpirationDate <- c.downField("licenseOrPermitExpirationDate").as[Option[String]]
      licenseOrPermitNumber         <- c.downField("licenseOrPermitNumber").as[Option[String]]
      entryNumber                   <- c.downField("entryNumber").as[Option[String]]
      foreignTradeZoneCode          <- c.downField("foreignTradeZoneCode").as[Option[String]]
    } yield InternationalControlledExportDetail(
      `type`,
      licenseOrPermitExpirationDate,
      licenseOrPermitNumber,
      entryNumber,
      foreignTradeZoneCode
    )
  }
}

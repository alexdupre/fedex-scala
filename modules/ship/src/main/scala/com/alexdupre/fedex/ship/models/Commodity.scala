package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Commodity Model
  *
  * @param description
  *   Required<br>ScrewsMaximum allowed 450 characters.<br>Example: description
  * @param unitPrice
  *   This is the unit price.
  * @param additionalMeasures
  *   This object contains additional quantitative information other than weight and quantity to calculate duties and taxes.
  * @param numberOfPieces
  *   Indicate the number of pieces associated with the commodity. The value can neither be negative nor exceed 9,999.<br>Example: 12
  * @param quantity
  *   This is the units quantity (using quantityUnits as the unit of measure) per commodity. This is used to estimate duties and taxes.<br>
  *   Example: 125
  * @param quantityUnits
  *   This is the unit of measure for the units quantity. This is used to estimate duties and taxes.<br>Example: EA<br><a
  *   onclick='loadDocReference("harmonizedsystemcodeunitofmeasure-table1")'>click here to see Commodity Unit Measures</a>
  * @param customsValue
  *   This customs value is applicable for all items(or units) under the specified commodity.
  * @param countryOfManufacture
  *   This is commodity country of manufacture. This is required for International shipments. Maximum allowed length is 4.<br>Example:
  *   US<br><a onclick='loadDocReference("countrycodes")'>click here to see Country codes</a>
  * @param cIMarksAndNumbers
  *   This is an identifying mark or number used on the packaging of a shipment to help customers identify a particular shipment<br>Example:
  *   87123
  * @param harmonizedCode
  *   This is to specify the Harmonized Tariff System (HTS) code to meet U.S. and foreign governments' customs requirements. These are
  *   mainly used to estimate the duties and taxes.<br>Example: 0613<br>To research the classification for your commodity, use the FedEx
  *   Global Trade Manager online at <a href='http://www.fedex.com/gtm' target='_blank'>fedex.com/gtm</a>. You will find country-specific
  *   information to determine whether your commodity is considered to be a document or non-document for your destination.
  * @param name
  *   This is Commodity name.<br>Example: Non-Threaded Rivets
  * @param exportLicenseNumber
  *   This is the export license number for the shipment.<br>Example: 26456
  * @param exportLicenseExpirationDate
  *   Specify the export license expiration date for the shipment.<br>Format YYYY-MM-DD<br>Example : 2009-04-12
  * @param partNumber
  *   This is a part number.<br>Example: 167
  * @param purpose
  *   This field is used for calculation of duties and taxes.<br><br> Valid values are : BUSINESS and CONSUMER. <br>Example:BUSINESS
  */
case class Commodity(
    description: String,
    unitPrice: Option[Money] = None,
    additionalMeasures: Option[Seq[AdditionalMeasures]] = None,
    numberOfPieces: Option[Int] = None,
    quantity: Option[Int] = None,
    quantityUnits: Option[String] = None,
    customsValue: Option[CustomsMoney] = None,
    countryOfManufacture: Option[String] = None,
    cIMarksAndNumbers: Option[String] = None,
    harmonizedCode: Option[String] = None,
    name: Option[String] = None,
    weight: Option[Weight4] = None,
    exportLicenseNumber: Option[String] = None,
    exportLicenseExpirationDate: Option[java.time.OffsetDateTime] = None,
    partNumber: Option[String] = None,
    purpose: Option[Commodity.Purpose] = None,
    usmcaDetail: Option[UsmcaDetail] = None
)

object Commodity {
  enum Purpose {
    case BUSINESS
    case CONSUMER
  }
  object Purpose {
    given Encoder[Purpose] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Purpose] = Decoder.decodeString.emapTry(s => scala.util.Try(Purpose.valueOf(s)))
  }
  given Encoder[Commodity] = new Encoder.AsObject[Commodity] {
    final def encodeObject(o: Commodity): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "description"                 -> o.description.asJson,
          "unitPrice"                   -> o.unitPrice.asJson,
          "additionalMeasures"          -> o.additionalMeasures.asJson,
          "numberOfPieces"              -> o.numberOfPieces.asJson,
          "quantity"                    -> o.quantity.asJson,
          "quantityUnits"               -> o.quantityUnits.asJson,
          "customsValue"                -> o.customsValue.asJson,
          "countryOfManufacture"        -> o.countryOfManufacture.asJson,
          "cIMarksAndNumbers"           -> o.cIMarksAndNumbers.asJson,
          "harmonizedCode"              -> o.harmonizedCode.asJson,
          "name"                        -> o.name.asJson,
          "weight"                      -> o.weight.asJson,
          "exportLicenseNumber"         -> o.exportLicenseNumber.asJson,
          "exportLicenseExpirationDate" -> o.exportLicenseExpirationDate.asJson,
          "partNumber"                  -> o.partNumber.asJson,
          "purpose"                     -> o.purpose.asJson,
          "usmcaDetail"                 -> o.usmcaDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Commodity] = (c: HCursor) => {
    for {
      description                 <- c.downField("description").as[String]
      unitPrice                   <- c.downField("unitPrice").as[Option[Money]]
      additionalMeasures          <- c.downField("additionalMeasures").as[Option[Seq[AdditionalMeasures]]]
      numberOfPieces              <- c.downField("numberOfPieces").as[Option[Int]]
      quantity                    <- c.downField("quantity").as[Option[Int]]
      quantityUnits               <- c.downField("quantityUnits").as[Option[String]]
      customsValue                <- c.downField("customsValue").as[Option[CustomsMoney]]
      countryOfManufacture        <- c.downField("countryOfManufacture").as[Option[String]]
      cIMarksAndNumbers           <- c.downField("cIMarksAndNumbers").as[Option[String]]
      harmonizedCode              <- c.downField("harmonizedCode").as[Option[String]]
      name                        <- c.downField("name").as[Option[String]]
      weight                      <- c.downField("weight").as[Option[Weight4]]
      exportLicenseNumber         <- c.downField("exportLicenseNumber").as[Option[String]]
      exportLicenseExpirationDate <- c.downField("exportLicenseExpirationDate").as[Option[java.time.OffsetDateTime]]
      partNumber                  <- c.downField("partNumber").as[Option[String]]
      purpose                     <- c.downField("purpose").as[Option[Purpose]]
      usmcaDetail                 <- c.downField("usmcaDetail").as[Option[UsmcaDetail]]
    } yield Commodity(
      description,
      unitPrice,
      additionalMeasures,
      numberOfPieces,
      quantity,
      quantityUnits,
      customsValue,
      countryOfManufacture,
      cIMarksAndNumbers,
      harmonizedCode,
      name,
      weight,
      exportLicenseNumber,
      exportLicenseExpirationDate,
      partNumber,
      purpose,
      usmcaDetail
    )
  }
}

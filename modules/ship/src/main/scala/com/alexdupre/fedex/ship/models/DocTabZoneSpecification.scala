package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the doc tab zone specification.
  *
  * @param zoneNumber
  *   It is a non-negative integer that represents the portion of doc-tab in a label.<br>Example: 1
  * @param header
  *   Indicates the parameter name in the header for the doc tab zone. The maximum charater limit is 7.<br>Example: WHT
  * @param dataField
  *   Indicate the path request/reply element to be printed on doc tab.<br>Example: <ul><li> REQUEST/PACKAGE/weight/Value</li><li>
  *   REQUEST/PACKAGE/weight/Value</li><li> REQUEST/PACKAGE/InsuredValue/Amount</li><li>
  *   REQUEST/SHIPMENT/SpecialServicesRequested/CodDetail/CodCollectionAmount/Amount</li><li>REQUEST/SHIPMENT/Shipper/Address/StreetLines[1]CLIENT/MeterNumber</li><li>
  *   TRANSACTION/CustomerTransactionId</li><li> REQUEST/SHIPMENT/TotalWeight/Value</li><li> REQUEST/SHIPMENT/ShipTimestamp</li><li>
  *   REQUEST/SHIPMENT/Recipient/Contact/PersonName</li><li> REPLY/SHIPMENT/OperationalDetail/DeliveryDate</li><li>
  *   REPLY/SHIPMENT/RATES/ACTUAL/totalBaseCharge/Amount</li><li> REPLY/SHIPMENT/RATES/ACTUAL/totalFreightDiscounts/Amount</li><li>
  *   REPLY/SHIPMENT/RATES/ACTUAL/totalSurcharges/Amount</li><li> REPLY/SHIPMENT/RATES/ACTUAL/totalNetCharge/Amount</li><li>
  *   REPLY/SHIPMENT/RATES/PAYOR_ACCOUNT_PACKAGE/totalSurcharges/Amount</li></ul>
  * @param literalValue
  *   Indicates the actual data to be printed in the label<br>
  * @param justification
  *   Indicates the justification format for the string.
  */
case class DocTabZoneSpecification(
    zoneNumber: Option[Int] = None,
    header: Option[String] = None,
    dataField: Option[String] = None,
    literalValue: Option[String] = None,
    justification: Option[DocTabZoneSpecification.Justification] = None
)

object DocTabZoneSpecification {
  enum Justification {
    case LEFT
    case RIGHT
  }
  object Justification {
    given Encoder[Justification] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Justification] = Decoder.decodeString.emapTry(s => scala.util.Try(Justification.valueOf(s)))
  }
  given Encoder[DocTabZoneSpecification] = new Encoder.AsObject[DocTabZoneSpecification] {
    final def encodeObject(o: DocTabZoneSpecification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "zoneNumber"    -> o.zoneNumber.asJson,
          "header"        -> o.header.asJson,
          "dataField"     -> o.dataField.asJson,
          "literalValue"  -> o.literalValue.asJson,
          "justification" -> o.justification.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocTabZoneSpecification] = (c: HCursor) => {
    for {
      zoneNumber    <- c.downField("zoneNumber").as[Option[Int]]
      header        <- c.downField("header").as[Option[String]]
      dataField     <- c.downField("dataField").as[Option[String]]
      literalValue  <- c.downField("literalValue").as[Option[String]]
      justification <- c.downField("justification").as[Option[Justification]]
    } yield DocTabZoneSpecification(zoneNumber, header, dataField, literalValue, justification)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the Hazardous commodity quantity details.
  *
  * @param quantityType
  *   Specifies which measure of quantity is to be validated.<br>Example:GROSS
  * @param amount
  *   Indicate the amount of the commodity in alternate units.<br>Example: 24.56
  * @param units
  *   Indicate the unit of measure.<br>Example: KG
  */
case class HazardousCommodityQuantityDetail(
    quantityType: HazardousCommodityQuantityDetail.QuantityType,
    amount: Double,
    units: Option[String] = None
)

object HazardousCommodityQuantityDetail {
  enum QuantityType {
    case GROSS
    case NET
  }
  object QuantityType {
    given Encoder[QuantityType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[QuantityType] = Decoder.decodeString.emapTry(s => scala.util.Try(QuantityType.valueOf(s)))
  }
  given Encoder[HazardousCommodityQuantityDetail] = new Encoder.AsObject[HazardousCommodityQuantityDetail] {
    final def encodeObject(o: HazardousCommodityQuantityDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "quantityType" -> o.quantityType.asJson,
          "amount"       -> o.amount.asJson,
          "units"        -> o.units.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[HazardousCommodityQuantityDetail] = (c: HCursor) => {
    for {
      quantityType <- c.downField("quantityType").as[QuantityType]
      amount       <- c.downField("amount").as[Double]
      units        <- c.downField("units").as[Option[String]]
    } yield HazardousCommodityQuantityDetail(quantityType, amount, units)
  }
}

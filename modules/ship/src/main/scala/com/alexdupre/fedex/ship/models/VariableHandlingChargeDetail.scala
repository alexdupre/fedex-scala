package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the details about how to calculate variable handling charges at the shipment level. They can be based on a percentage of the
  * shipping charges or a fixed amount. If indicated, element rateLevelType is required.
  *
  * @param rateType
  *   The rate type indicates what type of rate request is being returned; account, preferred, incentive, etc<br>Example: PREFERRED_CURRENCY
  * @param percentValue
  *   This is the variable handling percentage. If the percent value is mentioned as 10, it means 10%(multiplier of 0.1).<br>Example: 12.45
  * @param rateLevelType
  *   indicates whether or not the rating is being done at the package level, or if the packages are bundled together. At the package level,
  *   charges are applied based on the details of each individual package. If they are bundled, one package is chosen as the parent and
  *   charges are applied based on that one package.<br>Example: INDIVIDUAL_PACKAGE_RATE
  * @param fixedValue
  *   This is to specify a fixed handling charge on the shipment. The element allows entry of 7 characters before the decimal and 2
  *   characters following the decimal. <br>Example: 5.00.
  * @param rateElementBasis
  *   Specify the charge type upon which the variable handling percentage amount is calculated.
  */
case class VariableHandlingChargeDetail(
    rateType: Option[VariableHandlingChargeDetail.RateType] = None,
    percentValue: Option[Double] = None,
    rateLevelType: Option[VariableHandlingChargeDetail.RateLevelType] = None,
    fixedValue: Option[Money] = None,
    rateElementBasis: Option[VariableHandlingChargeDetail.RateElementBasis] = None
)

object VariableHandlingChargeDetail {
  enum RateType {
    case ACCOUNT
    case ACTUAL
    case CURRENT
    case CUSTOM
    case LIST
    case INCENTIVE
    case PREFERRED
    case PREFERRED_INCENTIVE
    case PREFERRED_CURRENCY
  }
  object RateType {
    given Encoder[RateType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateType] = Decoder.decodeString.emapTry(s => scala.util.Try(RateType.valueOf(s)))
  }

  enum RateLevelType {
    case BUNDLED_RATE
    case INDIVIDUAL_PACKAGE_RATE
  }
  object RateLevelType {
    given Encoder[RateLevelType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateLevelType] = Decoder.decodeString.emapTry(s => scala.util.Try(RateLevelType.valueOf(s)))
  }

  enum RateElementBasis {
    case NET_CHARGE
    case NET_FREIGHT
    case BASE_CHARGE
    case NET_CHARGE_EXCLUDING_TAXES
  }
  object RateElementBasis {
    given Encoder[RateElementBasis] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateElementBasis] = Decoder.decodeString.emapTry(s => scala.util.Try(RateElementBasis.valueOf(s)))
  }
  given Encoder[VariableHandlingChargeDetail] = new Encoder.AsObject[VariableHandlingChargeDetail] {
    final def encodeObject(o: VariableHandlingChargeDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "rateType"         -> o.rateType.asJson,
          "percentValue"     -> o.percentValue.asJson,
          "rateLevelType"    -> o.rateLevelType.asJson,
          "fixedValue"       -> o.fixedValue.asJson,
          "rateElementBasis" -> o.rateElementBasis.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[VariableHandlingChargeDetail] = (c: HCursor) => {
    for {
      rateType         <- c.downField("rateType").as[Option[RateType]]
      percentValue     <- c.downField("percentValue").as[Option[Double]]
      rateLevelType    <- c.downField("rateLevelType").as[Option[RateLevelType]]
      fixedValue       <- c.downField("fixedValue").as[Option[Money]]
      rateElementBasis <- c.downField("rateElementBasis").as[Option[RateElementBasis]]
    } yield VariableHandlingChargeDetail(rateType, percentValue, rateLevelType, fixedValue, rateElementBasis)
  }
}

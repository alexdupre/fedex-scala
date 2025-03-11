package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify C.O.D. transportation charges.
  *
  * @param rateType
  *   Specify the Rate Type used.
  * @param rateLevelType
  *   Specify which level the rate to be applied.
  * @param chargeLevelType
  *   Specify which level the charges to be applied.
  * @param chargeType
  *   Specify Charge type.
  */
case class CODTransportationChargesDetail(
    rateType: Option[CODTransportationChargesDetail.RateType] = None,
    rateLevelType: Option[CODTransportationChargesDetail.RateLevelType] = None,
    chargeLevelType: Option[CODTransportationChargesDetail.ChargeLevelType] = None,
    chargeType: Option[CODTransportationChargesDetail.ChargeType] = None
)

object CODTransportationChargesDetail {
  enum RateType {
    case ACCOUNT
    case LIST
    case ACTUAL
    case CURRENT
    case CUSTOM
    case UNKNOWN_DEFAULT
  }
  object RateType {
    given Encoder[RateType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateType] = Decoder.decodeString.map(s => scala.util.Try(RateType.valueOf(s)).getOrElse(RateType.UNKNOWN_DEFAULT))
  }

  enum RateLevelType {
    case BUNDLED_RATE
    case INDIVIDUAL_PACKAGE_RATE
    case UNKNOWN_DEFAULT
  }
  object RateLevelType {
    given Encoder[RateLevelType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateLevelType] =
      Decoder.decodeString.map(s => scala.util.Try(RateLevelType.valueOf(s)).getOrElse(RateLevelType.UNKNOWN_DEFAULT))
  }

  enum ChargeLevelType {
    case CURRENT_PACKAGE
    case SUM_OF_PACKAGES
    case UNKNOWN_DEFAULT
  }
  object ChargeLevelType {
    given Encoder[ChargeLevelType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ChargeLevelType] =
      Decoder.decodeString.map(s => scala.util.Try(ChargeLevelType.valueOf(s)).getOrElse(ChargeLevelType.UNKNOWN_DEFAULT))
  }

  enum ChargeType {
    case COD_SURCHARGE
    case NET_CHARGE
    case NET_FREIGHT
    case TOTAL_CUSTOMER_CHARGE
    case UNKNOWN_DEFAULT
  }
  object ChargeType {
    given Encoder[ChargeType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ChargeType] = Decoder.decodeString.map(s => scala.util.Try(ChargeType.valueOf(s)).getOrElse(ChargeType.UNKNOWN_DEFAULT))
  }
  given Encoder[CODTransportationChargesDetail] = new Encoder.AsObject[CODTransportationChargesDetail] {
    final def encodeObject(o: CODTransportationChargesDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "rateType"        -> o.rateType.asJson,
          "rateLevelType"   -> o.rateLevelType.asJson,
          "chargeLevelType" -> o.chargeLevelType.asJson,
          "chargeType"      -> o.chargeType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CODTransportationChargesDetail] = (c: HCursor) => {
    for {
      rateType        <- c.downField("rateType").as[Option[RateType]]
      rateLevelType   <- c.downField("rateLevelType").as[Option[RateLevelType]]
      chargeLevelType <- c.downField("chargeLevelType").as[Option[ChargeLevelType]]
      chargeType      <- c.downField("chargeType").as[Option[ChargeType]]
    } yield CODTransportationChargesDetail(rateType, rateLevelType, chargeLevelType, chargeType)
  }
}

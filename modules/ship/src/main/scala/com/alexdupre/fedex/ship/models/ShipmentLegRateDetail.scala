package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is a placeholder for single leg of a shipment rates details, as calculated per a specific rate type.
  *
  * @param rateZone
  *   Indicates the rate zone used (based on origin and destination).<br>Example: rateZone
  * @param pricingCode
  *   Specifies the Pricing Code.<br>Example: pricingCode
  * @param taxes
  *   Specifies the taxes.
  * @param totalDimWeight
  *   Sum of dimensional weights for all packages.
  * @param totalRebates
  *   Specifies the total rebate.<br>Example: 2.0
  * @param fuelSurchargePercent
  *   Specifies a fuel surcharge percentage.<br>Example: 6.0
  * @param currencyExchangeRate
  *   Specifies currency exchange rate.
  * @param dimDivisor
  *   The value used to calculate the weight based on the dimensions.<br>Example: 6
  * @param rateType
  *   Type used for this specific set of rate data.<br>Example: PAYOR_RETAIL_PACKAGE
  * @param legDestinationLocationId
  *   Specifies the location id the destination of shipment leg.<br>Example: HKAA
  * @param dimDivisorType
  *   Identifies the type of dim divisor that was applied.<br>Example: dimDivisorType
  * @param totalBaseCharge
  *   The total freight charge that was calculated before surcharges, discounts and taxes.<br>Example: 6.0
  * @param ratedWeightMethod
  *   Indicates which weight was used.<br>Example: ratedWeightMethod
  * @param totalFreightDiscounts
  *   The sum of all discounts.<br>Example: 9.0
  * @param totalTaxes
  *   Total of the transportation-based taxes.<br>Example: 12.6
  * @param minimumChargeType
  *   Specifies minimum charge type.Example: minimumChargeType
  * @param totalDutiesAndTaxes
  *   Total of shipments duties and taxes; only provided if estimated duties and taxes were calculated for this shipment.<br>Example: 17.78
  * @param totalNetFreight
  *   The freight charge minus discounts.<br>Example: 6.0
  * @param totalNetFedExCharge
  *   This is the sum of shipment's total surcharges (not including total taxes).<br>Example: 3.2
  * @param surcharges
  *   All surcharges that apply to this shipment.<br><a onclick='loadDocReference("surcharges")'>click here to see surcharges</a>
  * @param totalSurcharges
  *   The total of all surcharges.<br>Example: 5.0
  * @param totalBillingWeight
  *   The weight used to calculate these rates.
  * @param rateScale
  *   Indicates the rate scale used.<br>Example: 6702
  * @param totalNetCharge
  *   The net charge after applying all discounts and surcharges.<br>Example: 253
  * @param totalNetChargeWithDutiesAndTaxes
  *   Sum of total net charge, total duties and taxes; only provided if estimated duties and taxes were calculated for this shipment and
  *   duties, taxes and transportation charges are all paid by the same sender account.<br>Example: 25.67
  * @param currency
  *   This is the currency code for the amount.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>click here to see Currency
  *   codes</a>
  */
case class ShipmentLegRateDetail(
    rateZone: Option[String] = None,
    pricingCode: Option[String] = None,
    taxes: Option[Seq[Tax]] = None,
    totalDimWeight: Option[Weight] = None,
    totalRebates: Option[Double] = None,
    fuelSurchargePercent: Option[Double] = None,
    currencyExchangeRate: Option[CurrencyExchangeRate] = None,
    dimDivisor: Option[Int] = None,
    rateType: Option[String] = None,
    legDestinationLocationId: Option[String] = None,
    dimDivisorType: Option[String] = None,
    totalBaseCharge: Option[Double] = None,
    ratedWeightMethod: Option[String] = None,
    totalFreightDiscounts: Option[Double] = None,
    totalTaxes: Option[Double] = None,
    minimumChargeType: Option[String] = None,
    totalDutiesAndTaxes: Option[Double] = None,
    totalNetFreight: Option[Double] = None,
    totalNetFedExCharge: Option[Double] = None,
    surcharges: Option[Seq[Surcharge]] = None,
    totalSurcharges: Option[Double] = None,
    totalBillingWeight: Option[Weight] = None,
    freightDiscounts: Option[Seq[RateDiscount]] = None,
    rateScale: Option[String] = None,
    totalNetCharge: Option[Double] = None,
    totalNetChargeWithDutiesAndTaxes: Option[Double] = None,
    currency: Option[String] = None
)

object ShipmentLegRateDetail {

  given Encoder[ShipmentLegRateDetail] = new Encoder.AsObject[ShipmentLegRateDetail] {
    final def encodeObject(o: ShipmentLegRateDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "rateZone"                         -> o.rateZone.asJson,
          "pricingCode"                      -> o.pricingCode.asJson,
          "taxes"                            -> o.taxes.asJson,
          "totalDimWeight"                   -> o.totalDimWeight.asJson,
          "totalRebates"                     -> o.totalRebates.asJson,
          "fuelSurchargePercent"             -> o.fuelSurchargePercent.asJson,
          "currencyExchangeRate"             -> o.currencyExchangeRate.asJson,
          "dimDivisor"                       -> o.dimDivisor.asJson,
          "rateType"                         -> o.rateType.asJson,
          "legDestinationLocationId"         -> o.legDestinationLocationId.asJson,
          "dimDivisorType"                   -> o.dimDivisorType.asJson,
          "totalBaseCharge"                  -> o.totalBaseCharge.asJson,
          "ratedWeightMethod"                -> o.ratedWeightMethod.asJson,
          "totalFreightDiscounts"            -> o.totalFreightDiscounts.asJson,
          "totalTaxes"                       -> o.totalTaxes.asJson,
          "minimumChargeType"                -> o.minimumChargeType.asJson,
          "totalDutiesAndTaxes"              -> o.totalDutiesAndTaxes.asJson,
          "totalNetFreight"                  -> o.totalNetFreight.asJson,
          "totalNetFedExCharge"              -> o.totalNetFedExCharge.asJson,
          "surcharges"                       -> o.surcharges.asJson,
          "totalSurcharges"                  -> o.totalSurcharges.asJson,
          "totalBillingWeight"               -> o.totalBillingWeight.asJson,
          "freightDiscounts"                 -> o.freightDiscounts.asJson,
          "rateScale"                        -> o.rateScale.asJson,
          "totalNetCharge"                   -> o.totalNetCharge.asJson,
          "totalNetChargeWithDutiesAndTaxes" -> o.totalNetChargeWithDutiesAndTaxes.asJson,
          "currency"                         -> o.currency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentLegRateDetail] = (c: HCursor) => {
    for {
      rateZone                         <- c.downField("rateZone").as[Option[String]]
      pricingCode                      <- c.downField("pricingCode").as[Option[String]]
      taxes                            <- c.downField("taxes").as[Option[Seq[Tax]]]
      totalDimWeight                   <- c.downField("totalDimWeight").as[Option[Weight]]
      totalRebates                     <- c.downField("totalRebates").as[Option[Double]]
      fuelSurchargePercent             <- c.downField("fuelSurchargePercent").as[Option[Double]]
      currencyExchangeRate             <- c.downField("currencyExchangeRate").as[Option[CurrencyExchangeRate]]
      dimDivisor                       <- c.downField("dimDivisor").as[Option[Int]]
      rateType                         <- c.downField("rateType").as[Option[String]]
      legDestinationLocationId         <- c.downField("legDestinationLocationId").as[Option[String]]
      dimDivisorType                   <- c.downField("dimDivisorType").as[Option[String]]
      totalBaseCharge                  <- c.downField("totalBaseCharge").as[Option[Double]]
      ratedWeightMethod                <- c.downField("ratedWeightMethod").as[Option[String]]
      totalFreightDiscounts            <- c.downField("totalFreightDiscounts").as[Option[Double]]
      totalTaxes                       <- c.downField("totalTaxes").as[Option[Double]]
      minimumChargeType                <- c.downField("minimumChargeType").as[Option[String]]
      totalDutiesAndTaxes              <- c.downField("totalDutiesAndTaxes").as[Option[Double]]
      totalNetFreight                  <- c.downField("totalNetFreight").as[Option[Double]]
      totalNetFedExCharge              <- c.downField("totalNetFedExCharge").as[Option[Double]]
      surcharges                       <- c.downField("surcharges").as[Option[Seq[Surcharge]]]
      totalSurcharges                  <- c.downField("totalSurcharges").as[Option[Double]]
      totalBillingWeight               <- c.downField("totalBillingWeight").as[Option[Weight]]
      freightDiscounts                 <- c.downField("freightDiscounts").as[Option[Seq[RateDiscount]]]
      rateScale                        <- c.downField("rateScale").as[Option[String]]
      totalNetCharge                   <- c.downField("totalNetCharge").as[Option[Double]]
      totalNetChargeWithDutiesAndTaxes <- c.downField("totalNetChargeWithDutiesAndTaxes").as[Option[Double]]
      currency                         <- c.downField("currency").as[Option[String]]
    } yield ShipmentLegRateDetail(
      rateZone,
      pricingCode,
      taxes,
      totalDimWeight,
      totalRebates,
      fuelSurchargePercent,
      currencyExchangeRate,
      dimDivisor,
      rateType,
      legDestinationLocationId,
      dimDivisorType,
      totalBaseCharge,
      ratedWeightMethod,
      totalFreightDiscounts,
      totalTaxes,
      minimumChargeType,
      totalDutiesAndTaxes,
      totalNetFreight,
      totalNetFedExCharge,
      surcharges,
      totalSurcharges,
      totalBillingWeight,
      freightDiscounts,
      rateScale,
      totalNetCharge,
      totalNetChargeWithDutiesAndTaxes,
      currency
    )
  }
}

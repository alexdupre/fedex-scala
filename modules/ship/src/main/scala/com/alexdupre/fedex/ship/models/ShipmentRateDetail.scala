package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is a placeholder for shipment total/summary rates details, as calculated per a specific rate type. The totals may differ from the
  * sum of corresponding package data for Multiweight or Express MPS.
  *
  * @param rateZone
  *   Indicates the rate zone used (based on origin and destination).<br>Example: US001O
  * @param ratedWeightMethod
  *   Indicates which weight was used.<br>Example: ACTUAL
  * @param totalDutiesTaxesAndFees
  *   The total of the total duties & taxes and the total ancillary fees & taxes.<br>Example: 24.56
  * @param pricingCode
  *   Specifies pricing Code.<br>Example: PACKAGE
  * @param totalFreightDiscounts
  *   The total discounts used in the rate calculation.<br>Example: 1.56
  * @param totalTaxes
  *   Total of the transportation-based taxes.<br>Example: 3.45
  * @param totalDutiesAndTaxes
  *   Total of all values under this shipment's duties and taxes; only provided if estimated duties and taxes were calculated for this
  *   shipment.<br>Example: 6.78
  * @param totalAncillaryFeesAndTaxes
  *   Identifies the total amount of the shipment-level fees and taxes that are not based on transportation charges or commodity-level
  *   estimated duties and taxes.<br>Example: 5.67
  * @param taxes
  *   All transportation-based taxes applicable to this shipment.
  * @param totalRebates
  *   The total sum of all rebates applied to this shipment.<br>Example: 1.98
  * @param fuelSurchargePercent
  *   Specifies a fuel surcharge percentage.<br>Example: 4.56
  * @param totalNetFreight
  *   The freight charge minus discounts.<br>Example: 9.56
  * @param totalNetFedExCharge
  *   This is the sum of shipment's total net freight, total surchages (not including totalTaxes).<br>Example: 88.56
  * @param shipmentLegRateDetails
  *   This is data for a single leg of a shipment's total/summary rates, as calculated per a specific rate type.
  * @param dimDivisor
  *   The value used to calculate the weight based on the dimensions.<br>Example: 0
  * @param rateType
  *   The Type used for this specific set of rate data.<br>Example: RATED_ACCOUNT_SHIPMENT
  * @param surcharges
  *   All surcharges that apply to this shipment.<br><a onclick='loadDocReference("surcharges")'>click here to see Surcharges</a>
  * @param totalSurcharges
  *   The total amount of all surcharges applied to this shipment.<br>Example: 9.88
  * @param totalBillingWeight
  *   The weight used to calculate these rates.
  * @param freightDiscounts
  *   Indicates the freight discounts.
  * @param rateScale
  *   Indicates the rate scale used.<br>Example: 00000
  * @param totalNetCharge
  *   The net charge after applying all discounts and surcharges.<br>Example: 3.78
  * @param totalBaseCharge
  *   The total Shipment charge that was calculated before surcharges, discounts and taxes.<br>Example: 234.56
  * @param totalNetChargeWithDutiesAndTaxes
  *   This is the sum of shipment's total net charges and total duties and taxes; only provided if estimated duties and taxes were
  *   calculated for this shipment AND duties, taxes and transportation charges are all paid by the same sender account.<br>Example: 222.56
  * @param currency
  *   Indicates the currency code.<br><a onclick='loadDocReference("currencycodes")'>click here to see Currency Codes</a>
  */
case class ShipmentRateDetail(
    rateZone: Option[String] = None,
    ratedWeightMethod: Option[String] = None,
    totalDutiesTaxesAndFees: Option[Double] = None,
    pricingCode: Option[String] = None,
    totalFreightDiscounts: Option[Double] = None,
    totalTaxes: Option[Double] = None,
    totalDutiesAndTaxes: Option[Double] = None,
    totalAncillaryFeesAndTaxes: Option[Double] = None,
    taxes: Option[Seq[Tax]] = None,
    totalRebates: Option[Double] = None,
    fuelSurchargePercent: Option[Double] = None,
    currencyExchangeRate: Option[CurrencyExchangeRate] = None,
    totalNetFreight: Option[Double] = None,
    totalNetFedExCharge: Option[Double] = None,
    shipmentLegRateDetails: Option[Seq[ShipmentLegRateDetail]] = None,
    dimDivisor: Option[Int] = None,
    rateType: Option[String] = None,
    surcharges: Option[Seq[Surcharge]] = None,
    totalSurcharges: Option[Double] = None,
    totalBillingWeight: Option[Weight] = None,
    freightDiscounts: Option[Seq[RateDiscount]] = None,
    rateScale: Option[String] = None,
    totalNetCharge: Option[Double] = None,
    totalBaseCharge: Option[Double] = None,
    totalNetChargeWithDutiesAndTaxes: Option[Double] = None,
    currency: Option[String] = None
)

object ShipmentRateDetail {

  given Encoder[ShipmentRateDetail] = new Encoder.AsObject[ShipmentRateDetail] {
    final def encodeObject(o: ShipmentRateDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "rateZone"                         -> o.rateZone.asJson,
          "ratedWeightMethod"                -> o.ratedWeightMethod.asJson,
          "totalDutiesTaxesAndFees"          -> o.totalDutiesTaxesAndFees.asJson,
          "pricingCode"                      -> o.pricingCode.asJson,
          "totalFreightDiscounts"            -> o.totalFreightDiscounts.asJson,
          "totalTaxes"                       -> o.totalTaxes.asJson,
          "totalDutiesAndTaxes"              -> o.totalDutiesAndTaxes.asJson,
          "totalAncillaryFeesAndTaxes"       -> o.totalAncillaryFeesAndTaxes.asJson,
          "taxes"                            -> o.taxes.asJson,
          "totalRebates"                     -> o.totalRebates.asJson,
          "fuelSurchargePercent"             -> o.fuelSurchargePercent.asJson,
          "currencyExchangeRate"             -> o.currencyExchangeRate.asJson,
          "totalNetFreight"                  -> o.totalNetFreight.asJson,
          "totalNetFedExCharge"              -> o.totalNetFedExCharge.asJson,
          "shipmentLegRateDetails"           -> o.shipmentLegRateDetails.asJson,
          "dimDivisor"                       -> o.dimDivisor.asJson,
          "rateType"                         -> o.rateType.asJson,
          "surcharges"                       -> o.surcharges.asJson,
          "totalSurcharges"                  -> o.totalSurcharges.asJson,
          "totalBillingWeight"               -> o.totalBillingWeight.asJson,
          "freightDiscounts"                 -> o.freightDiscounts.asJson,
          "rateScale"                        -> o.rateScale.asJson,
          "totalNetCharge"                   -> o.totalNetCharge.asJson,
          "totalBaseCharge"                  -> o.totalBaseCharge.asJson,
          "totalNetChargeWithDutiesAndTaxes" -> o.totalNetChargeWithDutiesAndTaxes.asJson,
          "currency"                         -> o.currency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentRateDetail] = (c: HCursor) => {
    for {
      rateZone                         <- c.downField("rateZone").as[Option[String]]
      ratedWeightMethod                <- c.downField("ratedWeightMethod").as[Option[String]]
      totalDutiesTaxesAndFees          <- c.downField("totalDutiesTaxesAndFees").as[Option[Double]]
      pricingCode                      <- c.downField("pricingCode").as[Option[String]]
      totalFreightDiscounts            <- c.downField("totalFreightDiscounts").as[Option[Double]]
      totalTaxes                       <- c.downField("totalTaxes").as[Option[Double]]
      totalDutiesAndTaxes              <- c.downField("totalDutiesAndTaxes").as[Option[Double]]
      totalAncillaryFeesAndTaxes       <- c.downField("totalAncillaryFeesAndTaxes").as[Option[Double]]
      taxes                            <- c.downField("taxes").as[Option[Seq[Tax]]]
      totalRebates                     <- c.downField("totalRebates").as[Option[Double]]
      fuelSurchargePercent             <- c.downField("fuelSurchargePercent").as[Option[Double]]
      currencyExchangeRate             <- c.downField("currencyExchangeRate").as[Option[CurrencyExchangeRate]]
      totalNetFreight                  <- c.downField("totalNetFreight").as[Option[Double]]
      totalNetFedExCharge              <- c.downField("totalNetFedExCharge").as[Option[Double]]
      shipmentLegRateDetails           <- c.downField("shipmentLegRateDetails").as[Option[Seq[ShipmentLegRateDetail]]]
      dimDivisor                       <- c.downField("dimDivisor").as[Option[Int]]
      rateType                         <- c.downField("rateType").as[Option[String]]
      surcharges                       <- c.downField("surcharges").as[Option[Seq[Surcharge]]]
      totalSurcharges                  <- c.downField("totalSurcharges").as[Option[Double]]
      totalBillingWeight               <- c.downField("totalBillingWeight").as[Option[Weight]]
      freightDiscounts                 <- c.downField("freightDiscounts").as[Option[Seq[RateDiscount]]]
      rateScale                        <- c.downField("rateScale").as[Option[String]]
      totalNetCharge                   <- c.downField("totalNetCharge").as[Option[Double]]
      totalBaseCharge                  <- c.downField("totalBaseCharge").as[Option[Double]]
      totalNetChargeWithDutiesAndTaxes <- c.downField("totalNetChargeWithDutiesAndTaxes").as[Option[Double]]
      currency                         <- c.downField("currency").as[Option[String]]
    } yield ShipmentRateDetail(
      rateZone,
      ratedWeightMethod,
      totalDutiesTaxesAndFees,
      pricingCode,
      totalFreightDiscounts,
      totalTaxes,
      totalDutiesAndTaxes,
      totalAncillaryFeesAndTaxes,
      taxes,
      totalRebates,
      fuelSurchargePercent,
      currencyExchangeRate,
      totalNetFreight,
      totalNetFedExCharge,
      shipmentLegRateDetails,
      dimDivisor,
      rateType,
      surcharges,
      totalSurcharges,
      totalBillingWeight,
      freightDiscounts,
      rateScale,
      totalNetCharge,
      totalBaseCharge,
      totalNetChargeWithDutiesAndTaxes,
      currency
    )
  }
}

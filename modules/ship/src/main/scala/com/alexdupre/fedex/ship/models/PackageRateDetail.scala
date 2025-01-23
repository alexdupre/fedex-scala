package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are package rate details, as calculated per a specific rate type.
  *
  * @param ratedWeightMethod
  *   Indicates the weight types used in calculating this rate, such as actual weight or dimensional weight.<br> Example: DIM
  * @param totalFreightDiscounts
  *   The sum of all freight discounts for this package.<br>Example: 44.55
  * @param totalTaxes
  *   The sum of all taxes on this package.<br>Example: 3.45
  * @param minimumChargeType
  *   Indicates the minumum charge type. INTERNAL FEDEX USE ONLY.Example: minimumChargeType
  * @param baseCharge
  *   The package transportation charge(prior to any discounts applied).<br>Example: 45.67
  * @param totalRebates
  *   Specifies total rebates on this package.<br>Example: 4.56
  * @param rateType
  *   This is the rate type used.<br>Example: PAYOR_RETAIL_PACKAGE
  * @param netFreight
  *   This is the net freight charges. i.e. base charge minus total freight discounts for a package.<br>Example: 4.89
  * @param surcharges
  *   These are all surcharges on this package.<br><a onclick='loadDocReference("surcharges")'>click here to see Surcharges</a>
  * @param totalSurcharges
  *   The sum of all surcharges on this package.<br>Example: 22.56
  * @param netFedExCharge
  *   This is sum of net freight and total surcharges (not including totalTaxes) for this package.<br>Example: 12.56
  * @param netCharge
  *   This is the sum of net freight, total surcharges and total taxes for a package.<br>Example: 121.56
  * @param currency
  *   This is the currency code. <br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>click here to see Currency codes</a>
  */
case class PackageRateDetail(
    ratedWeightMethod: Option[String] = None,
    totalFreightDiscounts: Option[Double] = None,
    totalTaxes: Option[Double] = None,
    minimumChargeType: Option[String] = None,
    baseCharge: Option[Double] = None,
    totalRebates: Option[Double] = None,
    rateType: Option[String] = None,
    billingWeight: Option[Weight] = None,
    netFreight: Option[Double] = None,
    surcharges: Option[Seq[Surcharge]] = None,
    totalSurcharges: Option[Double] = None,
    netFedExCharge: Option[Double] = None,
    netCharge: Option[Double] = None,
    currency: Option[String] = None
)

object PackageRateDetail {

  given Encoder[PackageRateDetail] = new Encoder.AsObject[PackageRateDetail] {
    final def encodeObject(o: PackageRateDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "ratedWeightMethod"     -> o.ratedWeightMethod.asJson,
          "totalFreightDiscounts" -> o.totalFreightDiscounts.asJson,
          "totalTaxes"            -> o.totalTaxes.asJson,
          "minimumChargeType"     -> o.minimumChargeType.asJson,
          "baseCharge"            -> o.baseCharge.asJson,
          "totalRebates"          -> o.totalRebates.asJson,
          "rateType"              -> o.rateType.asJson,
          "billingWeight"         -> o.billingWeight.asJson,
          "netFreight"            -> o.netFreight.asJson,
          "surcharges"            -> o.surcharges.asJson,
          "totalSurcharges"       -> o.totalSurcharges.asJson,
          "netFedExCharge"        -> o.netFedExCharge.asJson,
          "netCharge"             -> o.netCharge.asJson,
          "currency"              -> o.currency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageRateDetail] = (c: HCursor) => {
    for {
      ratedWeightMethod     <- c.downField("ratedWeightMethod").as[Option[String]]
      totalFreightDiscounts <- c.downField("totalFreightDiscounts").as[Option[Double]]
      totalTaxes            <- c.downField("totalTaxes").as[Option[Double]]
      minimumChargeType     <- c.downField("minimumChargeType").as[Option[String]]
      baseCharge            <- c.downField("baseCharge").as[Option[Double]]
      totalRebates          <- c.downField("totalRebates").as[Option[Double]]
      rateType              <- c.downField("rateType").as[Option[String]]
      billingWeight         <- c.downField("billingWeight").as[Option[Weight]]
      netFreight            <- c.downField("netFreight").as[Option[Double]]
      surcharges            <- c.downField("surcharges").as[Option[Seq[Surcharge]]]
      totalSurcharges       <- c.downField("totalSurcharges").as[Option[Double]]
      netFedExCharge        <- c.downField("netFedExCharge").as[Option[Double]]
      netCharge             <- c.downField("netCharge").as[Option[Double]]
      currency              <- c.downField("currency").as[Option[String]]
    } yield PackageRateDetail(
      ratedWeightMethod,
      totalFreightDiscounts,
      totalTaxes,
      minimumChargeType,
      baseCharge,
      totalRebates,
      rateType,
      billingWeight,
      netFreight,
      surcharges,
      totalSurcharges,
      netFedExCharge,
      netCharge,
      currency
    )
  }
}

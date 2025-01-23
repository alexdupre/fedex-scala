package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the shipment level operational information.
  *
  * @param originServiceArea
  *   Indicates the origin service area.<br>Example: A1
  * @param serviceCode
  *   Indicates the service code.<br>Example: 010
  * @param airportId
  *   Indicates the airport identifier.<br>Example: DFW
  * @param postalCode
  *   Specifies the postal code.<br>Example: 38010<br><a onclick='loadDocReference("postalawarecountries")'>click here to see Postal aware
  *   countries</a>
  * @param scac
  *   Indicates standard carrier alpha code.
  * @param deliveryDay
  *   Specifies expected/estimated day of week of the delivery.<br>Example: TUE
  * @param originLocationId
  *   This is the origin Location identifier.<br>Example: 678
  * @param countryCode
  *   Indicate the two-letter country code.<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>click here to see Country
  *   codes</a>
  * @param astraDescription
  *   Specifies astra description.<br>Example: SMART POST
  * @param originLocationNumber
  *   Specifies origin location number.<br>Example: 243
  * @param deliveryDate
  *   Specifies delivery date for the shipment. The format is [YYYY-MM-DD]<br>Example: 2001-04-05
  * @param deliveryEligibilities
  *   FedEx Ground delivery features for which this shipment may be eligible.<br>Example: ["deliveryEligibilities"]
  * @param ineligibleForMoneyBackGuarantee
  *   Indicates that this shipment is not eligible for money back guarantee.
  * @param maximumTransitTime
  *   Maximum expected transit time.<br>Example: SEVEN_DAYS
  * @param destinationLocationStateOrProvinceCode
  *   This is the state or province code of the shipment destination location, and is not necessarily the same as the postal
  *   state.<br>Example: GA<br><a onclick='loadDocReference("canadaprovincecodes")'>click here to see State or Province Code</a>
  * @param astraPlannedServiceLevel
  *   Text describing planned delivery.<br>Example: TUE - 15 OCT 10:30A
  * @param destinationLocationId
  *   Specifies the FedEx Destination Location Identifier.<br>Example: DALA
  * @param transitTime
  *   Standard transit time per origin, destination, and service.<br>Example: TWO_DAYS
  * @param stateOrProvinceCode
  *   This is a placeholder for State or Province code.State code is required for US, CA, PR and not required for other countries.
  *   Conditional. Max length is 2.<br>Example: CA<br><a onclick='loadDocReference("canadaprovincecodes")'>click here to see State or
  *   Province Code</a>
  * @param destinationLocationNumber
  *   Indicates destination location number.<br>Example: 876
  * @param packagingCode
  *   Indicates packaging code.<br>Example: 03
  * @param commitDate
  *   This is committed date of delivery.<br>Example: 2019-10-15
  * @param publishedDeliveryTime
  *   This is delivery time, as published in Service Guide.<br>Example: 10:30A
  * @param ursaSuffixCode
  *   This is ursa suffix code.<br>Example: Ga
  * @param ursaPrefixCode
  *   This is ursa prefix code.<br>Example: XH
  * @param destinationServiceArea
  *   Specifies destination service area.<br>Example: A1
  * @param commitDay
  *   Committed day of week of delivery.<br>Example: TUE
  * @param customTransitTime
  *   Transit time based on customer eligibility.<br>Example: ONE_DAY
  */
case class ShipmentOperationalDetail(
    originServiceArea: Option[String] = None,
    serviceCode: Option[String] = None,
    airportId: Option[String] = None,
    postalCode: Option[String] = None,
    scac: Option[String] = None,
    deliveryDay: Option[String] = None,
    originLocationId: Option[String] = None,
    countryCode: Option[String] = None,
    astraDescription: Option[String] = None,
    originLocationNumber: Option[Int] = None,
    deliveryDate: Option[String] = None,
    deliveryEligibilities: Option[Seq[String]] = None,
    ineligibleForMoneyBackGuarantee: Option[Boolean] = None,
    maximumTransitTime: Option[String] = None,
    destinationLocationStateOrProvinceCode: Option[String] = None,
    astraPlannedServiceLevel: Option[String] = None,
    destinationLocationId: Option[String] = None,
    transitTime: Option[String] = None,
    stateOrProvinceCode: Option[String] = None,
    destinationLocationNumber: Option[Int] = None,
    packagingCode: Option[String] = None,
    commitDate: Option[String] = None,
    publishedDeliveryTime: Option[String] = None,
    ursaSuffixCode: Option[String] = None,
    ursaPrefixCode: Option[String] = None,
    destinationServiceArea: Option[String] = None,
    commitDay: Option[String] = None,
    customTransitTime: Option[String] = None
)

object ShipmentOperationalDetail {

  given Encoder[ShipmentOperationalDetail] = new Encoder.AsObject[ShipmentOperationalDetail] {
    final def encodeObject(o: ShipmentOperationalDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "originServiceArea"                      -> o.originServiceArea.asJson,
          "serviceCode"                            -> o.serviceCode.asJson,
          "airportId"                              -> o.airportId.asJson,
          "postalCode"                             -> o.postalCode.asJson,
          "scac"                                   -> o.scac.asJson,
          "deliveryDay"                            -> o.deliveryDay.asJson,
          "originLocationId"                       -> o.originLocationId.asJson,
          "countryCode"                            -> o.countryCode.asJson,
          "astraDescription"                       -> o.astraDescription.asJson,
          "originLocationNumber"                   -> o.originLocationNumber.asJson,
          "deliveryDate"                           -> o.deliveryDate.asJson,
          "deliveryEligibilities"                  -> o.deliveryEligibilities.asJson,
          "ineligibleForMoneyBackGuarantee"        -> o.ineligibleForMoneyBackGuarantee.asJson,
          "maximumTransitTime"                     -> o.maximumTransitTime.asJson,
          "destinationLocationStateOrProvinceCode" -> o.destinationLocationStateOrProvinceCode.asJson,
          "astraPlannedServiceLevel"               -> o.astraPlannedServiceLevel.asJson,
          "destinationLocationId"                  -> o.destinationLocationId.asJson,
          "transitTime"                            -> o.transitTime.asJson,
          "stateOrProvinceCode"                    -> o.stateOrProvinceCode.asJson,
          "destinationLocationNumber"              -> o.destinationLocationNumber.asJson,
          "packagingCode"                          -> o.packagingCode.asJson,
          "commitDate"                             -> o.commitDate.asJson,
          "publishedDeliveryTime"                  -> o.publishedDeliveryTime.asJson,
          "ursaSuffixCode"                         -> o.ursaSuffixCode.asJson,
          "ursaPrefixCode"                         -> o.ursaPrefixCode.asJson,
          "destinationServiceArea"                 -> o.destinationServiceArea.asJson,
          "commitDay"                              -> o.commitDay.asJson,
          "customTransitTime"                      -> o.customTransitTime.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentOperationalDetail] = (c: HCursor) => {
    for {
      originServiceArea                      <- c.downField("originServiceArea").as[Option[String]]
      serviceCode                            <- c.downField("serviceCode").as[Option[String]]
      airportId                              <- c.downField("airportId").as[Option[String]]
      postalCode                             <- c.downField("postalCode").as[Option[String]]
      scac                                   <- c.downField("scac").as[Option[String]]
      deliveryDay                            <- c.downField("deliveryDay").as[Option[String]]
      originLocationId                       <- c.downField("originLocationId").as[Option[String]]
      countryCode                            <- c.downField("countryCode").as[Option[String]]
      astraDescription                       <- c.downField("astraDescription").as[Option[String]]
      originLocationNumber                   <- c.downField("originLocationNumber").as[Option[Int]]
      deliveryDate                           <- c.downField("deliveryDate").as[Option[String]]
      deliveryEligibilities                  <- c.downField("deliveryEligibilities").as[Option[Seq[String]]]
      ineligibleForMoneyBackGuarantee        <- c.downField("ineligibleForMoneyBackGuarantee").as[Option[Boolean]]
      maximumTransitTime                     <- c.downField("maximumTransitTime").as[Option[String]]
      destinationLocationStateOrProvinceCode <- c.downField("destinationLocationStateOrProvinceCode").as[Option[String]]
      astraPlannedServiceLevel               <- c.downField("astraPlannedServiceLevel").as[Option[String]]
      destinationLocationId                  <- c.downField("destinationLocationId").as[Option[String]]
      transitTime                            <- c.downField("transitTime").as[Option[String]]
      stateOrProvinceCode                    <- c.downField("stateOrProvinceCode").as[Option[String]]
      destinationLocationNumber              <- c.downField("destinationLocationNumber").as[Option[Int]]
      packagingCode                          <- c.downField("packagingCode").as[Option[String]]
      commitDate                             <- c.downField("commitDate").as[Option[String]]
      publishedDeliveryTime                  <- c.downField("publishedDeliveryTime").as[Option[String]]
      ursaSuffixCode                         <- c.downField("ursaSuffixCode").as[Option[String]]
      ursaPrefixCode                         <- c.downField("ursaPrefixCode").as[Option[String]]
      destinationServiceArea                 <- c.downField("destinationServiceArea").as[Option[String]]
      commitDay                              <- c.downField("commitDay").as[Option[String]]
      customTransitTime                      <- c.downField("customTransitTime").as[Option[String]]
    } yield ShipmentOperationalDetail(
      originServiceArea,
      serviceCode,
      airportId,
      postalCode,
      scac,
      deliveryDay,
      originLocationId,
      countryCode,
      astraDescription,
      originLocationNumber,
      deliveryDate,
      deliveryEligibilities,
      ineligibleForMoneyBackGuarantee,
      maximumTransitTime,
      destinationLocationStateOrProvinceCode,
      astraPlannedServiceLevel,
      destinationLocationId,
      transitTime,
      stateOrProvinceCode,
      destinationLocationNumber,
      packagingCode,
      commitDate,
      publishedDeliveryTime,
      ursaSuffixCode,
      ursaPrefixCode,
      destinationServiceArea,
      commitDay,
      customTransitTime
    )
  }
}

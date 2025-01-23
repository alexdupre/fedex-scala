package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the detailed shipment request data to be validated before being submitted to FedEx.
  *
  * @param pickupType
  *   Select if the shipment is to be dropped off at Fedex location or to be picked up by FedEx or if it is a scheduled pickup for this
  *   shipment.<br><a onclick='loadDocReference("pickuptypes")'>Click here for more information on Pickup Types.</a>
  * @param serviceType
  *   Indicate the FedEx service Type used for this shipment.<br>Example: STANDARD_OVERNIGHT<br><a
  *   onclick='loadDocReference("servicetypes")'>click here to see available FedEx Service Types</a>
  * @param packagingType
  *   Indicate the type of packaging used for the package.<br>Note: For Express Freight shipments, the packaging will default to value
  *   YOUR_PACKAGING irrespective type provided in the request.<br>Example: FEDEX_ENVELOPE<br><a
  *   onclick='loadDocReference("packagetypes")'>click here to see Package Types</a>
  * @param totalWeight
  *   shipment total weight should be in Kg or in Lbs
  * @param shipper
  *   Indicate shippers details. <br>Note: Shipper address and Origin address should be the same address.
  * @param recipients
  *   Indicate the shipment recipient details or the physical location details for the package destination.
  * @param requestedPackageLineItems
  *   Use this object to provide the package details.<br>Note: <ul><li>At least one instance containing the weight is required for EXPRESS
  *   and GROUND package.</li><li>Only Single piece requests are supported henceonly one line item should be provided.</li><li>Multiple
  *   piece shipment validation is not supported.</li></ul>
  * @param shipDatestamp
  *   Indicate the shipment date.<br>Format: YYYY-MM-DD<br>Note: Default value is current date in case the date is not provided in the
  *   request.<br>Example: 2021-04-06
  * @param origin
  *   Indicate the shipment origin address information, if it is different from the shippers address.
  * @param blockInsightVisibility
  *   Indicate if the shipment be available to be visible/tracked using FedEx InSight® tool. If value indicated as true, only the
  *   shipper/payer will have visibility of this shipment in the said tool.
  * @param rateRequestType
  *   Indicate the type of rates to be returned.<br>Following are values:<ul><li>LIST - Returns published list rates will be returned in
  *   addition to account-specific rate (if applicable).</li><li>PREFERRED - It returns rates in currency as specified in the
  *   PreferredCurrency element.</li><li>ACCOUNT - Returns account specific rates. Note: The account specific rates are returned by default
  *   if the shipper account number is specified in the shipment.</li><li>INCENTIVE - This is one-time discount for incentivizing the
  *   customer.</li></ul>Examples: ["ACCOUNT", "PREFERRED"]
  * @param preferredCurrency
  *   Indicate the currency the caller requests to have used in all returned monetary values. Should be Used in conjunction with the element
  *   RateRequestType.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>click here to see available Currency
  *   codes</a><br>Note: Incorrect currency codes should not be supplied. The system ignores the incorrect currency code.
  */
case class RequestedShipmentVerify(
    pickupType: RequestedShipmentVerify.PickupType,
    serviceType: String,
    packagingType: String,
    totalWeight: Int,
    shipper: ShipperParty,
    recipients: Seq[RecipientsParty],
    shippingChargesPayment: Payment,
    labelSpecification: LabelSpecification,
    requestedPackageLineItems: Seq[RequestedPackageLineItem],
    shipDatestamp: Option[String] = None,
    origin: Option[ContactAndAddressVerify] = None,
    shipmentSpecialServices: Option[RequestedShipmentVerifyShipmentSpecialServices] = None,
    emailNotificationDetail: Option[EMailNotificationDetail] = None,
    variableHandlingChargeDetail: Option[VariableHandlingChargeDetail] = None,
    customsClearanceDetail: Option[CustomsClearanceDetail] = None,
    smartPostInfoDetail: Option[SmartPostInfoDetail] = None,
    blockInsightVisibility: Option[Boolean] = None,
    shippingDocumentSpecification: Option[ShippingDocumentSpecification] = None,
    rateRequestType: Option[Seq[RequestedShipmentVerify.RateRequestType]] = None,
    preferredCurrency: Option[String] = None
)

object RequestedShipmentVerify {
  enum RateRequestType {
    case LIST
    case NONE
    case PREFERRED
    case ACCOUNT
    case INCENTIVE
    case RETAIL
  }
  object RateRequestType {
    given Encoder[RateRequestType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RateRequestType] = Decoder.decodeString.emapTry(s => scala.util.Try(RateRequestType.valueOf(s)))
  }

  enum PickupType {
    case `CONTACT_FEDEX TO_SCHEDULE`
    case DROPOFF_AT_FEDEX_LOCATION
    case USE_SCHEDULED_PICKUP
  }
  object PickupType {
    given Encoder[PickupType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[PickupType] = Decoder.decodeString.emapTry(s => scala.util.Try(PickupType.valueOf(s)))
  }
  given Encoder[RequestedShipmentVerify] = new Encoder.AsObject[RequestedShipmentVerify] {
    final def encodeObject(o: RequestedShipmentVerify): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "pickupType"                    -> o.pickupType.asJson,
          "serviceType"                   -> o.serviceType.asJson,
          "packagingType"                 -> o.packagingType.asJson,
          "totalWeight"                   -> o.totalWeight.asJson,
          "shipper"                       -> o.shipper.asJson,
          "recipients"                    -> o.recipients.asJson,
          "shippingChargesPayment"        -> o.shippingChargesPayment.asJson,
          "labelSpecification"            -> o.labelSpecification.asJson,
          "requestedPackageLineItems"     -> o.requestedPackageLineItems.asJson,
          "shipDatestamp"                 -> o.shipDatestamp.asJson,
          "origin"                        -> o.origin.asJson,
          "shipmentSpecialServices"       -> o.shipmentSpecialServices.asJson,
          "emailNotificationDetail"       -> o.emailNotificationDetail.asJson,
          "variableHandlingChargeDetail"  -> o.variableHandlingChargeDetail.asJson,
          "customsClearanceDetail"        -> o.customsClearanceDetail.asJson,
          "smartPostInfoDetail"           -> o.smartPostInfoDetail.asJson,
          "blockInsightVisibility"        -> o.blockInsightVisibility.asJson,
          "shippingDocumentSpecification" -> o.shippingDocumentSpecification.asJson,
          "rateRequestType"               -> o.rateRequestType.asJson,
          "preferredCurrency"             -> o.preferredCurrency.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RequestedShipmentVerify] = (c: HCursor) => {
    for {
      pickupType                    <- c.downField("pickupType").as[PickupType]
      serviceType                   <- c.downField("serviceType").as[String]
      packagingType                 <- c.downField("packagingType").as[String]
      totalWeight                   <- c.downField("totalWeight").as[Int]
      shipper                       <- c.downField("shipper").as[ShipperParty]
      recipients                    <- c.downField("recipients").as[Seq[RecipientsParty]]
      shippingChargesPayment        <- c.downField("shippingChargesPayment").as[Payment]
      labelSpecification            <- c.downField("labelSpecification").as[LabelSpecification]
      requestedPackageLineItems     <- c.downField("requestedPackageLineItems").as[Seq[RequestedPackageLineItem]]
      shipDatestamp                 <- c.downField("shipDatestamp").as[Option[String]]
      origin                        <- c.downField("origin").as[Option[ContactAndAddressVerify]]
      shipmentSpecialServices       <- c.downField("shipmentSpecialServices").as[Option[RequestedShipmentVerifyShipmentSpecialServices]]
      emailNotificationDetail       <- c.downField("emailNotificationDetail").as[Option[EMailNotificationDetail]]
      variableHandlingChargeDetail  <- c.downField("variableHandlingChargeDetail").as[Option[VariableHandlingChargeDetail]]
      customsClearanceDetail        <- c.downField("customsClearanceDetail").as[Option[CustomsClearanceDetail]]
      smartPostInfoDetail           <- c.downField("smartPostInfoDetail").as[Option[SmartPostInfoDetail]]
      blockInsightVisibility        <- c.downField("blockInsightVisibility").as[Option[Boolean]]
      shippingDocumentSpecification <- c.downField("shippingDocumentSpecification").as[Option[ShippingDocumentSpecification]]
      rateRequestType               <- c.downField("rateRequestType").as[Option[Seq[RateRequestType]]]
      preferredCurrency             <- c.downField("preferredCurrency").as[Option[String]]
    } yield RequestedShipmentVerify(
      pickupType,
      serviceType,
      packagingType,
      totalWeight,
      shipper,
      recipients,
      shippingChargesPayment,
      labelSpecification,
      requestedPackageLineItems,
      shipDatestamp,
      origin,
      shipmentSpecialServices,
      emailNotificationDetail,
      variableHandlingChargeDetail,
      customsClearanceDetail,
      smartPostInfoDetail,
      blockInsightVisibility,
      shippingDocumentSpecification,
      rateRequestType,
      preferredCurrency
    )
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The descriptive data of the requested shipment.
  *
  * @param shipper
  *   Indicate the Shipper contact details for this shipment.
  * @param recipients
  *   Indicate the descriptive data for the recipient location to which the shipment is to be received.
  * @param pickupType
  *   Indicates if shipment is being dropped off at a FedEx location or being picked up by FedEx or if it's a regularly scheduled pickup for
  *   this shipment. Required for FedEx Express and Ground Shipment.<br>Example: USE_SCHEDULED_PICKUP
  * @param serviceType
  *   Indicate the FedEx service type used for this shipment.<br>Example: STANDARD_OVERNIGHT<br><a
  *   onclick='loadDocReference("servicetypes")'>click here to see Service Types</a>
  * @param packagingType
  *   Specify the packaging used.<br>Note: For Express Freight shipments, the packaging will default to YOUR_PACKAGING irrespective of the
  *   user provided package type in the request.<br>Example: FEDEX_PAK<br><a onclick='loadDocReference("packagetypes")'>click here to see
  *   Package Types</a>
  * @param totalWeight
  *   Indicate the shipment total weight in pounds.<br>Example: 10.6<br>Note: <ul><li>This only applies to International shipments and
  *   should be used on the first package of a multiple piece shipment.</li><li>This value contains 1 explicit decimal position.</li><li>For
  *   one Label at a time shipments, the unit of totalWeight is considered same as the unit of weight provided in requestedPackageLineItem
  *   field.</li></ul>
  * @param requestedPackageLineItems
  *   These are one or more package-attribute descriptions, each of which describes an individual package, a group of identical packages, or
  *   (for the total-piece-total-weight case) common characteristics of all packages in the shipment.<ul><li>At least one instance
  *   containing the weight for at least one package is required for EXPRESS and GROUND shipments.</li><li>Single piece requests will have
  *   one RequestedPackageLineItem.</li><li>Multiple piece requests will have multiple RequestedPackageLineItems.</li><li>Maximum
  *   occurrences is 30.</li></ul>
  * @param shipDatestamp
  *   This is the shipment date. Default value is current date in case the date is not provided or a past date is provided.<br>Format
  *   [YYYY-MM-DD].<br>Example: 2019-10-14
  * @param totalDeclaredValue
  *   It is the sum of all declared values of all packages in a shipment. The amount of totalDeclaredValue must be equal to the sum of all
  *   the individual declaredValues in the shipment. The declaredValue and totalDeclaredValue must match in all currencies in one shipment.
  *   This value represents FedEx maximum liability associated with a shipment. This is including, but not limited to any loss, damage,
  *   delay, misdelivery, any failure to provide information, or misdelivery of information related to the Shipment.<br><i>Note: The
  *   totalDeclaredValue should not exceed customsValue.</i>
  * @param soldTo
  *   Will indicate the party responsible for purchasing the goods shipped from the shipper to the recipient. The sold to party is not
  *   necessarily the recipient or the importer of record. The sold to party is relevant when the purchaser, rather than the recipient
  *   determines when certain customs regulations apply.
  * @param recipientLocationNumber
  *   A unique identifier for a recipient location.<br>Example:1234567
  * @param origin
  *   Indicate shipment origin address information, if it is different from the shipper address.
  * @param blockInsightVisibility
  *   If true, only the shipper/payer will have visibility of this shipment.<br>Valid Value : true, false.<br> Default:false<br>Example:
  *   true
  * @param rateRequestType
  *   Indicate the type of rates to be returned. The account specific rates are returned by default if the account number is specified in
  *   the request.<br>Following are values:<ul><li>LIST - Returns FedEx published list rates in addition to account-specific rates (if
  *   applicable).</li><li>INCENTIVE - This is one-time discount for incentivising the customer. For more information, contact your FedEx
  *   representative.</li><li>ACCOUNT - Returns account specific rates (Default).</li><li>PREFERRED - Returns rates in the preferred
  *   currency specified in the element preferredCurrency.</li><li>RETAIL - Returns customer rate from one of retail FedEx service
  *   centers.</li></ul>Examples: ["ACCOUNT", "PREFERRED"]
  * @param preferredCurrency
  *   Indicate the currency the caller requests to have used in all returned monetary values. Should be Used in conjunction with the element
  *   RateRequestType.<br>Example: USD<br><a onclick='loadDocReference("currencycodes")'>click here to see available Currency
  *   codes</a><br>Note: Incorrect currency codes should not be supplied. The system ignores the incorrect currency code.
  * @param totalPackageCount
  *   For an MPS, this is the total number of packages in the shipment.Applicable for parent shipment for one label at a time shipments.
  *   <br>Example: 25
  */
case class RequestedShipment(
    shipper: ShipperParty,
    recipients: Seq[RecipientsParty],
    pickupType: RequestedShipment.PickupType,
    serviceType: String,
    packagingType: String,
    totalWeight: Double,
    shippingChargesPayment: Payment,
    labelSpecification: LabelSpecification,
    requestedPackageLineItems: Seq[RequestedPackageLineItem],
    shipDatestamp: Option[String] = None,
    totalDeclaredValue: Option[Money] = None,
    soldTo: Option[SoldToParty] = None,
    recipientLocationNumber: Option[String] = None,
    origin: Option[ContactAndAddress1] = None,
    shipmentSpecialServices: Option[ShipmentSpecialServicesRequested] = None,
    emailNotificationDetail: Option[ShipShipmentEMailNotificationDetail] = None,
    expressFreightDetail: Option[ExpressFreightDetail] = None,
    variableHandlingChargeDetail: Option[VariableHandlingChargeDetail] = None,
    customsClearanceDetail: Option[CustomsClearanceDetail] = None,
    smartPostInfoDetail: Option[SmartPostInfoDetail] = None,
    blockInsightVisibility: Option[Boolean] = None,
    shippingDocumentSpecification: Option[ShippingDocumentSpecification] = None,
    rateRequestType: Option[Seq[RequestedShipment.RateRequestType]] = None,
    preferredCurrency: Option[String] = None,
    totalPackageCount: Option[Int] = None,
    masterTrackingId: Option[MasterTrackingId] = None
)

object RequestedShipment {
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
    case CONTACT_FEDEX_TO_SCHEDULE
    case DROPOFF_AT_FEDEX_LOCATION
    case USE_SCHEDULED_PICKUP
  }
  object PickupType {
    given Encoder[PickupType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[PickupType] = Decoder.decodeString.emapTry(s => scala.util.Try(PickupType.valueOf(s)))
  }
  given Encoder[RequestedShipment] = new Encoder.AsObject[RequestedShipment] {
    final def encodeObject(o: RequestedShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "shipper"                       -> o.shipper.asJson,
          "recipients"                    -> o.recipients.asJson,
          "pickupType"                    -> o.pickupType.asJson,
          "serviceType"                   -> o.serviceType.asJson,
          "packagingType"                 -> o.packagingType.asJson,
          "totalWeight"                   -> o.totalWeight.asJson,
          "shippingChargesPayment"        -> o.shippingChargesPayment.asJson,
          "labelSpecification"            -> o.labelSpecification.asJson,
          "requestedPackageLineItems"     -> o.requestedPackageLineItems.asJson,
          "shipDatestamp"                 -> o.shipDatestamp.asJson,
          "totalDeclaredValue"            -> o.totalDeclaredValue.asJson,
          "soldTo"                        -> o.soldTo.asJson,
          "recipientLocationNumber"       -> o.recipientLocationNumber.asJson,
          "origin"                        -> o.origin.asJson,
          "shipmentSpecialServices"       -> o.shipmentSpecialServices.asJson,
          "emailNotificationDetail"       -> o.emailNotificationDetail.asJson,
          "expressFreightDetail"          -> o.expressFreightDetail.asJson,
          "variableHandlingChargeDetail"  -> o.variableHandlingChargeDetail.asJson,
          "customsClearanceDetail"        -> o.customsClearanceDetail.asJson,
          "smartPostInfoDetail"           -> o.smartPostInfoDetail.asJson,
          "blockInsightVisibility"        -> o.blockInsightVisibility.asJson,
          "shippingDocumentSpecification" -> o.shippingDocumentSpecification.asJson,
          "rateRequestType"               -> o.rateRequestType.asJson,
          "preferredCurrency"             -> o.preferredCurrency.asJson,
          "totalPackageCount"             -> o.totalPackageCount.asJson,
          "masterTrackingId"              -> o.masterTrackingId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RequestedShipment] = (c: HCursor) => {
    for {
      shipper                       <- c.downField("shipper").as[ShipperParty]
      recipients                    <- c.downField("recipients").as[Seq[RecipientsParty]]
      pickupType                    <- c.downField("pickupType").as[PickupType]
      serviceType                   <- c.downField("serviceType").as[String]
      packagingType                 <- c.downField("packagingType").as[String]
      totalWeight                   <- c.downField("totalWeight").as[Double]
      shippingChargesPayment        <- c.downField("shippingChargesPayment").as[Payment]
      labelSpecification            <- c.downField("labelSpecification").as[LabelSpecification]
      requestedPackageLineItems     <- c.downField("requestedPackageLineItems").as[Seq[RequestedPackageLineItem]]
      shipDatestamp                 <- c.downField("shipDatestamp").as[Option[String]]
      totalDeclaredValue            <- c.downField("totalDeclaredValue").as[Option[Money]]
      soldTo                        <- c.downField("soldTo").as[Option[SoldToParty]]
      recipientLocationNumber       <- c.downField("recipientLocationNumber").as[Option[String]]
      origin                        <- c.downField("origin").as[Option[ContactAndAddress1]]
      shipmentSpecialServices       <- c.downField("shipmentSpecialServices").as[Option[ShipmentSpecialServicesRequested]]
      emailNotificationDetail       <- c.downField("emailNotificationDetail").as[Option[ShipShipmentEMailNotificationDetail]]
      expressFreightDetail          <- c.downField("expressFreightDetail").as[Option[ExpressFreightDetail]]
      variableHandlingChargeDetail  <- c.downField("variableHandlingChargeDetail").as[Option[VariableHandlingChargeDetail]]
      customsClearanceDetail        <- c.downField("customsClearanceDetail").as[Option[CustomsClearanceDetail]]
      smartPostInfoDetail           <- c.downField("smartPostInfoDetail").as[Option[SmartPostInfoDetail]]
      blockInsightVisibility        <- c.downField("blockInsightVisibility").as[Option[Boolean]]
      shippingDocumentSpecification <- c.downField("shippingDocumentSpecification").as[Option[ShippingDocumentSpecification]]
      rateRequestType               <- c.downField("rateRequestType").as[Option[Seq[RateRequestType]]]
      preferredCurrency             <- c.downField("preferredCurrency").as[Option[String]]
      totalPackageCount             <- c.downField("totalPackageCount").as[Option[Int]]
      masterTrackingId              <- c.downField("masterTrackingId").as[Option[MasterTrackingId]]
    } yield RequestedShipment(
      shipper,
      recipients,
      pickupType,
      serviceType,
      packagingType,
      totalWeight,
      shippingChargesPayment,
      labelSpecification,
      requestedPackageLineItems,
      shipDatestamp,
      totalDeclaredValue,
      soldTo,
      recipientLocationNumber,
      origin,
      shipmentSpecialServices,
      emailNotificationDetail,
      expressFreightDetail,
      variableHandlingChargeDetail,
      customsClearanceDetail,
      smartPostInfoDetail,
      blockInsightVisibility,
      shippingDocumentSpecification,
      rateRequestType,
      preferredCurrency,
      totalPackageCount,
      masterTrackingId
    )
  }
}

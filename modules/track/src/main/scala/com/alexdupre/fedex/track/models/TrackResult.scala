package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Contains detailed tracking information for the requested packages(s). In case of duplicates, multiple track details will be populated.
  *
  * @param consolidationDetail
  *   Indicates the consolidation details.
  * @param meterNumber
  *   The associated meter number for your FedEx account number. Maximum of 9 characters. <br> Example: 8468376
  * @param destinationLocation
  *   Location details for the recipient where the package will be or has been delivered.
  * @param informationNotes
  *   Notifications to the end user that provide additional information relevant to the tracked shipment. For example, a notification may
  *   indicate that a change in behavior has occurred.
  * @param specialHandlings
  *   Indicate the special handlings on the package being tracked. Includes Special handlings requested for the package like signature
  *   options, Broker select or COD etc.<br><a onclick='loadDocReference("fedexexpressspecialhandlingcodes")'>Click here to see FedEx
  *   Express Special Handling Codes.</a>
  * @param availableImages
  *   The available tracking documents for the shipment being tracked. Available tracking documents includes SPOD and Bill of lading.
  * @param scanEvents
  *   FedEx scan event information for a shipment. Includes the list of events and/or scans applied.
  * @param dateAndTimes
  *   Date and time information for the tracked shipment. Date and time information for the tracked shipment includes various type of date
  *   time including when the package was shipped, when it is expected to deliver, when it is actually delivered etc.
  * @param goodsClassificationCode
  *   Classification codes for the goods in package. Goods classification codes required for clearance purpose. <br> Example:
  *   goodsClassificationCode
  * @param holdAtLocation
  *   Location details for the FedEx facility holding package for delivery. Populated only when REDIRECT_TO_HOLD_AT_LOCATION is available as
  *   custom delivery options.
  * @param customDeliveryOptions
  *   List of delivery options that can be used to customize the delivery of the package.
  * @param estimatedDeliveryTimeWindow
  *   The estimated window for time of delivery. May be periodically updated based on available in-flight shipment information.
  * @param pieceCounts
  *   Piece count information at origin and destination.
  * @param recipientInformation
  *   Contact and address information of recipient.
  * @param standardTransitTimeWindow
  *   The standard committed window of time by which the package is expected to be delivered.
  * @param availableNotifications
  *   The types of email notifications that are available for the package. <br> Example:ON_DELIVERY
  * @param shipperInformation
  *   Contact and address information of shipper.
  * @param lastUpdatedDestinationAddress
  *   Last updated delivery address for the package.
  */
case class TrackResult(
    trackingNumberInfo: Option[TrackingNumberInfo1] = None,
    additionalTrackingInfo: Option[AdditionalTrackingInfo] = None,
    distanceToDestination: Option[Distance] = None,
    consolidationDetail: Option[Seq[ConsolidationDetail]] = None,
    meterNumber: Option[String] = None,
    returnDetail: Option[ReturnDetail] = None,
    serviceDetail: Option[ServiceDescriptionDetail] = None,
    destinationLocation: Option[LocationDetail1] = None,
    latestStatusDetail: Option[StatusDetail] = None,
    serviceCommitMessage: Option[ServiceCommitMessage] = None,
    informationNotes: Option[Seq[InformationNoteDetail]] = None,
    error: Option[CXSError] = None,
    specialHandlings: Option[Seq[TrackSpecialHandling]] = None,
    availableImages: Option[Seq[AvailableImagesDetail]] = None,
    deliveryDetails: Option[DeliveryDetails] = None,
    scanEvents: Option[Seq[ScanEvent]] = None,
    dateAndTimes: Option[Seq[TrackingDateAndTime]] = None,
    packageDetails: Option[PackageDetail] = None,
    goodsClassificationCode: Option[String] = None,
    holdAtLocation: Option[LocationDetail1] = None,
    customDeliveryOptions: Option[Seq[CustomDeliveryOption]] = None,
    estimatedDeliveryTimeWindow: Option[TimeWindow] = None,
    pieceCounts: Option[Seq[PieceCountDetail]] = None,
    originLocation: Option[OriginLocation] = None,
    recipientInformation: Option[ContactAndAddress1] = None,
    standardTransitTimeWindow: Option[TimeWindow] = None,
    shipmentDetails: Option[TrackShipmentDetail] = None,
    reasonDetail: Option[ReasonDetail] = None,
    availableNotifications: Option[Seq[String]] = None,
    shipperInformation: Option[ContactAndAddress1] = None,
    lastUpdatedDestinationAddress: Option[AddressVO1] = None
)

object TrackResult {

  given Encoder[TrackResult] = new Encoder.AsObject[TrackResult] {
    final def encodeObject(o: TrackResult): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumberInfo"            -> o.trackingNumberInfo.asJson,
          "additionalTrackingInfo"        -> o.additionalTrackingInfo.asJson,
          "distanceToDestination"         -> o.distanceToDestination.asJson,
          "consolidationDetail"           -> o.consolidationDetail.asJson,
          "meterNumber"                   -> o.meterNumber.asJson,
          "returnDetail"                  -> o.returnDetail.asJson,
          "serviceDetail"                 -> o.serviceDetail.asJson,
          "destinationLocation"           -> o.destinationLocation.asJson,
          "latestStatusDetail"            -> o.latestStatusDetail.asJson,
          "serviceCommitMessage"          -> o.serviceCommitMessage.asJson,
          "informationNotes"              -> o.informationNotes.asJson,
          "error"                         -> o.error.asJson,
          "specialHandlings"              -> o.specialHandlings.asJson,
          "availableImages"               -> o.availableImages.asJson,
          "deliveryDetails"               -> o.deliveryDetails.asJson,
          "scanEvents"                    -> o.scanEvents.asJson,
          "dateAndTimes"                  -> o.dateAndTimes.asJson,
          "packageDetails"                -> o.packageDetails.asJson,
          "goodsClassificationCode"       -> o.goodsClassificationCode.asJson,
          "holdAtLocation"                -> o.holdAtLocation.asJson,
          "customDeliveryOptions"         -> o.customDeliveryOptions.asJson,
          "estimatedDeliveryTimeWindow"   -> o.estimatedDeliveryTimeWindow.asJson,
          "pieceCounts"                   -> o.pieceCounts.asJson,
          "originLocation"                -> o.originLocation.asJson,
          "recipientInformation"          -> o.recipientInformation.asJson,
          "standardTransitTimeWindow"     -> o.standardTransitTimeWindow.asJson,
          "shipmentDetails"               -> o.shipmentDetails.asJson,
          "reasonDetail"                  -> o.reasonDetail.asJson,
          "availableNotifications"        -> o.availableNotifications.asJson,
          "shipperInformation"            -> o.shipperInformation.asJson,
          "lastUpdatedDestinationAddress" -> o.lastUpdatedDestinationAddress.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackResult] = (c: HCursor) => {
    for {
      trackingNumberInfo            <- c.downField("trackingNumberInfo").as[Option[TrackingNumberInfo1]]
      additionalTrackingInfo        <- c.downField("additionalTrackingInfo").as[Option[AdditionalTrackingInfo]]
      distanceToDestination         <- c.downField("distanceToDestination").as[Option[Distance]]
      consolidationDetail           <- c.downField("consolidationDetail").as[Option[Seq[ConsolidationDetail]]]
      meterNumber                   <- c.downField("meterNumber").as[Option[String]]
      returnDetail                  <- c.downField("returnDetail").as[Option[ReturnDetail]]
      serviceDetail                 <- c.downField("serviceDetail").as[Option[ServiceDescriptionDetail]]
      destinationLocation           <- c.downField("destinationLocation").as[Option[LocationDetail1]]
      latestStatusDetail            <- c.downField("latestStatusDetail").as[Option[StatusDetail]]
      serviceCommitMessage          <- c.downField("serviceCommitMessage").as[Option[ServiceCommitMessage]]
      informationNotes              <- c.downField("informationNotes").as[Option[Seq[InformationNoteDetail]]]
      error                         <- c.downField("error").as[Option[CXSError]]
      specialHandlings              <- c.downField("specialHandlings").as[Option[Seq[TrackSpecialHandling]]]
      availableImages               <- c.downField("availableImages").as[Option[Seq[AvailableImagesDetail]]]
      deliveryDetails               <- c.downField("deliveryDetails").as[Option[DeliveryDetails]]
      scanEvents                    <- c.downField("scanEvents").as[Option[Seq[ScanEvent]]]
      dateAndTimes                  <- c.downField("dateAndTimes").as[Option[Seq[TrackingDateAndTime]]]
      packageDetails                <- c.downField("packageDetails").as[Option[PackageDetail]]
      goodsClassificationCode       <- c.downField("goodsClassificationCode").as[Option[String]]
      holdAtLocation                <- c.downField("holdAtLocation").as[Option[LocationDetail1]]
      customDeliveryOptions         <- c.downField("customDeliveryOptions").as[Option[Seq[CustomDeliveryOption]]]
      estimatedDeliveryTimeWindow   <- c.downField("estimatedDeliveryTimeWindow").as[Option[TimeWindow]]
      pieceCounts                   <- c.downField("pieceCounts").as[Option[Seq[PieceCountDetail]]]
      originLocation                <- c.downField("originLocation").as[Option[OriginLocation]]
      recipientInformation          <- c.downField("recipientInformation").as[Option[ContactAndAddress1]]
      standardTransitTimeWindow     <- c.downField("standardTransitTimeWindow").as[Option[TimeWindow]]
      shipmentDetails               <- c.downField("shipmentDetails").as[Option[TrackShipmentDetail]]
      reasonDetail                  <- c.downField("reasonDetail").as[Option[ReasonDetail]]
      availableNotifications        <- c.downField("availableNotifications").as[Option[Seq[String]]]
      shipperInformation            <- c.downField("shipperInformation").as[Option[ContactAndAddress1]]
      lastUpdatedDestinationAddress <- c.downField("lastUpdatedDestinationAddress").as[Option[AddressVO1]]
    } yield TrackResult(
      trackingNumberInfo,
      additionalTrackingInfo,
      distanceToDestination,
      consolidationDetail,
      meterNumber,
      returnDetail,
      serviceDetail,
      destinationLocation,
      latestStatusDetail,
      serviceCommitMessage,
      informationNotes,
      error,
      specialHandlings,
      availableImages,
      deliveryDetails,
      scanEvents,
      dateAndTimes,
      packageDetails,
      goodsClassificationCode,
      holdAtLocation,
      customDeliveryOptions,
      estimatedDeliveryTimeWindow,
      pieceCounts,
      originLocation,
      recipientInformation,
      standardTransitTimeWindow,
      shipmentDetails,
      reasonDetail,
      availableNotifications,
      shipperInformation,
      lastUpdatedDestinationAddress
    )
  }
}

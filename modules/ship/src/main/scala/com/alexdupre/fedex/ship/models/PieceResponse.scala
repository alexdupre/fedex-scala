package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Piece Response information.
  *
  * @param netChargeAmount
  *   Indicates the net charges amount.<br>Example: 21.45
  * @param packageDocuments
  *   These are package documents returned in the response.
  * @param acceptanceTrackingNumber
  *   Indicates the acceptance tracking number.<br>Example: 7949XXXXX5000
  * @param serviceCategory
  *   Indicates the service category.
  * @param listCustomerTotalCharge
  *   Indicates total charges applicable to the customer.<br>Example: listCustomerTotalCharge
  * @param deliveryTimestamp
  *   Indicates delivery date with timestamp.<br>Example: 2012-09-23
  * @param trackingIdType
  *   Indicates the type of the tracking identifier.
  * @param additionalChargesDiscount
  *   These are additional charges or discounts.<br>Example: 621.45
  * @param listRateAmount
  *   Indicates the net List rate amount.<br>Example: 1.45
  * @param baseRateAmount
  *   Specifies the base rate amount.<br>Example: 321.45
  * @param packageSequenceNumber
  *   Indicates package sequence number.<br>Example: 215
  * @param netDiscountAmount
  *   Specifies the net discount amount.<br>Example: 121.45
  * @param codCollectionAmount
  *   Specifies the Collect on Delivery collection amount.<br>Example: 231.45
  * @param masterTrackingNumber
  *   This is a master tracking number of the shipment (must be unique for stand-alone open shipments, or unique within consolidation if
  *   consolidation key is provided).<br>Example: 794XXXXX5000
  * @param acceptanceType
  *   Indicates acceptance type.<br>Example: acceptanceType<br>Example:acceptanceType
  * @param trackingNumber
  *   This is the tracking number associated with this package. <br>Example: 49XXX0000XXX20032835
  * @param customerReferences
  *   These are additional customer reference data.<br>Note: The groupPackageCount must be specified to retrieve customer references.
  */
case class PieceResponse(
    netChargeAmount: Option[Double] = None,
    packageDocuments: Option[Seq[LabelResponseVO]] = None,
    acceptanceTrackingNumber: Option[String] = None,
    serviceCategory: Option[PieceResponse.ServiceCategory] = None,
    listCustomerTotalCharge: Option[String] = None,
    deliveryTimestamp: Option[String] = None,
    trackingIdType: Option[String] = None,
    additionalChargesDiscount: Option[Double] = None,
    listRateAmount: Option[Double] = None,
    baseRateAmount: Option[Double] = None,
    packageSequenceNumber: Option[Int] = None,
    netDiscountAmount: Option[Double] = None,
    codCollectionAmount: Option[Double] = None,
    masterTrackingNumber: Option[String] = None,
    acceptanceType: Option[String] = None,
    trackingNumber: Option[String] = None,
    customerReferences: Option[Seq[CustomerReference]] = None
)

object PieceResponse {
  enum ServiceCategory {
    case EXPRESS
    case GROUND
    case EXPRESS_FREIGHT
    case FREIGHT
    case SMARTPOST
    case EXPRESS_PARCEL
    case NULL
    case UNKNOWN_DEFAULT
  }
  object ServiceCategory {
    given Encoder[ServiceCategory] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ServiceCategory] =
      Decoder.decodeString.map(s => scala.util.Try(ServiceCategory.valueOf(s)).getOrElse(ServiceCategory.UNKNOWN_DEFAULT))
  }
  given Encoder[PieceResponse] = new Encoder.AsObject[PieceResponse] {
    final def encodeObject(o: PieceResponse): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "netChargeAmount"           -> o.netChargeAmount.asJson,
          "packageDocuments"          -> o.packageDocuments.asJson,
          "acceptanceTrackingNumber"  -> o.acceptanceTrackingNumber.asJson,
          "serviceCategory"           -> o.serviceCategory.asJson,
          "listCustomerTotalCharge"   -> o.listCustomerTotalCharge.asJson,
          "deliveryTimestamp"         -> o.deliveryTimestamp.asJson,
          "trackingIdType"            -> o.trackingIdType.asJson,
          "additionalChargesDiscount" -> o.additionalChargesDiscount.asJson,
          "listRateAmount"            -> o.listRateAmount.asJson,
          "baseRateAmount"            -> o.baseRateAmount.asJson,
          "packageSequenceNumber"     -> o.packageSequenceNumber.asJson,
          "netDiscountAmount"         -> o.netDiscountAmount.asJson,
          "codCollectionAmount"       -> o.codCollectionAmount.asJson,
          "masterTrackingNumber"      -> o.masterTrackingNumber.asJson,
          "acceptanceType"            -> o.acceptanceType.asJson,
          "trackingNumber"            -> o.trackingNumber.asJson,
          "customerReferences"        -> o.customerReferences.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PieceResponse] = (c: HCursor) => {
    for {
      netChargeAmount           <- c.downField("netChargeAmount").as[Option[Double]]
      packageDocuments          <- c.downField("packageDocuments").as[Option[Seq[LabelResponseVO]]]
      acceptanceTrackingNumber  <- c.downField("acceptanceTrackingNumber").as[Option[String]]
      serviceCategory           <- c.downField("serviceCategory").as[Option[ServiceCategory]]
      listCustomerTotalCharge   <- c.downField("listCustomerTotalCharge").as[Option[String]]
      deliveryTimestamp         <- c.downField("deliveryTimestamp").as[Option[String]]
      trackingIdType            <- c.downField("trackingIdType").as[Option[String]]
      additionalChargesDiscount <- c.downField("additionalChargesDiscount").as[Option[Double]]
      listRateAmount            <- c.downField("listRateAmount").as[Option[Double]]
      baseRateAmount            <- c.downField("baseRateAmount").as[Option[Double]]
      packageSequenceNumber     <- c.downField("packageSequenceNumber").as[Option[Int]]
      netDiscountAmount         <- c.downField("netDiscountAmount").as[Option[Double]]
      codCollectionAmount       <- c.downField("codCollectionAmount").as[Option[Double]]
      masterTrackingNumber      <- c.downField("masterTrackingNumber").as[Option[String]]
      acceptanceType            <- c.downField("acceptanceType").as[Option[String]]
      trackingNumber            <- c.downField("trackingNumber").as[Option[String]]
      customerReferences        <- c.downField("customerReferences").as[Option[Seq[CustomerReference]]]
    } yield PieceResponse(
      netChargeAmount,
      packageDocuments,
      acceptanceTrackingNumber,
      serviceCategory,
      listCustomerTotalCharge,
      deliveryTimestamp,
      trackingIdType,
      additionalChargesDiscount,
      listRateAmount,
      baseRateAmount,
      packageSequenceNumber,
      netDiscountAmount,
      codCollectionAmount,
      masterTrackingNumber,
      acceptanceType,
      trackingNumber,
      customerReferences
    )
  }
}

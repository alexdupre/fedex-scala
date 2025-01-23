package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** CompletedPackageDetail Model
  *
  * @param sequenceNumber
  *   This is package sequence number. No negative value or decimals are allowed.<br>Example: 256
  * @param signatureOption
  *   It specifies the signature option applied, to allow cases in which the value requested conflicted with other service features in the
  *   shipment. <br>Example: DIRECT
  * @param trackingIds
  *   Tracking details of the package.
  * @param groupNumber
  *   This is group shipment number. Used with request containing PACKAGE_GROUPS, to identify which group of identical packages was used to
  *   produce a reply item.<br>Example: 10
  * @param oversizeClass
  *   Indicates the oversize classification.<br>Example: OVERSIZE_1
  * @param dryIceWeight
  *   Descriptive data required for a FedEx shipment containing dry ice. Includes weight and units. This element is required when
  *   SpecialServiceType DRY_ICE is present in the SpecialServiceTypes collection at the package level.
  */
case class CompletedPackageDetail(
    sequenceNumber: Option[Int] = None,
    operationalDetail: Option[PackageOperationalDetail] = None,
    signatureOption: Option[String] = None,
    trackingIds: Option[Seq[TrackingId]] = None,
    groupNumber: Option[Int] = None,
    oversizeClass: Option[String] = None,
    packageRating: Option[PackageRating] = None,
    dryIceWeight: Option[Weight] = None,
    hazardousPackageDetail: Option[CompletedHazardousPackageDetail] = None
)

object CompletedPackageDetail {

  given Encoder[CompletedPackageDetail] = new Encoder.AsObject[CompletedPackageDetail] {
    final def encodeObject(o: CompletedPackageDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "sequenceNumber"         -> o.sequenceNumber.asJson,
          "operationalDetail"      -> o.operationalDetail.asJson,
          "signatureOption"        -> o.signatureOption.asJson,
          "trackingIds"            -> o.trackingIds.asJson,
          "groupNumber"            -> o.groupNumber.asJson,
          "oversizeClass"          -> o.oversizeClass.asJson,
          "packageRating"          -> o.packageRating.asJson,
          "dryIceWeight"           -> o.dryIceWeight.asJson,
          "hazardousPackageDetail" -> o.hazardousPackageDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CompletedPackageDetail] = (c: HCursor) => {
    for {
      sequenceNumber         <- c.downField("sequenceNumber").as[Option[Int]]
      operationalDetail      <- c.downField("operationalDetail").as[Option[PackageOperationalDetail]]
      signatureOption        <- c.downField("signatureOption").as[Option[String]]
      trackingIds            <- c.downField("trackingIds").as[Option[Seq[TrackingId]]]
      groupNumber            <- c.downField("groupNumber").as[Option[Int]]
      oversizeClass          <- c.downField("oversizeClass").as[Option[String]]
      packageRating          <- c.downField("packageRating").as[Option[PackageRating]]
      dryIceWeight           <- c.downField("dryIceWeight").as[Option[Weight]]
      hazardousPackageDetail <- c.downField("hazardousPackageDetail").as[Option[CompletedHazardousPackageDetail]]
    } yield CompletedPackageDetail(
      sequenceNumber,
      operationalDetail,
      signatureOption,
      trackingIds,
      groupNumber,
      oversizeClass,
      packageRating,
      dryIceWeight,
      hazardousPackageDetail
    )
  }
}

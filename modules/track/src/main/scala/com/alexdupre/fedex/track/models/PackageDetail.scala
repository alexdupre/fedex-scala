package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Details of the packages in the shipment being tracked. Includes details like package type, weight, dimensions, declared value, etc.
  *
  * @param physicalPackagingType
  *   Indicate the physical package type for non-Express shipments.<br><a onclick='loadDocReference("subpackagetypes")'>Click here to see
  *   Available Types</a>
  * @param sequenceNumber
  *   Field which holds the number representing which package in a multi-piece shipment applies to this TrackDetail.<br> Example: 45
  * @param undeliveredCount
  *   Field which holds information about total count of the undelivered packages in the shipment. <br> Example: 7
  * @param count
  *   Field which holds the total number of pieces in the shipment which includes the package represented by this TrackDetail.<br> Example:
  *   1
  * @param packageContent
  *   Field which holds information about the package content of the shipment. Populated for secure users only.<br> Example: wire hangers.
  * @param contentPieceCount
  *   Field which holds information about total count of the packages in the shipment.<br> Example: 100
  * @param declaredValue
  *   This is the declared value. Declared Value represents FedEx maximum liability in connection with a shipment of that Package, including
  *   but not limited to, any loss, damage, delay, misdelivery, any failure to provide information, or misdelivery of information relating
  *   to the Shipment.
  */
case class PackageDetail(
    physicalPackagingType: Option[String] = None,
    sequenceNumber: Option[String] = None,
    undeliveredCount: Option[String] = None,
    packagingDescription: Option[PackagingDescription] = None,
    count: Option[String] = None,
    weightAndDimensions: Option[TrackingWeightAndDimensions] = None,
    packageContent: Option[Seq[String]] = None,
    contentPieceCount: Option[String] = None,
    declaredValue: Option[Amount] = None
)

object PackageDetail {

  given Encoder[PackageDetail] = new Encoder.AsObject[PackageDetail] {
    final def encodeObject(o: PackageDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "physicalPackagingType" -> o.physicalPackagingType.asJson,
          "sequenceNumber"        -> o.sequenceNumber.asJson,
          "undeliveredCount"      -> o.undeliveredCount.asJson,
          "packagingDescription"  -> o.packagingDescription.asJson,
          "count"                 -> o.count.asJson,
          "weightAndDimensions"   -> o.weightAndDimensions.asJson,
          "packageContent"        -> o.packageContent.asJson,
          "contentPieceCount"     -> o.contentPieceCount.asJson,
          "declaredValue"         -> o.declaredValue.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageDetail] = (c: HCursor) => {
    for {
      physicalPackagingType <- c.downField("physicalPackagingType").as[Option[String]]
      sequenceNumber        <- c.downField("sequenceNumber").as[Option[String]]
      undeliveredCount      <- c.downField("undeliveredCount").as[Option[String]]
      packagingDescription  <- c.downField("packagingDescription").as[Option[PackagingDescription]]
      count                 <- c.downField("count").as[Option[String]]
      weightAndDimensions   <- c.downField("weightAndDimensions").as[Option[TrackingWeightAndDimensions]]
      packageContent        <- c.downField("packageContent").as[Option[Seq[String]]]
      contentPieceCount     <- c.downField("contentPieceCount").as[Option[String]]
      declaredValue         <- c.downField("declaredValue").as[Option[Amount]]
    } yield PackageDetail(
      physicalPackagingType,
      sequenceNumber,
      undeliveredCount,
      packagingDescription,
      count,
      weightAndDimensions,
      packageContent,
      contentPieceCount,
      declaredValue
    )
  }
}

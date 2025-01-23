package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are special services that are available at the package level for some or all service types.
  *
  * @param specialServiceTypes
  *   The list of all special services requested for the package.<br><a
  *   href="/developer-portal/en-us/reference-guide.html#packagespecialservicetypes" target="_blank">Click here to see Package Special
  *   Service Types</a><br>Example:ALCOHOL
  * @param signatureOptionType
  *   Signature Option Type<br/>ADULT - Adult signature required, at recipient''s address.<br/>DIRECT - Signature required, at recipient''s
  *   address.<br>INDIRECT - Signature required, alternate address is accepted.<br/>NO_SIGNATURE_REQUIRED - Signature is not
  *   required.<br/>SERVICE_DEFAULT - Signature handled as per current Service Guide.<br>Example:ADULT
  * @param pieceCountVerificationBoxCount
  *   Provide the pieceCount or VerificationBoxCount for batteries or cells that are contained within this specific package.<br>Example:1
  * @param batteryDetails
  *   Provide details about the batteries or cells that are contained within this specific package.
  * @param standaloneBatteryDetails
  *   Provide details about the batteries or cells that are contained within this specific package.
  */
case class PackageSpecialServicesRequested(
    specialServiceTypes: Option[Seq[String]] = None,
    signatureOptionType: Option[PackageSpecialServicesRequested.SignatureOptionType] = None,
    priorityAlertDetail: Option[PriorityAlertDetail] = None,
    signatureOptionDetail: Option[SignatureOptionDetail] = None,
    alcoholDetail: Option[AlcoholDetail] = None,
    dangerousGoodsDetail: Option[DangerousGoodsDetail] = None,
    packageCODDetail: Option[PackageCODDetail] = None,
    pieceCountVerificationBoxCount: Option[Int] = None,
    batteryDetails: Option[Seq[BatteryDetail]] = None,
    dryIceWeight: Option[Weight3] = None,
    standaloneBatteryDetails: Option[Seq[StandaloneBatteryDetails]] = None
)

object PackageSpecialServicesRequested {
  enum SignatureOptionType {
    case SERVICE_DEFAULT
    case NO_SIGNATURE_REQUIRED
    case INDIRECT
    case DIRECT
    case ADULT
  }
  object SignatureOptionType {
    given Encoder[SignatureOptionType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[SignatureOptionType] = Decoder.decodeString.emapTry(s => scala.util.Try(SignatureOptionType.valueOf(s)))
  }
  given Encoder[PackageSpecialServicesRequested] = new Encoder.AsObject[PackageSpecialServicesRequested] {
    final def encodeObject(o: PackageSpecialServicesRequested): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "specialServiceTypes"            -> o.specialServiceTypes.asJson,
          "signatureOptionType"            -> o.signatureOptionType.asJson,
          "priorityAlertDetail"            -> o.priorityAlertDetail.asJson,
          "signatureOptionDetail"          -> o.signatureOptionDetail.asJson,
          "alcoholDetail"                  -> o.alcoholDetail.asJson,
          "dangerousGoodsDetail"           -> o.dangerousGoodsDetail.asJson,
          "packageCODDetail"               -> o.packageCODDetail.asJson,
          "pieceCountVerificationBoxCount" -> o.pieceCountVerificationBoxCount.asJson,
          "batteryDetails"                 -> o.batteryDetails.asJson,
          "dryIceWeight"                   -> o.dryIceWeight.asJson,
          "standaloneBatteryDetails"       -> o.standaloneBatteryDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PackageSpecialServicesRequested] = (c: HCursor) => {
    for {
      specialServiceTypes            <- c.downField("specialServiceTypes").as[Option[Seq[String]]]
      signatureOptionType            <- c.downField("signatureOptionType").as[Option[SignatureOptionType]]
      priorityAlertDetail            <- c.downField("priorityAlertDetail").as[Option[PriorityAlertDetail]]
      signatureOptionDetail          <- c.downField("signatureOptionDetail").as[Option[SignatureOptionDetail]]
      alcoholDetail                  <- c.downField("alcoholDetail").as[Option[AlcoholDetail]]
      dangerousGoodsDetail           <- c.downField("dangerousGoodsDetail").as[Option[DangerousGoodsDetail]]
      packageCODDetail               <- c.downField("packageCODDetail").as[Option[PackageCODDetail]]
      pieceCountVerificationBoxCount <- c.downField("pieceCountVerificationBoxCount").as[Option[Int]]
      batteryDetails                 <- c.downField("batteryDetails").as[Option[Seq[BatteryDetail]]]
      dryIceWeight                   <- c.downField("dryIceWeight").as[Option[Weight3]]
      standaloneBatteryDetails       <- c.downField("standaloneBatteryDetails").as[Option[Seq[StandaloneBatteryDetails]]]
    } yield PackageSpecialServicesRequested(
      specialServiceTypes,
      signatureOptionType,
      priorityAlertDetail,
      signatureOptionDetail,
      alcoholDetail,
      dangerousGoodsDetail,
      packageCODDetail,
      pieceCountVerificationBoxCount,
      batteryDetails,
      dryIceWeight,
      standaloneBatteryDetails
    )
  }
}

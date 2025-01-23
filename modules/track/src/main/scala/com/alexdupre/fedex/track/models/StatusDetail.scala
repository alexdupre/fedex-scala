package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies details about the status of the track information for the shipment being tracked. AncilliaryDetails may also be available
  * which describe the cause of exception along with any action that needs to taken by customer.
  *
  * @param scanLocation
  *   Address information related to the associated Status.
  * @param code
  *   A code that identifies this type of status.<br> Example:PU,DE,DP,HL,OC
  * @param derivedCode
  *   Specifies the latest status detail code of the package.<br> Example:PU,DE,DP,HL,OC
  * @param ancillaryDetails
  *   Specifies the cause of exception along with any action that needs to taken by customer.
  * @param statusByLocale
  *   This is the latest tracking status by locale.<br> Example: Picked up
  * @param description
  *   A human-readable description of this status.<br> Example: Picked up
  */
case class StatusDetail(
    scanLocation: Option[AddressVO1] = None,
    code: Option[String] = None,
    derivedCode: Option[String] = None,
    ancillaryDetails: Option[Seq[StatusAncillaryDetail]] = None,
    statusByLocale: Option[String] = None,
    description: Option[String] = None,
    delayDetail: Option[DelayDetail] = None
)

object StatusDetail {

  given Encoder[StatusDetail] = new Encoder.AsObject[StatusDetail] {
    final def encodeObject(o: StatusDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "scanLocation"     -> o.scanLocation.asJson,
          "code"             -> o.code.asJson,
          "derivedCode"      -> o.derivedCode.asJson,
          "ancillaryDetails" -> o.ancillaryDetails.asJson,
          "statusByLocale"   -> o.statusByLocale.asJson,
          "description"      -> o.description.asJson,
          "delayDetail"      -> o.delayDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[StatusDetail] = (c: HCursor) => {
    for {
      scanLocation     <- c.downField("scanLocation").as[Option[AddressVO1]]
      code             <- c.downField("code").as[Option[String]]
      derivedCode      <- c.downField("derivedCode").as[Option[String]]
      ancillaryDetails <- c.downField("ancillaryDetails").as[Option[Seq[StatusAncillaryDetail]]]
      statusByLocale   <- c.downField("statusByLocale").as[Option[String]]
      description      <- c.downField("description").as[Option[String]]
      delayDetail      <- c.downField("delayDetail").as[Option[DelayDetail]]
    } yield StatusDetail(scanLocation, code, derivedCode, ancillaryDetails, statusByLocale, description, delayDetail)
  }
}

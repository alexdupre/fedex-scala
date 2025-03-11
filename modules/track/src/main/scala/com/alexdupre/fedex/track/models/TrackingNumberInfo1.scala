package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Information uniquely identifying a shipment such as Tracking number, ShipDate, and Tracking number uniqueId.
  *
  * @param trackingNumber
  *   This is a Tracking number for FedEx packages used for tracking a single package or group of packages. <br> Example: 128667043726<br><a
  *   onclick='loadDocReference("mocktrackingnumbersforfedexexpressandfedexground")'>Click here to see Mock Tracking Numbers.</a>
  * @param carrierCode
  *   This is a placeholder to provide the FedEx operating company (transportation) code used for package delivery. <br> Example: FDXE
  * @param trackingNumberUniqueId
  *   Unique identifier used to distinguish duplicate FedEx tracking numbers. This value will be set by FedEx systems. <br> Example:
  *   245822\~123456789012\~FDEG
  */
case class TrackingNumberInfo1(
    trackingNumber: Option[String] = None,
    carrierCode: Option[TrackingNumberInfo1.CarrierCode] = None,
    trackingNumberUniqueId: Option[String] = None
)

object TrackingNumberInfo1 {
  enum CarrierCode {
    case FDXE
    case FDXG
    case FXSP
    case FXFR
    case FDXC
    case FXCC
    case FEDEX_CARGO
    case FEDEX_CUSTOM_CRITICAL
    case FEDEX_EXPRESS
    case FEDEX_FREIGHT
    case FEDEX_GROUND
    case FEDEX_OFFICE
    case FEDEX_KINKOS
    case FX
    case FDFR
    case FDEG
    case FXK
    case FDC
    case FDCC
    case UNKNOWN_DEFAULT
  }
  object CarrierCode {
    given Encoder[CarrierCode] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CarrierCode] =
      Decoder.decodeString.map(s => scala.util.Try(CarrierCode.valueOf(s)).getOrElse(CarrierCode.UNKNOWN_DEFAULT))
  }
  given Encoder[TrackingNumberInfo1] = new Encoder.AsObject[TrackingNumberInfo1] {
    final def encodeObject(o: TrackingNumberInfo1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumber"         -> o.trackingNumber.asJson,
          "carrierCode"            -> o.carrierCode.asJson,
          "trackingNumberUniqueId" -> o.trackingNumberUniqueId.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingNumberInfo1] = (c: HCursor) => {
    for {
      trackingNumber         <- c.downField("trackingNumber").as[Option[String]]
      carrierCode            <- c.downField("carrierCode").as[Option[CarrierCode]]
      trackingNumberUniqueId <- c.downField("trackingNumberUniqueId").as[Option[String]]
    } yield TrackingNumberInfo1(trackingNumber, carrierCode, trackingNumberUniqueId)
  }
}

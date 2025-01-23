package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** TrackDocumentSpecification Model
  *
  * @param shipDateBegin
  *   ShipDateBegin and ShipDateEnd are used to narrow the search, reduce lookup time, and avoid duplicates when searching for a specific
  *   tracking number during a specific date range.<br>Format: YYYY-MM-DD<br>example: '2020-03-29'
  * @param shipDateEnd
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number during a specific date range.<br>Format: YYYY-MM-DD<br>example: '2020-04-01'
  * @param accountNumber
  *   Specifies Signature Proof of Delivery(SPOD) account number for the shipment being tracked.<br>Conditionally required when documentType
  *   is BILL_OF_LADING or FREIGHT_BILLING_DOCUMENT<br>Example: 123456789
  */
case class TrackDocumentSpecification(
    trackingNumberInfo: TrackingNumberInfo,
    shipDateBegin: Option[String] = None,
    shipDateEnd: Option[String] = None,
    accountNumber: Option[String] = None
)

object TrackDocumentSpecification {

  given Encoder[TrackDocumentSpecification] = new Encoder.AsObject[TrackDocumentSpecification] {
    final def encodeObject(o: TrackDocumentSpecification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "trackingNumberInfo" -> o.trackingNumberInfo.asJson,
          "shipDateBegin"      -> o.shipDateBegin.asJson,
          "shipDateEnd"        -> o.shipDateEnd.asJson,
          "accountNumber"      -> o.accountNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackDocumentSpecification] = (c: HCursor) => {
    for {
      trackingNumberInfo <- c.downField("trackingNumberInfo").as[TrackingNumberInfo]
      shipDateBegin      <- c.downField("shipDateBegin").as[Option[String]]
      shipDateEnd        <- c.downField("shipDateEnd").as[Option[String]]
      accountNumber      <- c.downField("accountNumber").as[Option[String]]
    } yield TrackDocumentSpecification(trackingNumberInfo, shipDateBegin, shipDateEnd, accountNumber)
  }
}

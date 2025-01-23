package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request to receive a tracking notification.
  *
  * @param senderContactName
  *   Placeholder for Sender contact name.<br> Example: Sam Smith
  * @param senderEMailAddress
  *   Email address of the sender from which the shipment notification will be sent.<br>Example: LSR123@gmail.com
  * @param shipDateBegin
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number in a specific time range. <br>Format: YYYY-MM-DD<br>example:'2019-10-13'
  * @param shipDateEnd
  *   ShipDateBegin and ShipDateEnd are recommended to narrow the search, reduce lookup time, and avoid duplicates when searching for a
  *   specific tracking number in a specific time range. <br>Format: YYYY-MM-DD<br>example:'2019-10-13'
  */
case class FullSchemaNotification(
    senderContactName: String,
    senderEMailAddress: String,
    trackingEventNotificationDetail: TrackingEventNotificationDetail,
    trackingNumberInfo: TrackingNumberInfo,
    shipDateBegin: Option[String] = None,
    shipDateEnd: Option[String] = None
)

object FullSchemaNotification {

  given Encoder[FullSchemaNotification] = new Encoder.AsObject[FullSchemaNotification] {
    final def encodeObject(o: FullSchemaNotification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "senderContactName"               -> o.senderContactName.asJson,
          "senderEMailAddress"              -> o.senderEMailAddress.asJson,
          "trackingEventNotificationDetail" -> o.trackingEventNotificationDetail.asJson,
          "trackingNumberInfo"              -> o.trackingNumberInfo.asJson,
          "shipDateBegin"                   -> o.shipDateBegin.asJson,
          "shipDateEnd"                     -> o.shipDateEnd.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaNotification] = (c: HCursor) => {
    for {
      senderContactName               <- c.downField("senderContactName").as[String]
      senderEMailAddress              <- c.downField("senderEMailAddress").as[String]
      trackingEventNotificationDetail <- c.downField("trackingEventNotificationDetail").as[TrackingEventNotificationDetail]
      trackingNumberInfo              <- c.downField("trackingNumberInfo").as[TrackingNumberInfo]
      shipDateBegin                   <- c.downField("shipDateBegin").as[Option[String]]
      shipDateEnd                     <- c.downField("shipDateEnd").as[Option[String]]
    } yield FullSchemaNotification(
      senderContactName,
      senderEMailAddress,
      trackingEventNotificationDetail,
      trackingNumberInfo,
      shipDateBegin,
      shipDateEnd
    )
  }
}

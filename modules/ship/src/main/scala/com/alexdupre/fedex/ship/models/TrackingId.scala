package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicates the tracking details of the package.
  *
  * @param formId
  *   This is FedEx tracking Identifier associated with the package.<br>Example: 8600
  * @param trackingIdType
  *   Specify the FedEx transportation type. <br>Example: EXPRESS
  * @param uspsApplicationId
  *   Specify the USPS tracking Identifier associated with FedEx SmartPost shipment.<br>Example: 92
  * @param trackingNumber
  *   This is the number associated with the package that is used to track it. <br>Example: 49XXX0000XXX20032835
  */
case class TrackingId(
    formId: Option[String] = None,
    trackingIdType: Option[String] = None,
    uspsApplicationId: Option[String] = None,
    trackingNumber: Option[String] = None
)

object TrackingId {

  given Encoder[TrackingId] = new Encoder.AsObject[TrackingId] {
    final def encodeObject(o: TrackingId): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "formId"            -> o.formId.asJson,
          "trackingIdType"    -> o.trackingIdType.asJson,
          "uspsApplicationId" -> o.uspsApplicationId.asJson,
          "trackingNumber"    -> o.trackingNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TrackingId] = (c: HCursor) => {
    for {
      formId            <- c.downField("formId").as[Option[String]]
      trackingIdType    <- c.downField("trackingIdType").as[Option[String]]
      uspsApplicationId <- c.downField("uspsApplicationId").as[Option[String]]
      trackingNumber    <- c.downField("trackingNumber").as[Option[String]]
    } yield TrackingId(formId, trackingIdType, uspsApplicationId, trackingNumber)
  }
}

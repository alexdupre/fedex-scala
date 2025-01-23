package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The input details required to cancel a tag.
  *
  * @param accountNumber
  *   The specific FedEx customer account number (account value and account key) associated with the shipment.
  * @param serviceType
  *   This is the FedEx service type associated with the shipment.<br>Example: PRIORITY_OVERNIGHT<br><a
  *   onclick='loadDocReference("servicetypes")'>Click here to see Service Types</a>
  * @param completedTagDetail
  *   The details of the package for which shipping has been completed. The details include dispatch confirmation number, dispatch date,
  *   location, and the cxs alerts associated with the process.
  * @param trackingNumber
  *   The tracking number for the Express or Ground Tag to the cancelled.<br>Example: 301025281523<br><a
  *   onclick='loadDocReference("mocktrackingnumbersforfedexexpressandfedexground")'>Click here to see mock tracking numbers for FedEx
  *   Express and FedEx Ground.</a>
  */
case class FullSchemaCancelTag(
    accountNumber: ShipmentAccountNumber,
    serviceType: String,
    completedTagDetail: CompletedTagDetail2,
    trackingNumber: Option[String] = None
)

object FullSchemaCancelTag {

  given Encoder[FullSchemaCancelTag] = new Encoder.AsObject[FullSchemaCancelTag] {
    final def encodeObject(o: FullSchemaCancelTag): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "accountNumber"      -> o.accountNumber.asJson,
          "serviceType"        -> o.serviceType.asJson,
          "completedTagDetail" -> o.completedTagDetail.asJson,
          "trackingNumber"     -> o.trackingNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaCancelTag] = (c: HCursor) => {
    for {
      accountNumber      <- c.downField("accountNumber").as[ShipmentAccountNumber]
      serviceType        <- c.downField("serviceType").as[String]
      completedTagDetail <- c.downField("completedTagDetail").as[CompletedTagDetail2]
      trackingNumber     <- c.downField("trackingNumber").as[Option[String]]
    } yield FullSchemaCancelTag(accountNumber, serviceType, completedTagDetail, trackingNumber)
  }
}

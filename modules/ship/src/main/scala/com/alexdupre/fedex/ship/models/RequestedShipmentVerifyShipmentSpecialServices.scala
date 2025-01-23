package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the shipment special service or handling required for this shipment. <br>Note: <ul><li>If the shipper is requesting a special
  * service, the special service type must be indicated in the object specialServiceTypes, and all supporting detail must be provided in the
  * appropriate sub-object below.</li><li>For returns it is required to provide value RETURN_SHIPMENT in the specialServiceTypes.</li></ul>
  *
  * @param specialServiceTypes
  *   Indicate the Special services requested for this shipment.<br>Example:
  *   <ul><li>HOLD_AT_LOCATION</li><li>RETURN_SHIPMENT</li><li>BROKER_SELECT_OPTION</li><li>CALL_BEFORE_DELIVERY</li><li>COD</li><li>CUSTOM_DELIVERY_WINDOW</li></ul><br><a
  *   onclick='loadDocReference("shipmentlevelspecialservicetypes")'>click here to see Shipment Special Service Types</a>
  */
case class RequestedShipmentVerifyShipmentSpecialServices(
    specialServiceTypes: Option[Seq[String]] = None,
    etdDetail: Option[ETDDetail] = None,
    returnShipmentDetail: Option[ReturnShipmentDetail] = None,
    deliveryOnInvoiceAcceptanceDetail: Option[DeliveryOnInvoiceAcceptanceDetail] = None,
    internationalTrafficInArmsRegulationsDetail: Option[InternationalTrafficInArmsRegulationsDetail] = None,
    pendingShipmentDetail: Option[PendingShipmentDetail] = None,
    holdAtLocationDetail: Option[HoldAtLocationDetail] = None,
    shipmentCODDetail: Option[ShipmentCODDetail] = None,
    shipmentDryIceDetail: Option[ShipmentDryIceDetail1] = None,
    internationalControlledExportDetail: Option[InternationalControlledExportDetail] = None,
    homeDeliveryPremiumDetail: Option[HomeDeliveryPremiumDetail] = None
)

object RequestedShipmentVerifyShipmentSpecialServices {

  given Encoder[RequestedShipmentVerifyShipmentSpecialServices] = new Encoder.AsObject[RequestedShipmentVerifyShipmentSpecialServices] {
    final def encodeObject(o: RequestedShipmentVerifyShipmentSpecialServices): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "specialServiceTypes"                         -> o.specialServiceTypes.asJson,
          "etdDetail"                                   -> o.etdDetail.asJson,
          "returnShipmentDetail"                        -> o.returnShipmentDetail.asJson,
          "deliveryOnInvoiceAcceptanceDetail"           -> o.deliveryOnInvoiceAcceptanceDetail.asJson,
          "internationalTrafficInArmsRegulationsDetail" -> o.internationalTrafficInArmsRegulationsDetail.asJson,
          "pendingShipmentDetail"                       -> o.pendingShipmentDetail.asJson,
          "holdAtLocationDetail"                        -> o.holdAtLocationDetail.asJson,
          "shipmentCODDetail"                           -> o.shipmentCODDetail.asJson,
          "shipmentDryIceDetail"                        -> o.shipmentDryIceDetail.asJson,
          "internationalControlledExportDetail"         -> o.internationalControlledExportDetail.asJson,
          "homeDeliveryPremiumDetail"                   -> o.homeDeliveryPremiumDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RequestedShipmentVerifyShipmentSpecialServices] = (c: HCursor) => {
    for {
      specialServiceTypes               <- c.downField("specialServiceTypes").as[Option[Seq[String]]]
      etdDetail                         <- c.downField("etdDetail").as[Option[ETDDetail]]
      returnShipmentDetail              <- c.downField("returnShipmentDetail").as[Option[ReturnShipmentDetail]]
      deliveryOnInvoiceAcceptanceDetail <- c.downField("deliveryOnInvoiceAcceptanceDetail").as[Option[DeliveryOnInvoiceAcceptanceDetail]]
      internationalTrafficInArmsRegulationsDetail <- c
        .downField("internationalTrafficInArmsRegulationsDetail")
        .as[Option[InternationalTrafficInArmsRegulationsDetail]]
      pendingShipmentDetail <- c.downField("pendingShipmentDetail").as[Option[PendingShipmentDetail]]
      holdAtLocationDetail  <- c.downField("holdAtLocationDetail").as[Option[HoldAtLocationDetail]]
      shipmentCODDetail     <- c.downField("shipmentCODDetail").as[Option[ShipmentCODDetail]]
      shipmentDryIceDetail  <- c.downField("shipmentDryIceDetail").as[Option[ShipmentDryIceDetail1]]
      internationalControlledExportDetail <- c
        .downField("internationalControlledExportDetail")
        .as[Option[InternationalControlledExportDetail]]
      homeDeliveryPremiumDetail <- c.downField("homeDeliveryPremiumDetail").as[Option[HomeDeliveryPremiumDetail]]
    } yield RequestedShipmentVerifyShipmentSpecialServices(
      specialServiceTypes,
      etdDetail,
      returnShipmentDetail,
      deliveryOnInvoiceAcceptanceDetail,
      internationalTrafficInArmsRegulationsDetail,
      pendingShipmentDetail,
      holdAtLocationDetail,
      shipmentCODDetail,
      shipmentDryIceDetail,
      internationalControlledExportDetail,
      homeDeliveryPremiumDetail
    )
  }
}

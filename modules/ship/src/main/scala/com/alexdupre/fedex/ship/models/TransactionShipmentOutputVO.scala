package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies shipping transaction output details
  *
  * @param serviceType
  *   Indicate the FedEx serviceType used for this shipment. The results will be filtered by the serviceType value indicated.<br>Example:
  *   STANDARD_OVERNIGHT<br><a onclick='loadDocReference("servicetypes")'>click here to see Service Types</a>
  * @param shipDatestamp
  *   This is the shipment date. Default value is current date in case the date is not provided or a past date is provided.<br>Format
  *   [YYYY-MM-DD].<br>Example: 2019-10-14
  * @param serviceCategory
  *   Indicates the Service Category.<br>Example: EXPRESS
  * @param shipmentDocuments
  *   These are shipping document details.
  * @param pieceResponses
  *   Specifies the information about the pieces, received in the response.
  * @param serviceName
  *   This is the service name associated with the shipment.<br>Example: FedEx Ground
  * @param alerts
  *   These are alert details received in the response.
  * @param masterTrackingNumber
  *   This is a master tracking number for the shipment (must be unique for stand-alone open shipments, or unique within consolidation if
  *   consolidation key is provided).<br>Example: 794953535000
  */
case class TransactionShipmentOutputVO(
    serviceType: Option[String] = None,
    shipDatestamp: Option[String] = None,
    serviceCategory: Option[String] = None,
    shipmentDocuments: Option[Seq[LabelResponseVO]] = None,
    pieceResponses: Option[Seq[PieceResponse]] = None,
    serviceName: Option[String] = None,
    alerts: Option[Seq[Alert3P]] = None,
    completedShipmentDetail: Option[CompletedShipmentDetail] = None,
    shipmentAdvisoryDetails: Option[ShipmentAdvisoryDetails] = None,
    masterTrackingNumber: Option[String] = None
)

object TransactionShipmentOutputVO {

  given Encoder[TransactionShipmentOutputVO] = new Encoder.AsObject[TransactionShipmentOutputVO] {
    final def encodeObject(o: TransactionShipmentOutputVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "serviceType"             -> o.serviceType.asJson,
          "shipDatestamp"           -> o.shipDatestamp.asJson,
          "serviceCategory"         -> o.serviceCategory.asJson,
          "shipmentDocuments"       -> o.shipmentDocuments.asJson,
          "pieceResponses"          -> o.pieceResponses.asJson,
          "serviceName"             -> o.serviceName.asJson,
          "alerts"                  -> o.alerts.asJson,
          "completedShipmentDetail" -> o.completedShipmentDetail.asJson,
          "shipmentAdvisoryDetails" -> o.shipmentAdvisoryDetails.asJson,
          "masterTrackingNumber"    -> o.masterTrackingNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[TransactionShipmentOutputVO] = (c: HCursor) => {
    for {
      serviceType             <- c.downField("serviceType").as[Option[String]]
      shipDatestamp           <- c.downField("shipDatestamp").as[Option[String]]
      serviceCategory         <- c.downField("serviceCategory").as[Option[String]]
      shipmentDocuments       <- c.downField("shipmentDocuments").as[Option[Seq[LabelResponseVO]]]
      pieceResponses          <- c.downField("pieceResponses").as[Option[Seq[PieceResponse]]]
      serviceName             <- c.downField("serviceName").as[Option[String]]
      alerts                  <- c.downField("alerts").as[Option[Seq[Alert3P]]]
      completedShipmentDetail <- c.downField("completedShipmentDetail").as[Option[CompletedShipmentDetail]]
      shipmentAdvisoryDetails <- c.downField("shipmentAdvisoryDetails").as[Option[ShipmentAdvisoryDetails]]
      masterTrackingNumber    <- c.downField("masterTrackingNumber").as[Option[String]]
    } yield TransactionShipmentOutputVO(
      serviceType,
      shipDatestamp,
      serviceCategory,
      shipmentDocuments,
      pieceResponses,
      serviceName,
      alerts,
      completedShipmentDetail,
      shipmentAdvisoryDetails,
      masterTrackingNumber
    )
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This object is used to specify the Pending Shipment Type for Email label.
  *
  * @param pendingShipmentType
  *   Specifies the pending shipment type. Must include the value: EMAIL for email return shipments. <br>Not applicable for other types of
  *   shipments<br>Example: EMAIL
  * @param attachedDocuments
  *   These are the reference document details with the shipment.
  * @param expirationTimeStamp
  *   Specifies the Email Label expiration date. The maximum expiration date for an Email Return Label must be greater of equal to the day
  *   of the label request and not greater than 2 years in the future. Format[YYYY-MM-DD] <br>Example: 2020-01-01
  */
case class PendingShipmentDetail(
    pendingShipmentType: PendingShipmentDetail.PendingShipmentType,
    emailLabelDetail: EmailLabelDetail,
    processingOptions: Option[PendingShipmentProcessingOptionsRequested] = None,
    recommendedDocumentSpecification: Option[RecommendedDocumentSpecification] = None,
    attachedDocuments: Option[Seq[UploadDocumentReferenceDetail1]] = None,
    expirationTimeStamp: Option[String] = None
)

object PendingShipmentDetail {
  enum PendingShipmentType {
    case EMAIL
    case UNKNOWN_DEFAULT
  }
  object PendingShipmentType {
    given Encoder[PendingShipmentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[PendingShipmentType] =
      Decoder.decodeString.map(s => scala.util.Try(PendingShipmentType.valueOf(s)).getOrElse(PendingShipmentType.UNKNOWN_DEFAULT))
  }
  given Encoder[PendingShipmentDetail] = new Encoder.AsObject[PendingShipmentDetail] {
    final def encodeObject(o: PendingShipmentDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "pendingShipmentType"              -> o.pendingShipmentType.asJson,
          "emailLabelDetail"                 -> o.emailLabelDetail.asJson,
          "processingOptions"                -> o.processingOptions.asJson,
          "recommendedDocumentSpecification" -> o.recommendedDocumentSpecification.asJson,
          "attachedDocuments"                -> o.attachedDocuments.asJson,
          "expirationTimeStamp"              -> o.expirationTimeStamp.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PendingShipmentDetail] = (c: HCursor) => {
    for {
      pendingShipmentType              <- c.downField("pendingShipmentType").as[PendingShipmentType]
      emailLabelDetail                 <- c.downField("emailLabelDetail").as[EmailLabelDetail]
      processingOptions                <- c.downField("processingOptions").as[Option[PendingShipmentProcessingOptionsRequested]]
      recommendedDocumentSpecification <- c.downField("recommendedDocumentSpecification").as[Option[RecommendedDocumentSpecification]]
      attachedDocuments                <- c.downField("attachedDocuments").as[Option[Seq[UploadDocumentReferenceDetail1]]]
      expirationTimeStamp              <- c.downField("expirationTimeStamp").as[Option[String]]
    } yield PendingShipmentDetail(
      pendingShipmentType,
      emailLabelDetail,
      processingOptions,
      recommendedDocumentSpecification,
      attachedDocuments,
      expirationTimeStamp
    )
  }
}

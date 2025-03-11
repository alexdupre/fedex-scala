package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** The request elements for Tracking by associated shipment.
  *
  * @param associatedType
  *   The associated shipment type, such as MPS, Group MPS, or an outbound shipment which is linked to a return shipment.<br>Example:
  *   STANDARD_MPS<br>STANDARD_MPS: Single shipment with multiple packages, where tracking number got assigned to all pieces, one tracking
  *   number is treated as master tracking number while others are child tracking numbers.<br>Group MPS: Behavior is just like standard MPS
  *   but it’s created differently. The shipper must be enrolled for this by a particular reference type like ‘PO’ (purchase order).Then any
  *   package order create on the same day going to the same place that has the reference number will get grouped into GMPS
  *   relationships.<br>OUTBOUND_LINK_TO_RETURN: is an RTRN relationship.Same concept as an MPS.the OUTBOUND_LINK_TO_RETURN is the Master
  *   outbound package tracking number, that when tracked with this indicator you can get all the child return pieces.
  * @param includeDetailedScans
  *   Indicates if detailed scans are requested or not. <br>Valid values are True, or False.
  */
case class FullSchemaMultiplePieceShipment(
    masterTrackingNumberInfo: MasterTrackingInfo,
    associatedType: FullSchemaMultiplePieceShipment.AssociatedType,
    includeDetailedScans: Option[Boolean] = None,
    pagingDetails: Option[PagingDetails] = None
)

object FullSchemaMultiplePieceShipment {
  enum AssociatedType {
    case OUTBOUND_LINK_TO_RETURN
    case STANDARD_MPS
    case GROUP_MPS
    case UNKNOWN_DEFAULT
  }
  object AssociatedType {
    given Encoder[AssociatedType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AssociatedType] =
      Decoder.decodeString.map(s => scala.util.Try(AssociatedType.valueOf(s)).getOrElse(AssociatedType.UNKNOWN_DEFAULT))
  }
  given Encoder[FullSchemaMultiplePieceShipment] = new Encoder.AsObject[FullSchemaMultiplePieceShipment] {
    final def encodeObject(o: FullSchemaMultiplePieceShipment): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "masterTrackingNumberInfo" -> o.masterTrackingNumberInfo.asJson,
          "associatedType"           -> o.associatedType.asJson,
          "includeDetailedScans"     -> o.includeDetailedScans.asJson,
          "pagingDetails"            -> o.pagingDetails.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[FullSchemaMultiplePieceShipment] = (c: HCursor) => {
    for {
      masterTrackingNumberInfo <- c.downField("masterTrackingNumberInfo").as[MasterTrackingInfo]
      associatedType           <- c.downField("associatedType").as[AssociatedType]
      includeDetailedScans     <- c.downField("includeDetailedScans").as[Option[Boolean]]
      pagingDetails            <- c.downField("pagingDetails").as[Option[PagingDetails]]
    } yield FullSchemaMultiplePieceShipment(masterTrackingNumberInfo, associatedType, includeDetailedScans, pagingDetails)
  }
}

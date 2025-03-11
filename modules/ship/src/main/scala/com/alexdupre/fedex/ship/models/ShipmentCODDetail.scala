package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is the shipment level COD detail.
  *
  * @param codCollectionType
  *   Identifies the type of funds FedEx should collect upon shipment delivery<br>Example: CASH
  * @param codRecipient
  *   Descriptive data of the Cash On Delivery along with their details of the physical location.
  * @param remitToName
  *   Specify the name of the person or company receiving the secured/unsecured funds payment<br>Example: remitToName
  * @param codCollectionAmount
  *   Indicate the COD collection amount.
  * @param returnReferenceIndicatorType
  *   Indicates which type of reference information to include on the COD return shipping label.<br>Example: INVOICE
  * @param shipmentCodAmount
  *   Indicate the COD amount for this shipment.
  */
case class ShipmentCODDetail(
    codCollectionType: ShipmentCODDetail.CodCollectionType,
    addTransportationChargesDetail: Option[CODTransportationChargesDetail] = None,
    codRecipient: Option[Party1] = None,
    remitToName: Option[String] = None,
    financialInstitutionContactAndAddress: Option[ContactAndAddress] = None,
    codCollectionAmount: Option[Money] = None,
    returnReferenceIndicatorType: Option[ShipmentCODDetail.ReturnReferenceIndicatorType] = None,
    shipmentCodAmount: Option[Money] = None
)

object ShipmentCODDetail {
  enum CodCollectionType {
    case ANY
    case CASH
    case GUARANTEED_FUNDS
    case COMPANY_CHECK
    case PERSONAL_CHECK
    case UNKNOWN_DEFAULT
  }
  object CodCollectionType {
    given Encoder[CodCollectionType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CodCollectionType] =
      Decoder.decodeString.map(s => scala.util.Try(CodCollectionType.valueOf(s)).getOrElse(CodCollectionType.UNKNOWN_DEFAULT))
  }

  enum ReturnReferenceIndicatorType {
    case INVOICE
    case PO
    case REFERENCE
    case TRACKING
    case UNKNOWN_DEFAULT
  }
  object ReturnReferenceIndicatorType {
    given Encoder[ReturnReferenceIndicatorType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ReturnReferenceIndicatorType] = Decoder.decodeString.map(s =>
      scala.util.Try(ReturnReferenceIndicatorType.valueOf(s)).getOrElse(ReturnReferenceIndicatorType.UNKNOWN_DEFAULT)
    )
  }
  given Encoder[ShipmentCODDetail] = new Encoder.AsObject[ShipmentCODDetail] {
    final def encodeObject(o: ShipmentCODDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "codCollectionType"                     -> o.codCollectionType.asJson,
          "addTransportationChargesDetail"        -> o.addTransportationChargesDetail.asJson,
          "codRecipient"                          -> o.codRecipient.asJson,
          "remitToName"                           -> o.remitToName.asJson,
          "financialInstitutionContactAndAddress" -> o.financialInstitutionContactAndAddress.asJson,
          "codCollectionAmount"                   -> o.codCollectionAmount.asJson,
          "returnReferenceIndicatorType"          -> o.returnReferenceIndicatorType.asJson,
          "shipmentCodAmount"                     -> o.shipmentCodAmount.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShipmentCODDetail] = (c: HCursor) => {
    for {
      codCollectionType                     <- c.downField("codCollectionType").as[CodCollectionType]
      addTransportationChargesDetail        <- c.downField("addTransportationChargesDetail").as[Option[CODTransportationChargesDetail]]
      codRecipient                          <- c.downField("codRecipient").as[Option[Party1]]
      remitToName                           <- c.downField("remitToName").as[Option[String]]
      financialInstitutionContactAndAddress <- c.downField("financialInstitutionContactAndAddress").as[Option[ContactAndAddress]]
      codCollectionAmount                   <- c.downField("codCollectionAmount").as[Option[Money]]
      returnReferenceIndicatorType          <- c.downField("returnReferenceIndicatorType").as[Option[ReturnReferenceIndicatorType]]
      shipmentCodAmount                     <- c.downField("shipmentCodAmount").as[Option[Money]]
    } yield ShipmentCODDetail(
      codCollectionType,
      addTransportationChargesDetail,
      codRecipient,
      remitToName,
      financialInstitutionContactAndAddress,
      codCollectionAmount,
      returnReferenceIndicatorType,
      shipmentCodAmount
    )
  }
}

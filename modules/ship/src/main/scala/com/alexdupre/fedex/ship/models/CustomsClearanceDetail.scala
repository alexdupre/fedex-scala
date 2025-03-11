package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are customs clearance details. Required for International and intra-country Shipments.
  *
  * @param commodities
  *   Indicates the details about the dutiable packages. Maximum upto 999 commodities per shipment.
  * @param regulatoryControls
  *   These are the regulatory controls applicable to the shipment.<br> Example:USMCA,FOOD_OR_PERISHABLE
  * @param brokers
  *   Specify broker information. Use this option only if you are using Broker Select Option for your shipment. A country code must be
  *   specified in addition to one of the following address items: postal code, city, or location id.
  * @param freightOnValue
  *   Specify the risk owner for the Freight shipment.This element is only mandatory or valid for Intra India shipments.<br>Example:
  *   OWN_RISK
  * @param isDocumentOnly
  *   Defaults to false. Only used for international Express requests to indicate if just documents are being shipped or not. A valude of
  *   DERIVED will cause the value to be determined by PMIS based on the specified commodities information<br>Example: false
  * @param importerOfRecord
  *   The descriptive data for the importer of Record for the shipment and their physical address, contact and account number information.
  * @param generatedDocumentLocale
  *   This is the locale for generated document.<br>Example: en_US<br><a onclick='loadDocReference("locales")'>click here to see
  *   Locales</a><br>Note: If the locale is left blank or an invalid locale is entered, an error message is returned in response.
  * @param totalCustomsValue
  *   This is the total customs value.
  * @param partiesToTransactionAreRelated
  *   Specify if the transacting parties are related.
  * @param insuranceCharge
  *   Specify insurance charges if applicable. <br><i>Note: FedEx does not provide insurance of any kind.</i>
  */
case class CustomsClearanceDetail(
    commercialInvoice: CommercialInvoice,
    commodities: Seq[Commodity],
    regulatoryControls: Option[Seq[CustomsClearanceDetail.RegulatoryControls]] = None,
    brokers: Option[Seq[BrokerDetail]] = None,
    freightOnValue: Option[CustomsClearanceDetail.FreightOnValue] = None,
    dutiesPayment: Option[Payment1] = None,
    isDocumentOnly: Option[Boolean] = None,
    recipientCustomsId: Option[RecipientCustomsId] = None,
    customsOption: Option[CustomsOptionDetail] = None,
    importerOfRecord: Option[Party1] = None,
    generatedDocumentLocale: Option[String] = None,
    exportDetail: Option[ExportDetail] = None,
    totalCustomsValue: Option[Money] = None,
    partiesToTransactionAreRelated: Option[Boolean] = None,
    declarationStatementDetail: Option[CustomsDeclarationStatementDetail] = None,
    insuranceCharge: Option[Money] = None
)

object CustomsClearanceDetail {
  enum RegulatoryControls {
    case FOOD_OR_PERISHABLE
    case USMCA
    case NOT_APPLICABLE_FOR_LOW_VALUE_CUSTOMS_EXCEPTIONS
    case NOT_IN_FREE_CIRCULATION
    case UNKNOWN_DEFAULT
  }
  object RegulatoryControls {
    given Encoder[RegulatoryControls] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RegulatoryControls] =
      Decoder.decodeString.map(s => scala.util.Try(RegulatoryControls.valueOf(s)).getOrElse(RegulatoryControls.UNKNOWN_DEFAULT))
  }

  enum FreightOnValue {
    case CARRIER_RISK
    case OWN_RISK
    case UNKNOWN_DEFAULT
  }
  object FreightOnValue {
    given Encoder[FreightOnValue] = Encoder.encodeString.contramap(_.toString)
    given Decoder[FreightOnValue] =
      Decoder.decodeString.map(s => scala.util.Try(FreightOnValue.valueOf(s)).getOrElse(FreightOnValue.UNKNOWN_DEFAULT))
  }
  given Encoder[CustomsClearanceDetail] = new Encoder.AsObject[CustomsClearanceDetail] {
    final def encodeObject(o: CustomsClearanceDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "commercialInvoice"              -> o.commercialInvoice.asJson,
          "commodities"                    -> o.commodities.asJson,
          "regulatoryControls"             -> o.regulatoryControls.asJson,
          "brokers"                        -> o.brokers.asJson,
          "freightOnValue"                 -> o.freightOnValue.asJson,
          "dutiesPayment"                  -> o.dutiesPayment.asJson,
          "isDocumentOnly"                 -> o.isDocumentOnly.asJson,
          "recipientCustomsId"             -> o.recipientCustomsId.asJson,
          "customsOption"                  -> o.customsOption.asJson,
          "importerOfRecord"               -> o.importerOfRecord.asJson,
          "generatedDocumentLocale"        -> o.generatedDocumentLocale.asJson,
          "exportDetail"                   -> o.exportDetail.asJson,
          "totalCustomsValue"              -> o.totalCustomsValue.asJson,
          "partiesToTransactionAreRelated" -> o.partiesToTransactionAreRelated.asJson,
          "declarationStatementDetail"     -> o.declarationStatementDetail.asJson,
          "insuranceCharge"                -> o.insuranceCharge.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomsClearanceDetail] = (c: HCursor) => {
    for {
      commercialInvoice              <- c.downField("commercialInvoice").as[CommercialInvoice]
      commodities                    <- c.downField("commodities").as[Seq[Commodity]]
      regulatoryControls             <- c.downField("regulatoryControls").as[Option[Seq[RegulatoryControls]]]
      brokers                        <- c.downField("brokers").as[Option[Seq[BrokerDetail]]]
      freightOnValue                 <- c.downField("freightOnValue").as[Option[FreightOnValue]]
      dutiesPayment                  <- c.downField("dutiesPayment").as[Option[Payment1]]
      isDocumentOnly                 <- c.downField("isDocumentOnly").as[Option[Boolean]]
      recipientCustomsId             <- c.downField("recipientCustomsId").as[Option[RecipientCustomsId]]
      customsOption                  <- c.downField("customsOption").as[Option[CustomsOptionDetail]]
      importerOfRecord               <- c.downField("importerOfRecord").as[Option[Party1]]
      generatedDocumentLocale        <- c.downField("generatedDocumentLocale").as[Option[String]]
      exportDetail                   <- c.downField("exportDetail").as[Option[ExportDetail]]
      totalCustomsValue              <- c.downField("totalCustomsValue").as[Option[Money]]
      partiesToTransactionAreRelated <- c.downField("partiesToTransactionAreRelated").as[Option[Boolean]]
      declarationStatementDetail     <- c.downField("declarationStatementDetail").as[Option[CustomsDeclarationStatementDetail]]
      insuranceCharge                <- c.downField("insuranceCharge").as[Option[Money]]
    } yield CustomsClearanceDetail(
      commercialInvoice,
      commodities,
      regulatoryControls,
      brokers,
      freightOnValue,
      dutiesPayment,
      isDocumentOnly,
      recipientCustomsId,
      customsOption,
      importerOfRecord,
      generatedDocumentLocale,
      exportDetail,
      totalCustomsValue,
      partiesToTransactionAreRelated,
      declarationStatementDetail,
      insuranceCharge
    )
  }
}

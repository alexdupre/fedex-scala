package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to provide Commercial Invoice details. This element is required for electronic upload of CI data. It will serve to
  * create/transmit an electronic Commercial Invoice through the FedEx system.<br>Customers are responsible for printing their own
  * Commercial Invoice.<br>If you would like FedEx to generate a Commercial Invoice and transmit it to Customs for clearance purposes, you
  * need to specify that in the ETDDetail/RequestedDocumentCopies element.<br>Supports maximum of 99 commodity line items.
  *
  * @param originatorName
  *   The originatorName that will populate the Commercial Invoice (or Pro Forma).<br>Example: originator name
  * @param comments
  *   The comments that will populate the Commercial Invoice (or Pro Forma). Only the comments specified in the first two indexes of the
  *   array will be printed on the invoice and other comments would be ignored as the limitation is set for only two indexes. It considers
  *   the comment which is in the first index as a Special Instructions, Hence the comment at first index will be printed under special
  *   instructions and the other will be printed at comments section in the Commercial Invoice Document. <br>Example: comments
  * @param customerReferences
  *   These are additional customer reference data for commercial invoice.
  * @param taxesOrMiscellaneousCharge
  *   Indicate the taxes or miscellaneous charges(other than freight charges or insurance charges) that are associated with the shipment.
  * @param taxesOrMiscellaneousChargeType
  *   Specifies the Taxes Or Miscellaneous Charge Type<br>Example: COMMISSIONS
  * @param freightCharge
  *   Indicates the freight charge added by the shipper/customer for shipping the package. Optional to the customer.
  * @param packingCosts
  *   Indicates the packing cost added by the shipper/customer for shipping the package. Optional to the customer.
  * @param handlingCosts
  *   Indicates the packing cost added by the shipper/customer for shipping the package. Optional to the customer.
  * @param declarationStatement
  *   This is the declaration statement which will populate the Commercial Invoice (or Pro Forma).<br>Maximum length is 554.<br>Example:
  *   declarationStatement
  * @param termsOfSale
  *   The termsOfSale that will populate the Commercial Invoice (or Pro Forma). Max length is 3<br>Example: FCA
  * @param specialInstructions
  *   These are special instructions that will be populated on the Commercial Invoice (or Pro Forma).<br>Example: specialInstructions
  * @param shipmentPurpose
  *   The reason for the shipment. Note: SOLD is not a valid purpose for a Proforma Invoice.<br>Example: REPAIR_AND_RETURN
  */
case class CommercialInvoice(
    originatorName: Option[String] = None,
    comments: Option[Seq[String]] = None,
    customerReferences: Option[Seq[CustomerReference]] = None,
    taxesOrMiscellaneousCharge: Option[Money] = None,
    taxesOrMiscellaneousChargeType: Option[CommercialInvoice.TaxesOrMiscellaneousChargeType] = None,
    freightCharge: Option[Money] = None,
    packingCosts: Option[Money] = None,
    handlingCosts: Option[Money] = None,
    declarationStatement: Option[String] = None,
    termsOfSale: Option[String] = None,
    specialInstructions: Option[String] = None,
    shipmentPurpose: Option[CommercialInvoice.ShipmentPurpose] = None,
    emailNotificationDetail: Option[ShipEmailDispositionDetail] = None
)

object CommercialInvoice {
  enum TaxesOrMiscellaneousChargeType {
    case COMMISSIONS
    case DISCOUNTS
    case HANDLING_FEES
    case OTHER
    case ROYALTIES_AND_LICENSE_FEES
    case TAXES
    case UNKNOWN_DEFAULT
  }
  object TaxesOrMiscellaneousChargeType {
    given Encoder[TaxesOrMiscellaneousChargeType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[TaxesOrMiscellaneousChargeType] = Decoder.decodeString.map(s =>
      scala.util.Try(TaxesOrMiscellaneousChargeType.valueOf(s)).getOrElse(TaxesOrMiscellaneousChargeType.UNKNOWN_DEFAULT)
    )
  }

  enum ShipmentPurpose {
    case GIFT
    case NOT_SOLD
    case PERSONAL_EFFECTS
    case REPAIR_AND_RETURN
    case SAMPLE
    case SOLD
    case UNKNOWN_DEFAULT
  }
  object ShipmentPurpose {
    given Encoder[ShipmentPurpose] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ShipmentPurpose] =
      Decoder.decodeString.map(s => scala.util.Try(ShipmentPurpose.valueOf(s)).getOrElse(ShipmentPurpose.UNKNOWN_DEFAULT))
  }
  given Encoder[CommercialInvoice] = new Encoder.AsObject[CommercialInvoice] {
    final def encodeObject(o: CommercialInvoice): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "originatorName"                 -> o.originatorName.asJson,
          "comments"                       -> o.comments.asJson,
          "customerReferences"             -> o.customerReferences.asJson,
          "taxesOrMiscellaneousCharge"     -> o.taxesOrMiscellaneousCharge.asJson,
          "taxesOrMiscellaneousChargeType" -> o.taxesOrMiscellaneousChargeType.asJson,
          "freightCharge"                  -> o.freightCharge.asJson,
          "packingCosts"                   -> o.packingCosts.asJson,
          "handlingCosts"                  -> o.handlingCosts.asJson,
          "declarationStatement"           -> o.declarationStatement.asJson,
          "termsOfSale"                    -> o.termsOfSale.asJson,
          "specialInstructions"            -> o.specialInstructions.asJson,
          "shipmentPurpose"                -> o.shipmentPurpose.asJson,
          "emailNotificationDetail"        -> o.emailNotificationDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CommercialInvoice] = (c: HCursor) => {
    for {
      originatorName                 <- c.downField("originatorName").as[Option[String]]
      comments                       <- c.downField("comments").as[Option[Seq[String]]]
      customerReferences             <- c.downField("customerReferences").as[Option[Seq[CustomerReference]]]
      taxesOrMiscellaneousCharge     <- c.downField("taxesOrMiscellaneousCharge").as[Option[Money]]
      taxesOrMiscellaneousChargeType <- c.downField("taxesOrMiscellaneousChargeType").as[Option[TaxesOrMiscellaneousChargeType]]
      freightCharge                  <- c.downField("freightCharge").as[Option[Money]]
      packingCosts                   <- c.downField("packingCosts").as[Option[Money]]
      handlingCosts                  <- c.downField("handlingCosts").as[Option[Money]]
      declarationStatement           <- c.downField("declarationStatement").as[Option[String]]
      termsOfSale                    <- c.downField("termsOfSale").as[Option[String]]
      specialInstructions            <- c.downField("specialInstructions").as[Option[String]]
      shipmentPurpose                <- c.downField("shipmentPurpose").as[Option[ShipmentPurpose]]
      emailNotificationDetail        <- c.downField("emailNotificationDetail").as[Option[ShipEmailDispositionDetail]]
    } yield CommercialInvoice(
      originatorName,
      comments,
      customerReferences,
      taxesOrMiscellaneousCharge,
      taxesOrMiscellaneousChargeType,
      freightCharge,
      packingCosts,
      handlingCosts,
      declarationStatement,
      termsOfSale,
      specialInstructions,
      shipmentPurpose,
      emailNotificationDetail
    )
  }
}

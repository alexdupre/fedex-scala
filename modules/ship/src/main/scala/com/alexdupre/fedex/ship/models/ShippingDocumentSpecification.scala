package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to provide all data required for additional (non-label) shipping documents to be produced.
  *
  * @param shippingDocumentTypes
  *   Conditionally required in order to obtain shipping documents.Indicates the types of shipping documents requested by the
  *   shipper<br>Example:RETURN_INSTRUCTIONS
  */
case class ShippingDocumentSpecification(
    generalAgencyAgreementDetail: Option[GeneralAgencyAgreementDetail] = None,
    returnInstructionsDetail: Option[ReturnInstructionsDetail] = None,
    op900Detail: Option[Op900Detail] = None,
    usmcaCertificationOfOriginDetail: Option[UsmcaCertificationOfOriginDetail] = None,
    usmcaCommercialInvoiceCertificationOfOriginDetail: Option[UsmcaCommercialInvoiceCertificationOfOriginDetail] = None,
    shippingDocumentTypes: Option[Seq[ShippingDocumentSpecification.ShippingDocumentTypes]] = None,
    certificateOfOrigin: Option[CertificateOfOriginDetail] = None,
    commercialInvoiceDetail: Option[CommercialInvoiceDetail] = None
)

object ShippingDocumentSpecification {
  enum ShippingDocumentTypes {
    case CERTIFICATE_OF_ORIGIN
    case COMMERCIAL_INVOICE
    case CUSTOM_PACKAGE_DOCUMENT
    case CUSTOM_SHIPMENT_DOCUMENT
    case CUSTOMER_SPECIFIED_LABELS
    case EXPORT_DECLARATION
    case GENERAL_AGENCY_AGREEMENT
    case LABEL
    case USMCA_CERTIFICATION_OF_ORIGIN
    case OP_900
    case PENDING_SHIPMENT_EMAIL_NOTIFICATION
    case PRO_FORMA_INVOICE
    case RETURN_INSTRUCTIONS
    case USMCA_COMMERCIAL_INVOICE_CERTIFICATION_OF_ORIGIN
  }
  object ShippingDocumentTypes {
    given Encoder[ShippingDocumentTypes] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ShippingDocumentTypes] = Decoder.decodeString.emapTry(s => scala.util.Try(ShippingDocumentTypes.valueOf(s)))
  }
  given Encoder[ShippingDocumentSpecification] = new Encoder.AsObject[ShippingDocumentSpecification] {
    final def encodeObject(o: ShippingDocumentSpecification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "generalAgencyAgreementDetail"                      -> o.generalAgencyAgreementDetail.asJson,
          "returnInstructionsDetail"                          -> o.returnInstructionsDetail.asJson,
          "op900Detail"                                       -> o.op900Detail.asJson,
          "usmcaCertificationOfOriginDetail"                  -> o.usmcaCertificationOfOriginDetail.asJson,
          "usmcaCommercialInvoiceCertificationOfOriginDetail" -> o.usmcaCommercialInvoiceCertificationOfOriginDetail.asJson,
          "shippingDocumentTypes"                             -> o.shippingDocumentTypes.asJson,
          "certificateOfOrigin"                               -> o.certificateOfOrigin.asJson,
          "commercialInvoiceDetail"                           -> o.commercialInvoiceDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShippingDocumentSpecification] = (c: HCursor) => {
    for {
      generalAgencyAgreementDetail     <- c.downField("generalAgencyAgreementDetail").as[Option[GeneralAgencyAgreementDetail]]
      returnInstructionsDetail         <- c.downField("returnInstructionsDetail").as[Option[ReturnInstructionsDetail]]
      op900Detail                      <- c.downField("op900Detail").as[Option[Op900Detail]]
      usmcaCertificationOfOriginDetail <- c.downField("usmcaCertificationOfOriginDetail").as[Option[UsmcaCertificationOfOriginDetail]]
      usmcaCommercialInvoiceCertificationOfOriginDetail <- c
        .downField("usmcaCommercialInvoiceCertificationOfOriginDetail")
        .as[Option[UsmcaCommercialInvoiceCertificationOfOriginDetail]]
      shippingDocumentTypes   <- c.downField("shippingDocumentTypes").as[Option[Seq[ShippingDocumentTypes]]]
      certificateOfOrigin     <- c.downField("certificateOfOrigin").as[Option[CertificateOfOriginDetail]]
      commercialInvoiceDetail <- c.downField("commercialInvoiceDetail").as[Option[CommercialInvoiceDetail]]
    } yield ShippingDocumentSpecification(
      generalAgencyAgreementDetail,
      returnInstructionsDetail,
      op900Detail,
      usmcaCertificationOfOriginDetail,
      usmcaCommercialInvoiceCertificationOfOriginDetail,
      shippingDocumentTypes,
      certificateOfOrigin,
      commercialInvoiceDetail
    )
  }
}

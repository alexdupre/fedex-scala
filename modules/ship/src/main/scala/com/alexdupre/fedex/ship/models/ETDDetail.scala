package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Use this object to specify all information on how the electronic Trade document references used with the shipment.
  *
  * @param attributes
  *   Specifies the Post Document Upload <br> Example: POST_SHIPMENT_UPLOAD_REQUESTED
  * @param attachedDocuments
  *   Use this object to specify the details regarding already uploded document(s). This object is required if the documents are uploaded
  *   Pre-Shipment uploaded documents. It is recommended to provide values for all elements under this object.
  * @param requestedDocumentTypes
  *   Indicates the types of shipping documents requested by the shipper.<br>Example: CERTIFICATE_OF_ORIGIN, COMMERCIAL_INVOICE etc.
  */
case class ETDDetail(
    attributes: Option[Seq[ETDDetail.Attributes]] = None,
    attachedDocuments: Option[Seq[UploadDocumentReferenceDetail]] = None,
    requestedDocumentTypes: Option[Seq[ETDDetail.RequestedDocumentTypes]] = None
)

object ETDDetail {
  enum RequestedDocumentTypes {
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
    case UNKNOWN_DEFAULT
  }
  object RequestedDocumentTypes {
    given Encoder[RequestedDocumentTypes] = Encoder.encodeString.contramap(_.toString)
    given Decoder[RequestedDocumentTypes] =
      Decoder.decodeString.map(s => scala.util.Try(RequestedDocumentTypes.valueOf(s)).getOrElse(RequestedDocumentTypes.UNKNOWN_DEFAULT))
  }

  enum Attributes {
    case POST_SHIPMENT_UPLOAD_REQUESTED
    case UNKNOWN_DEFAULT
  }
  object Attributes {
    given Encoder[Attributes] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Attributes] = Decoder.decodeString.map(s => scala.util.Try(Attributes.valueOf(s)).getOrElse(Attributes.UNKNOWN_DEFAULT))
  }
  given Encoder[ETDDetail] = new Encoder.AsObject[ETDDetail] {
    final def encodeObject(o: ETDDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "attributes"             -> o.attributes.asJson,
          "attachedDocuments"      -> o.attachedDocuments.asJson,
          "requestedDocumentTypes" -> o.requestedDocumentTypes.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ETDDetail] = (c: HCursor) => {
    for {
      attributes             <- c.downField("attributes").as[Option[Seq[Attributes]]]
      attachedDocuments      <- c.downField("attachedDocuments").as[Option[Seq[UploadDocumentReferenceDetail]]]
      requestedDocumentTypes <- c.downField("requestedDocumentTypes").as[Option[Seq[RequestedDocumentTypes]]]
    } yield ETDDetail(attributes, attachedDocuments, requestedDocumentTypes)
  }
}

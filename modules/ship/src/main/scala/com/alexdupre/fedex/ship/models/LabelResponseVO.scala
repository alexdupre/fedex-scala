package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are shipping document/label specific information.
  *
  * @param contentKey
  *   This is the content key of the document/label.<br>Example: content key
  * @param copiesToPrint
  *   These are the number of copies to print for the specific document type.<br>Example: 10
  * @param contentType
  *   Indicates the type of document/label.
  * @param trackingNumber
  *   This is the tracking number of the package. <br>Example: 49XXX0000XXX20032835<br>
  * @param docType
  *   This is the document type.<br>Example: PDF
  * @param alerts
  *   These are alerts received in the label response.
  * @param encodedLabel
  *   Specifies if the document is encoded.<br>Example: encoded label
  * @param url
  *   The URL of the shipping document/label<br>Example:
  *   https://.../document/v2/document/retrieve/SH,794816968200_Merge/isLabel=true&autoPrint=false<br><i>Note: The URL once created will be
  *   active for 24 hours.</i>
  */
case class LabelResponseVO(
    contentKey: Option[String] = None,
    copiesToPrint: Option[Int] = None,
    contentType: Option[LabelResponseVO.ContentType] = None,
    trackingNumber: Option[String] = None,
    docType: Option[String] = None,
    alerts: Option[Seq[Alert]] = None,
    encodedLabel: Option[String] = None,
    url: Option[String] = None
)

object LabelResponseVO {
  enum ContentType {
    case LABEL
    case BILL_OF_LADING
    case GAA_FORM
    case HAZMAT_LABEL
    case END_OF_DAY_HAZMAT_REPORT
    case MANIFEST_REPORT
    case MULTIWEIGHT_REPORT
    case MERGED_LABEL_DOCUMENTS
    case AUXILIARY
    case RETURN_INSTRUCTIONS
    case ACCEPTANCE_LABEL
    case COMMERCIAL_INVOICE
    case PROFORMA_INVOICE
    case USMCA_CERTIFICATION_OF_ORIGIN
    case CERTIFICATE_OF_ORIGIN
    case MERGED_LABELS_ONLY
    case USMCA_COMMERCIAL_INVOICE_CERTIFICATION_OF_ORIGIN
    case UNKNOWN_DEFAULT
  }
  object ContentType {
    given Encoder[ContentType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ContentType] =
      Decoder.decodeString.map(s => scala.util.Try(ContentType.valueOf(s)).getOrElse(ContentType.UNKNOWN_DEFAULT))
  }
  given Encoder[LabelResponseVO] = new Encoder.AsObject[LabelResponseVO] {
    final def encodeObject(o: LabelResponseVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "contentKey"     -> o.contentKey.asJson,
          "copiesToPrint"  -> o.copiesToPrint.asJson,
          "contentType"    -> o.contentType.asJson,
          "trackingNumber" -> o.trackingNumber.asJson,
          "docType"        -> o.docType.asJson,
          "alerts"         -> o.alerts.asJson,
          "encodedLabel"   -> o.encodedLabel.asJson,
          "url"            -> o.url.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[LabelResponseVO] = (c: HCursor) => {
    for {
      contentKey     <- c.downField("contentKey").as[Option[String]]
      copiesToPrint  <- c.downField("copiesToPrint").as[Option[Int]]
      contentType    <- c.downField("contentType").as[Option[ContentType]]
      trackingNumber <- c.downField("trackingNumber").as[Option[String]]
      docType        <- c.downField("docType").as[Option[String]]
      alerts         <- c.downField("alerts").as[Option[Seq[Alert]]]
      encodedLabel   <- c.downField("encodedLabel").as[Option[String]]
      url            <- c.downField("url").as[Option[String]]
    } yield LabelResponseVO(contentKey, copiesToPrint, contentType, trackingNumber, docType, alerts, encodedLabel, url)
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This object allows the control of label content for customization.
  *
  * @param maskedData
  *   Controls which data/sections will be suppressed.<br>Example: TOTAL_WEIGHT
  * @param regulatoryLabels
  *   Specify how the regulatory details to be provided on the labels.
  * @param additionalLabels
  *   Specify how the additional details to be provided on the labels.
  */
case class CustomerSpecifiedLabelDetail(
    maskedData: Option[Seq[CustomerSpecifiedLabelDetail.MaskedData]] = None,
    regulatoryLabels: Option[Seq[RegulatoryLabelContentDetail]] = None,
    additionalLabels: Option[Seq[AdditionalLabelsDetail]] = None,
    docTabContent: Option[DocTabContent] = None
)

object CustomerSpecifiedLabelDetail {
  enum MaskedData {
    case CUSTOMS_VALUE
    case SHIPPER_ACCOUNT_NUMBER
    case DIMENSIONS
    case DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER
    case INSURED_VALUE
    case SECONDARY_BARCODE
    case SHIPPER_INFORMATION
    case TERMS_AND_CONDITIONS
    case TOTAL_WEIGHT
    case TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER
    case UNKNOWN_DEFAULT
  }
  object MaskedData {
    given Encoder[MaskedData] = Encoder.encodeString.contramap(_.toString)
    given Decoder[MaskedData] = Decoder.decodeString.map(s => scala.util.Try(MaskedData.valueOf(s)).getOrElse(MaskedData.UNKNOWN_DEFAULT))
  }
  given Encoder[CustomerSpecifiedLabelDetail] = new Encoder.AsObject[CustomerSpecifiedLabelDetail] {
    final def encodeObject(o: CustomerSpecifiedLabelDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "maskedData"       -> o.maskedData.asJson,
          "regulatoryLabels" -> o.regulatoryLabels.asJson,
          "additionalLabels" -> o.additionalLabels.asJson,
          "docTabContent"    -> o.docTabContent.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomerSpecifiedLabelDetail] = (c: HCursor) => {
    for {
      maskedData       <- c.downField("maskedData").as[Option[Seq[MaskedData]]]
      regulatoryLabels <- c.downField("regulatoryLabels").as[Option[Seq[RegulatoryLabelContentDetail]]]
      additionalLabels <- c.downField("additionalLabels").as[Option[Seq[AdditionalLabelsDetail]]]
      docTabContent    <- c.downField("docTabContent").as[Option[DocTabContent]]
    } yield CustomerSpecifiedLabelDetail(maskedData, regulatoryLabels, additionalLabels, docTabContent)
  }
}

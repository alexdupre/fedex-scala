package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Provides details of Hazardous Commodity Option Detail
  *
  * @param labelTextOption
  *   Provides the label text option
  * @param customerSuppliedLabelText
  *   DG Data Upload Mode:- Optional<br>DG Full Validation Mode: Optional<br>Text used in labeling the commodity under control of the
  *   LabelTextOption field
  */
case class HazardousCommodityOptionDetail(
    labelTextOption: Option[HazardousCommodityOptionDetail.LabelTextOption] = None,
    customerSuppliedLabelText: Option[String] = None
)

object HazardousCommodityOptionDetail {
  enum LabelTextOption {
    case APPEND
    case OVERRIDE
    case STANDARD
    case UNKNOWN_DEFAULT
  }
  object LabelTextOption {
    given Encoder[LabelTextOption] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelTextOption] =
      Decoder.decodeString.map(s => scala.util.Try(LabelTextOption.valueOf(s)).getOrElse(LabelTextOption.UNKNOWN_DEFAULT))
  }
  given Encoder[HazardousCommodityOptionDetail] = new Encoder.AsObject[HazardousCommodityOptionDetail] {
    final def encodeObject(o: HazardousCommodityOptionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "labelTextOption"           -> o.labelTextOption.asJson,
          "customerSuppliedLabelText" -> o.customerSuppliedLabelText.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[HazardousCommodityOptionDetail] = (c: HCursor) => {
    for {
      labelTextOption           <- c.downField("labelTextOption").as[Option[LabelTextOption]]
      customerSuppliedLabelText <- c.downField("customerSuppliedLabelText").as[Option[String]]
    } yield HazardousCommodityOptionDetail(labelTextOption, customerSuppliedLabelText)
  }
}

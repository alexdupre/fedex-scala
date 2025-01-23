package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are label specification details includes the image type, printer format, and label stock for label. Can also specify specific
  * details such as doc-tab content, regulatory labels, and masking data on the label.
  *
  * @param labelStockType
  *   Label Stock Type.<br>Example: PAPER_7X475
  * @param imageType
  *   Specifies the image type of this shipping document.<br>Example:PDF
  * @param labelFormatType
  *   Specifies the label Format Type<br>Example: COMMON2D
  * @param labelOrder
  *   This is the order of the Shipping label/documents to be generated.
  * @param labelRotation
  *   This is applicable only to documents produced on thermal printers with roll stock.
  * @param labelPrintingOrientation
  *   This is applicable only to documents produced on thermal printers with roll stock.
  * @param returnedDispositionDetail
  *   Specifies a particular way in which a kind of shipping document is to be produced and provided<br>Example:RETURNED
  * @param resolution
  *   Specifies the image resolution in DPI (Dots Per Inch). Valid values are 203 & 300. If not provided or for any other value, system will
  *   default it to 203.Note: 300 DPI resolution is only allowed for ZPLII image type.
  */
case class LabelSpecification(
    labelStockType: LabelSpecification.LabelStockType,
    imageType: LabelSpecification.ImageType,
    labelFormatType: Option[LabelSpecification.LabelFormatType] = None,
    labelOrder: Option[LabelSpecification.LabelOrder] = None,
    customerSpecifiedDetail: Option[CustomerSpecifiedLabelDetail] = None,
    printedLabelOrigin: Option[ContactAndAddress] = None,
    labelRotation: Option[LabelSpecification.LabelRotation] = None,
    labelPrintingOrientation: Option[LabelSpecification.LabelPrintingOrientation] = None,
    returnedDispositionDetail: Option[String] = None,
    resolution: Option[Int] = None
)

object LabelSpecification {
  enum LabelOrder {
    case SHIPPING_LABEL_FIRST
    case SHIPPING_LABEL_LAST
  }
  object LabelOrder {
    given Encoder[LabelOrder] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelOrder] = Decoder.decodeString.emapTry(s => scala.util.Try(LabelOrder.valueOf(s)))
  }

  enum LabelFormatType {
    case COMMON2D
    case LABEL_DATA_ONLY
  }
  object LabelFormatType {
    given Encoder[LabelFormatType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelFormatType] = Decoder.decodeString.emapTry(s => scala.util.Try(LabelFormatType.valueOf(s)))
  }

  enum LabelStockType {
    case PAPER_4X6
    case STOCK_4X675
    case PAPER_4X675
    case PAPER_4X8
    case PAPER_4X9
    case PAPER_7X475
    case PAPER_85X11_BOTTOM_HALF_LABEL
    case PAPER_85X11_TOP_HALF_LABEL
    case PAPER_LETTER
    case STOCK_4X675_LEADING_DOC_TAB
    case STOCK_4X8
    case STOCK_4X9_LEADING_DOC_TAB
    case STOCK_4X6
    case STOCK_4X675_TRAILING_DOC_TAB
    case STOCK_4X9_TRAILING_DOC_TAB
    case STOCK_4X9
    case STOCK_4X85_TRAILING_DOC_TAB
    case STOCK_4X105_TRAILING_DOC_TAB
  }
  object LabelStockType {
    given Encoder[LabelStockType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelStockType] = Decoder.decodeString.emapTry(s => scala.util.Try(LabelStockType.valueOf(s)))
  }

  enum LabelRotation {
    case LEFT
    case RIGHT
    case UPSIDE_DOWN
    case NONE
  }
  object LabelRotation {
    given Encoder[LabelRotation] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelRotation] = Decoder.decodeString.emapTry(s => scala.util.Try(LabelRotation.valueOf(s)))
  }

  enum ImageType {
    case ZPLII
    case EPL2
    case PDF
    case PNG
  }
  object ImageType {
    given Encoder[ImageType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[ImageType] = Decoder.decodeString.emapTry(s => scala.util.Try(ImageType.valueOf(s)))
  }

  enum LabelPrintingOrientation {
    case BOTTOM_EDGE_OF_TEXT_FIRST
    case TOP_EDGE_OF_TEXT_FIRST
  }
  object LabelPrintingOrientation {
    given Encoder[LabelPrintingOrientation] = Encoder.encodeString.contramap(_.toString)
    given Decoder[LabelPrintingOrientation] = Decoder.decodeString.emapTry(s => scala.util.Try(LabelPrintingOrientation.valueOf(s)))
  }
  given Encoder[LabelSpecification] = new Encoder.AsObject[LabelSpecification] {
    final def encodeObject(o: LabelSpecification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "labelStockType"            -> o.labelStockType.asJson,
          "imageType"                 -> o.imageType.asJson,
          "labelFormatType"           -> o.labelFormatType.asJson,
          "labelOrder"                -> o.labelOrder.asJson,
          "customerSpecifiedDetail"   -> o.customerSpecifiedDetail.asJson,
          "printedLabelOrigin"        -> o.printedLabelOrigin.asJson,
          "labelRotation"             -> o.labelRotation.asJson,
          "labelPrintingOrientation"  -> o.labelPrintingOrientation.asJson,
          "returnedDispositionDetail" -> o.returnedDispositionDetail.asJson,
          "resolution"                -> o.resolution.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[LabelSpecification] = (c: HCursor) => {
    for {
      labelStockType            <- c.downField("labelStockType").as[LabelStockType]
      imageType                 <- c.downField("imageType").as[ImageType]
      labelFormatType           <- c.downField("labelFormatType").as[Option[LabelFormatType]]
      labelOrder                <- c.downField("labelOrder").as[Option[LabelOrder]]
      customerSpecifiedDetail   <- c.downField("customerSpecifiedDetail").as[Option[CustomerSpecifiedLabelDetail]]
      printedLabelOrigin        <- c.downField("printedLabelOrigin").as[Option[ContactAndAddress]]
      labelRotation             <- c.downField("labelRotation").as[Option[LabelRotation]]
      labelPrintingOrientation  <- c.downField("labelPrintingOrientation").as[Option[LabelPrintingOrientation]]
      returnedDispositionDetail <- c.downField("returnedDispositionDetail").as[Option[String]]
      resolution                <- c.downField("resolution").as[Option[Int]]
    } yield LabelSpecification(
      labelStockType,
      imageType,
      labelFormatType,
      labelOrder,
      customerSpecifiedDetail,
      printedLabelOrigin,
      labelRotation,
      labelPrintingOrientation,
      returnedDispositionDetail,
      resolution
    )
  }
}

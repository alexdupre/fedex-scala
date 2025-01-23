package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the shipping document format.
  *
  * @param provideInstructions
  *   For those shipping document types which have both a "form" and "instructions" component (e.g General Agency Agreement), this field
  *   indicates whether to provide the instructions.<br>Example: true
  * @param stockType
  *   Specifies the label stock type. Lists the correct type of paper for the Freight address label option.Specify valid value
  *   PAPER_4_PER_PAGE_PORTRAIT.<br>Example:PAPER_TYPE
  * @param dispositions
  *   Specifies how to create, organize, and return the document
  * @param locale
  *   These are locale details.<br>example: 'en_US'<br><a onclick='loadDocReference("locales")'>click here to see Locales</a><br>Note: If
  *   the locale is left blank or an invalid locale is entered, an error message is returned in response.
  * @param docType
  *   Specify the image format used for a shipping document.<br>Example:PDF
  */
case class ShippingDocumentFormat(
    provideInstructions: Option[Boolean] = None,
    optionsRequested: Option[DocumentFormatOptionsRequested] = None,
    stockType: Option[ShippingDocumentFormat.StockType] = None,
    dispositions: Option[Seq[ShippingDocumentDispositionDetail]] = None,
    locale: Option[String] = None,
    docType: Option[ShippingDocumentFormat.DocType] = None
)

object ShippingDocumentFormat {
  enum StockType {
    case PAPER_LETTER
  }
  object StockType {
    given Encoder[StockType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[StockType] = Decoder.decodeString.emapTry(s => scala.util.Try(StockType.valueOf(s)))
  }

  enum DocType {
    case PDF
  }
  object DocType {
    given Encoder[DocType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DocType] = Decoder.decodeString.emapTry(s => scala.util.Try(DocType.valueOf(s)))
  }
  given Encoder[ShippingDocumentFormat] = new Encoder.AsObject[ShippingDocumentFormat] {
    final def encodeObject(o: ShippingDocumentFormat): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "provideInstructions" -> o.provideInstructions.asJson,
          "optionsRequested"    -> o.optionsRequested.asJson,
          "stockType"           -> o.stockType.asJson,
          "dispositions"        -> o.dispositions.asJson,
          "locale"              -> o.locale.asJson,
          "docType"             -> o.docType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShippingDocumentFormat] = (c: HCursor) => {
    for {
      provideInstructions <- c.downField("provideInstructions").as[Option[Boolean]]
      optionsRequested    <- c.downField("optionsRequested").as[Option[DocumentFormatOptionsRequested]]
      stockType           <- c.downField("stockType").as[Option[StockType]]
      dispositions        <- c.downField("dispositions").as[Option[Seq[ShippingDocumentDispositionDetail]]]
      locale              <- c.downField("locale").as[Option[String]]
      docType             <- c.downField("docType").as[Option[DocType]]
    } yield ShippingDocumentFormat(provideInstructions, optionsRequested, stockType, dispositions, locale, docType)
  }
}

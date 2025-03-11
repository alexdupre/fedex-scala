package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are characteristics of a shipping document to be produced.
  *
  * @param provideInstructions
  *   For those shipping document types which have both a "form" and "instructions" component (e.g General Agency Agreement), this field
  *   indicates whether to provide the instructions.<br>Example: true
  * @param stockType
  *   Specifies the label stock type. Lists the correct type of paper for the Freight address label option.Specify valid value
  *   PAPER_4_PER_PAGE_PORTRAIT<br>Example:PAPER_LETTER
  * @param dispositions
  *   Details on creating, organizing, and returning the document.
  * @param locale
  *   These are locale details.<br>example: 'en_US'<br><a onclick='loadDocReference("locales")'>click here to see Locales</a><br>Note: If
  *   the locale is left blank or an invalid locale is entered, an error message is returned in response.
  * @param docType
  *   Specify the image format used for a shipping document.<br>Example:PNG
  */
case class ReturnShippingDocumentFormat(
    provideInstructions: Option[Boolean] = None,
    optionsRequested: Option[DocumentFormatOptionsRequested] = None,
    stockType: Option[ReturnShippingDocumentFormat.StockType] = None,
    dispositions: Option[Seq[ShippingDocumentDispositionDetail]] = None,
    locale: Option[String] = None,
    docType: Option[ReturnShippingDocumentFormat.DocType] = None
)

object ReturnShippingDocumentFormat {
  enum StockType {
    case PAPER_LETTER
    case UNKNOWN_DEFAULT
  }
  object StockType {
    given Encoder[StockType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[StockType] = Decoder.decodeString.map(s => scala.util.Try(StockType.valueOf(s)).getOrElse(StockType.UNKNOWN_DEFAULT))
  }

  enum DocType {
    case PNG
    case PDF
    case UNKNOWN_DEFAULT
  }
  object DocType {
    given Encoder[DocType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[DocType] = Decoder.decodeString.map(s => scala.util.Try(DocType.valueOf(s)).getOrElse(DocType.UNKNOWN_DEFAULT))
  }
  given Encoder[ReturnShippingDocumentFormat] = new Encoder.AsObject[ReturnShippingDocumentFormat] {
    final def encodeObject(o: ReturnShippingDocumentFormat): JsonObject = {
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
  given Decoder[ReturnShippingDocumentFormat] = (c: HCursor) => {
    for {
      provideInstructions <- c.downField("provideInstructions").as[Option[Boolean]]
      optionsRequested    <- c.downField("optionsRequested").as[Option[DocumentFormatOptionsRequested]]
      stockType           <- c.downField("stockType").as[Option[StockType]]
      dispositions        <- c.downField("dispositions").as[Option[Seq[ShippingDocumentDispositionDetail]]]
      locale              <- c.downField("locale").as[Option[String]]
      docType             <- c.downField("docType").as[Option[DocType]]
    } yield ReturnShippingDocumentFormat(provideInstructions, optionsRequested, stockType, dispositions, locale, docType)
  }
}

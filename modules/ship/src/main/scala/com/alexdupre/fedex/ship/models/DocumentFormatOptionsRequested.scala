package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the requested options for document format.
  *
  * @param options
  *   Indicates the format options. SUPPRESS_ADDITIONAL_LANGUAGES value will suppress English language if another language is specified in
  *   the language code field.
  */
case class DocumentFormatOptionsRequested(
    options: Option[Seq[DocumentFormatOptionsRequested.Options]] = None
)

object DocumentFormatOptionsRequested {
  enum Options {
    case SHIPPING_LABEL_FIRST
    case SHIPPING_LABEL_LAST
    case SUPPRESS_ADDITIONAL_LANGUAGES
    case UNKNOWN_DEFAULT
  }
  object Options {
    given Encoder[Options] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Options] = Decoder.decodeString.map(s => scala.util.Try(Options.valueOf(s)).getOrElse(Options.UNKNOWN_DEFAULT))
  }
  given Encoder[DocumentFormatOptionsRequested] = new Encoder.AsObject[DocumentFormatOptionsRequested] {
    final def encodeObject(o: DocumentFormatOptionsRequested): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "options" -> o.options.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[DocumentFormatOptionsRequested] = (c: HCursor) => {
    for {
      options <- c.downField("options").as[Option[Seq[Options]]]
    } yield DocumentFormatOptionsRequested(options)
  }
}

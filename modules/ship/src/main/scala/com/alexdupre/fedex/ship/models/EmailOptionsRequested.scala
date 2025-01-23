package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are to indicate how the email notifications for the pending shipment to be processed.
  *
  * @param options
  *   These are the processing options.
  */
case class EmailOptionsRequested(
    options: Option[Seq[EmailOptionsRequested.Options]] = None
)

object EmailOptionsRequested {
  enum Options {
    case PRODUCE_PAPERLESS_SHIPPING_FORMAT
    case SUPPRESS_ADDITIONAL_LANGUAGES
    case SUPPRESS_ACCESS_EMAILS
  }
  object Options {
    given Encoder[Options] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Options] = Decoder.decodeString.emapTry(s => scala.util.Try(Options.valueOf(s)))
  }
  given Encoder[EmailOptionsRequested] = new Encoder.AsObject[EmailOptionsRequested] {
    final def encodeObject(o: EmailOptionsRequested): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "options" -> o.options.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[EmailOptionsRequested] = (c: HCursor) => {
    for {
      options <- c.downField("options").as[Option[Seq[Options]]]
    } yield EmailOptionsRequested(options)
  }
}

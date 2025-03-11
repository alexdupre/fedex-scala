package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies how to e-mail shipping documents.
  *
  * @param eMailRecipients
  *   Indicates the shipping document email recipients.
  * @param locale
  *   These are locale details.<br>Example: 'en_US'<br><a onclick='loadDocReference("locales")'>click here to see locales</a><br>Note: If
  *   the locale is left blank or an invalid locale is entered, an error message is returned in response.
  * @param grouping
  *   Identifies the convention by which documents are to be grouped as email attachment.
  */
case class ShippingDocumentEmailDetail(
    eMailRecipients: Seq[ShippingDocumentEmailRecipient],
    locale: Option[String] = None,
    grouping: Option[ShippingDocumentEmailDetail.Grouping] = None
)

object ShippingDocumentEmailDetail {
  enum Grouping {
    case BY_RECIPIENT
    case NONE
    case UNKNOWN_DEFAULT
  }
  object Grouping {
    given Encoder[Grouping] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Grouping] = Decoder.decodeString.map(s => scala.util.Try(Grouping.valueOf(s)).getOrElse(Grouping.UNKNOWN_DEFAULT))
  }
  given Encoder[ShippingDocumentEmailDetail] = new Encoder.AsObject[ShippingDocumentEmailDetail] {
    final def encodeObject(o: ShippingDocumentEmailDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "eMailRecipients" -> o.eMailRecipients.asJson,
          "locale"          -> o.locale.asJson,
          "grouping"        -> o.grouping.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ShippingDocumentEmailDetail] = (c: HCursor) => {
    for {
      eMailRecipients <- c.downField("eMailRecipients").as[Seq[ShippingDocumentEmailRecipient]]
      locale          <- c.downField("locale").as[Option[String]]
      grouping        <- c.downField("grouping").as[Option[Grouping]]
    } yield ShippingDocumentEmailDetail(eMailRecipients, locale, grouping)
  }
}

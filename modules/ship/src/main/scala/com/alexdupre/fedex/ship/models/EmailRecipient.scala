package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are the recipient details for the online email return label.
  *
  * @param emailAddress
  *   This is recipient email address for notifying the return label. Maximum length 200 characters.<br> Example: neenaaaaa@abc.com
  * @param role
  *   Relationship that the emailRecipient has to the pending email return label shipments.<br> Valid Values:
  *   SHIPMENT_COMPLETOR,SHIPMENT_INITIATOR<br>Example: SHIPMENT_COMPLETOR
  * @param locale
  *   These are locale details.<br>Example: 'en_US'<br><a onclick='loadDocReference("locales")'>click here to see locales</a><br>Note: If
  *   the locale is left blank or an invalid locale is entered, an error message is returned in response.
  */
case class EmailRecipient(
    emailAddress: String,
    role: EmailRecipient.Role,
    optionsRequested: Option[EmailOptionsRequested] = None,
    locale: Option[String] = None
)

object EmailRecipient {
  enum Role {
    case SHIPMENT_COMPLETOR
    case SHIPMENT_INITIATOR
    case UNKNOWN_DEFAULT
  }
  object Role {
    given Encoder[Role] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Role] = Decoder.decodeString.map(s => scala.util.Try(Role.valueOf(s)).getOrElse(Role.UNKNOWN_DEFAULT))
  }
  given Encoder[EmailRecipient] = new Encoder.AsObject[EmailRecipient] {
    final def encodeObject(o: EmailRecipient): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "emailAddress"     -> o.emailAddress.asJson,
          "role"             -> o.role.asJson,
          "optionsRequested" -> o.optionsRequested.asJson,
          "locale"           -> o.locale.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[EmailRecipient] = (c: HCursor) => {
    for {
      emailAddress     <- c.downField("emailAddress").as[String]
      role             <- c.downField("role").as[Role]
      optionsRequested <- c.downField("optionsRequested").as[Option[EmailOptionsRequested]]
      locale           <- c.downField("locale").as[Option[String]]
    } yield EmailRecipient(emailAddress, role, optionsRequested, locale)
  }
}

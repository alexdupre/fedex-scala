package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies the language details for email notification.
  *
  * @param languageCode
  *   Identifies two-letter code for the language (e.g. en/EN, fr/FR, es/ES etc..).<br> Example: en
  * @param localeCode
  *   Identifies the two-letter code for the region, used to further identify the requested language. for example, if you request Spanish,
  *   you must include a locale code of <i>US</i> for North American Spanish, or <i>MX</i> for Mexico. <br> Example: US<br><a
  *   onclick='loadDocReference("locales")'>Click here to see Locales</a>
  */
case class Localization(
    languageCode: String,
    localeCode: Option[String] = None
)

object Localization {

  given Encoder[Localization] = new Encoder.AsObject[Localization] {
    final def encodeObject(o: Localization): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "languageCode" -> o.languageCode.asJson,
          "localeCode"   -> o.localeCode.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Localization] = (c: HCursor) => {
    for {
      languageCode <- c.downField("languageCode").as[String]
      localeCode   <- c.downField("localeCode").as[Option[String]]
    } yield Localization(languageCode, localeCode)
  }
}

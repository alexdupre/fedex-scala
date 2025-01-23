package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the phone number. Only numeric values allowed.<br> Note that phoneNumber is mandatory when homedeliveryPremiumType is
  * DATE_CERTAIN or EVENING.
  *
  * @param areaCode
  *   Area-Code<br>Example: 901
  * @param localNumber
  *   Local Number<br>Example: 3575012
  * @param extension
  *   Extension<br>Example: 200
  * @param personalIdentificationNumber
  *   Personal Identification Number<br>Example: 98712345
  */
case class PhoneNumber1(
    areaCode: Option[String] = None,
    localNumber: Option[String] = None,
    extension: Option[String] = None,
    personalIdentificationNumber: Option[String] = None
)

object PhoneNumber1 {

  given Encoder[PhoneNumber1] = new Encoder.AsObject[PhoneNumber1] {
    final def encodeObject(o: PhoneNumber1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "areaCode"                     -> o.areaCode.asJson,
          "localNumber"                  -> o.localNumber.asJson,
          "extension"                    -> o.extension.asJson,
          "personalIdentificationNumber" -> o.personalIdentificationNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PhoneNumber1] = (c: HCursor) => {
    for {
      areaCode                     <- c.downField("areaCode").as[Option[String]]
      localNumber                  <- c.downField("localNumber").as[Option[String]]
      extension                    <- c.downField("extension").as[Option[String]]
      personalIdentificationNumber <- c.downField("personalIdentificationNumber").as[Option[String]]
    } yield PhoneNumber1(areaCode, localNumber, extension, personalIdentificationNumber)
  }
}

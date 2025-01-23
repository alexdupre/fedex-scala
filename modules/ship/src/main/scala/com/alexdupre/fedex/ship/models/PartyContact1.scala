package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the contact details for this shipment.
  *
  * @param personName
  *   Specify contact name. Maximum length is 70. <br> Example: John Taylor
  * @param emailAddress
  *   Specify contact email address. Maximum length is 80. <br> Example: sample@company.com
  * @param phoneExtension
  *   Specify contact phone extension. Maximum length is 6. <br> Example: 1234
  * @param phoneNumber
  *   Specify contact phone number. <br>Minimum length is 10 and supports Maximum as 15 for certain countries using longer phone numbers.
  *   <br>Note: Recommended Maximum length is 15 and there's no specific validation will be done for the phone number. <br> Example:
  *   918xxxxx890
  * @param companyName
  *   Specify contact company name.<br>Recommended length is 35.<br>Note: There's no specific validation of the company name.
  * @param faxNumber
  *   Specify contact person's fax number. Maximum length is 15.
  */
case class PartyContact1(
    personName: Option[String] = None,
    emailAddress: Option[String] = None,
    phoneExtension: Option[String] = None,
    phoneNumber: Option[String] = None,
    companyName: Option[String] = None,
    faxNumber: Option[String] = None
)

object PartyContact1 {

  given Encoder[PartyContact1] = new Encoder.AsObject[PartyContact1] {
    final def encodeObject(o: PartyContact1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "personName"     -> o.personName.asJson,
          "emailAddress"   -> o.emailAddress.asJson,
          "phoneExtension" -> o.phoneExtension.asJson,
          "phoneNumber"    -> o.phoneNumber.asJson,
          "companyName"    -> o.companyName.asJson,
          "faxNumber"      -> o.faxNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PartyContact1] = (c: HCursor) => {
    for {
      personName     <- c.downField("personName").as[Option[String]]
      emailAddress   <- c.downField("emailAddress").as[Option[String]]
      phoneExtension <- c.downField("phoneExtension").as[Option[String]]
      phoneNumber    <- c.downField("phoneNumber").as[Option[String]]
      companyName    <- c.downField("companyName").as[Option[String]]
      faxNumber      <- c.downField("faxNumber").as[Option[String]]
    } yield PartyContact1(personName, emailAddress, phoneExtension, phoneNumber, companyName, faxNumber)
  }
}

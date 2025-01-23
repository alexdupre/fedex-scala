package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the contact details of the shipper.
  *
  * @param personName
  *   Specify contact person name.<br>Recommended length is 70.<br>Note: There's no specific validation for the person name.<br> Example:
  *   John Taylor
  * @param emailAddress
  *   Specify contact email address. Maximum length is 80. <br> Example: sample@company.com
  * @param phoneNumber
  *   Specify contact phone number. <br>Minimum length is 10 and supports maximum of 15 for certain countries using longer phone numbers.
  *   <br>Note: Recommended Maximum length is 15 and there's no specific validation will be done for the phone number. <br> Example:
  *   918xxxxx890
  * @param phoneExtension
  *   Specify contact phone extension. <br>Note: Recommended length is 6. There's no specific validation for the phone extension. <br>
  *   Example: 1234
  * @param faxNumber
  *   Specify contact fax number.<br>Note: Recommended length is 15. There's no specific validation for the fax number.<br> Example:
  *   1234567890
  * @param companyName
  *   Specify contact company name.<br>Recommended length is 35.<br>Note: There's no specific validation for the company name.
  */
case class ContactVerify(
    personName: Option[String] = None,
    emailAddress: Option[String] = None,
    phoneNumber: Option[String] = None,
    phoneExtension: Option[String] = None,
    faxNumber: Option[String] = None,
    companyName: Option[String] = None
)

object ContactVerify {

  given Encoder[ContactVerify] = new Encoder.AsObject[ContactVerify] {
    final def encodeObject(o: ContactVerify): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "personName"     -> o.personName.asJson,
          "emailAddress"   -> o.emailAddress.asJson,
          "phoneNumber"    -> o.phoneNumber.asJson,
          "phoneExtension" -> o.phoneExtension.asJson,
          "faxNumber"      -> o.faxNumber.asJson,
          "companyName"    -> o.companyName.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ContactVerify] = (c: HCursor) => {
    for {
      personName     <- c.downField("personName").as[Option[String]]
      emailAddress   <- c.downField("emailAddress").as[Option[String]]
      phoneNumber    <- c.downField("phoneNumber").as[Option[String]]
      phoneExtension <- c.downField("phoneExtension").as[Option[String]]
      faxNumber      <- c.downField("faxNumber").as[Option[String]]
      companyName    <- c.downField("companyName").as[Option[String]]
    } yield ContactVerify(personName, emailAddress, phoneNumber, phoneExtension, faxNumber, companyName)
  }
}

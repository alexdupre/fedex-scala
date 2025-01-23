package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Indicate the contact details for this shipment.
  *
  * @param phoneNumber
  *   The shipper's phone number. <br>Minimum length is 10 and supports maximum of 15 for certain countries using longer phone
  *   numbers.<br>Note: For US and CA, a phone number must have exactly 10 digits, plus an optional leading country code of '1' or
  *   '+1'.<br>Example: 918xxxxx890
  * @param personName
  *   Specify contact name. Maximum length is 70. <br>Note: Either the companyName or personName is mandatory.<br> Example: John Taylor
  * @param emailAddress
  *   Specify contact email address. Maximum length is 80. <br> Example: sample@company.com
  * @param phoneExtension
  *   Specify contact phone extension. Maximum length is 6. <br> Example: 1234
  * @param companyName
  *   The shipper's company name. Max length is 35.<br>Example: FedEx
  */
case class PartyContact(
    phoneNumber: String,
    personName: Option[String] = None,
    emailAddress: Option[String] = None,
    phoneExtension: Option[String] = None,
    companyName: Option[String] = None
)

object PartyContact {

  given Encoder[PartyContact] = new Encoder.AsObject[PartyContact] {
    final def encodeObject(o: PartyContact): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "phoneNumber"    -> o.phoneNumber.asJson,
          "personName"     -> o.personName.asJson,
          "emailAddress"   -> o.emailAddress.asJson,
          "phoneExtension" -> o.phoneExtension.asJson,
          "companyName"    -> o.companyName.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PartyContact] = (c: HCursor) => {
    for {
      phoneNumber    <- c.downField("phoneNumber").as[String]
      personName     <- c.downField("personName").as[Option[String]]
      emailAddress   <- c.downField("emailAddress").as[Option[String]]
      phoneExtension <- c.downField("phoneExtension").as[Option[String]]
      companyName    <- c.downField("companyName").as[Option[String]]
    } yield PartyContact(phoneNumber, personName, emailAddress, phoneExtension, companyName)
  }
}

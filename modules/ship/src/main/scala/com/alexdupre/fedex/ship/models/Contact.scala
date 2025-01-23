package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the contact information.
  *
  * @param personName
  *   Specify person name.<br>Example: John Taylor
  * @param emailAddress
  *   Specify email address.<br>Example: sample@company.com
  * @param phoneNumber
  *   The shippers phone number. <br>Minimum length is 10 and supports maximum of 15 for certain countries using longer phone
  *   numbers.<br>Note: For US and CA, a phone number must have exactly 10 digits, plus an optional leading country code of 1 or
  *   +1.<br>Example: 918xxxxx890
  * @param phoneExtension
  *   The shipper's phone extension. Max length is 6.<br>Example: 91
  * @param companyName
  *   Specify company name.
  */
case class Contact(
    personName: Option[String] = None,
    emailAddress: Option[String] = None,
    phoneNumber: Option[String] = None,
    phoneExtension: Option[String] = None,
    companyName: Option[String] = None
)

object Contact {

  given Encoder[Contact] = new Encoder.AsObject[Contact] {
    final def encodeObject(o: Contact): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "personName"     -> o.personName.asJson,
          "emailAddress"   -> o.emailAddress.asJson,
          "phoneNumber"    -> o.phoneNumber.asJson,
          "phoneExtension" -> o.phoneExtension.asJson,
          "companyName"    -> o.companyName.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Contact] = (c: HCursor) => {
    for {
      personName     <- c.downField("personName").as[Option[String]]
      emailAddress   <- c.downField("emailAddress").as[Option[String]]
      phoneNumber    <- c.downField("phoneNumber").as[Option[String]]
      phoneExtension <- c.downField("phoneExtension").as[Option[String]]
      companyName    <- c.downField("companyName").as[Option[String]]
    } yield Contact(personName, emailAddress, phoneNumber, phoneExtension, companyName)
  }
}

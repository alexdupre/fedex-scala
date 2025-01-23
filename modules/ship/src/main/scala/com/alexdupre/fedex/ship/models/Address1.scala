package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is detailed information on physical location. May be used as an actual physical address (place to which one could go), or as a
  * container of address parts which should be handled as a unit (such as a city-state-ZIP combination within the U.S.).
  *
  * @param streetLines
  *   This is the combination of number, street name, etc. <br>Note: At least one line is required and streetlines more than 3 will be
  *   ignored. Empty lines should not be included. Maximum length per line is 35.<br>Example: [10 FedEx Parkway, Suite 302, .etc.]
  * @param city
  *   This is a placeholder for City Name.<br>Note: This is conditional and not required in all the requests.<br>Note: It is recommended for
  *   Express shipments for the most accurate ODA and OPA surcharges.<br>Example: Beverly Hills
  * @param stateOrProvinceCode
  *   This is a placeholder for State or Province code.State code is required for US, CA, PR and not required for other countries.
  *   Conditional. Max length is 2.<br>Example: CA<br><a onclick='loadDocReference("canadaprovincecodes")'>click here to see State or
  *   Province Code</a>
  * @param postalCode
  *   Indicate the Postal code. This is Optional for non postal-aware countries. Maximum length is 10.<br> Example: 65247<br><a
  *   onclick='loadDocReference("postalawarecountries")'>click here to see Postal aware countries</a>
  * @param countryCode
  *   This is the two-letter country code.<br>Maximum length is 2.<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>click
  *   here to see Country codes</a>
  * @param residential
  *   Indicates whether this address is residential (as opposed to commercial).<br> Example: false
  */
case class Address1(
    streetLines: Option[Seq[String]] = None,
    city: Option[String] = None,
    stateOrProvinceCode: Option[String] = None,
    postalCode: Option[String] = None,
    countryCode: Option[String] = None,
    residential: Option[Boolean] = None
)

object Address1 {

  given Encoder[Address1] = new Encoder.AsObject[Address1] {
    final def encodeObject(o: Address1): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "streetLines"         -> o.streetLines.asJson,
          "city"                -> o.city.asJson,
          "stateOrProvinceCode" -> o.stateOrProvinceCode.asJson,
          "postalCode"          -> o.postalCode.asJson,
          "countryCode"         -> o.countryCode.asJson,
          "residential"         -> o.residential.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[Address1] = (c: HCursor) => {
    for {
      streetLines         <- c.downField("streetLines").as[Option[Seq[String]]]
      city                <- c.downField("city").as[Option[String]]
      stateOrProvinceCode <- c.downField("stateOrProvinceCode").as[Option[String]]
      postalCode          <- c.downField("postalCode").as[Option[String]]
      countryCode         <- c.downField("countryCode").as[Option[String]]
      residential         <- c.downField("residential").as[Option[Boolean]]
    } yield Address1(streetLines, city, stateOrProvinceCode, postalCode, countryCode, residential)
  }
}

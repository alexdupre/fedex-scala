package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This is detailed information on physical location. May be used as an actual physical address (place to which one could go), or as a
  * container of address parts which should be handled as a unit (such as a city-state-ZIP combination within the US).
  *
  * @param streetLines
  *   Combination of number, street name, etc. At least one line is required for a valid physical address. Empty lines should not be
  *   included. Max Length is 35.<br>Example: [1550 Union Blvd,Suite 302]
  * @param city
  *   The name of city, town of the recipient.Max length is 35.<br>Example: Beverly Hills
  * @param countryCode
  *   This is the two-letter country code.<br>Maximum length is 2.<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>click
  *   here to see Country codes</a>
  * @param stateOrProvinceCode
  *   The US States,Canada and Puerto Rico Province codes of the recipient. The Format and presence of this field may vary depending on the
  *   country.State code is required for US, CA, PR and not required for other countries. Conditional. Max length is 2.<br>Example: CA
  * @param postalCode
  *   This is the postal code.<br>Note: This is Optional for non postal-aware countries. Maximum length is 10.<br>Example: 65247<br><a
  *   onclick='loadDocReference("postalawarecountries")'>click here to see Postal aware countries</a>
  * @param residential
  *   Indicates whether this address is residential (as opposed to commercial).<br>Example: false
  */
case class PartyAddress(
    streetLines: Seq[String],
    city: String,
    countryCode: String,
    stateOrProvinceCode: Option[String] = None,
    postalCode: Option[String] = None,
    residential: Option[Boolean] = None
)

object PartyAddress {

  given Encoder[PartyAddress] = new Encoder.AsObject[PartyAddress] {
    final def encodeObject(o: PartyAddress): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "streetLines"         -> o.streetLines.asJson,
          "city"                -> o.city.asJson,
          "countryCode"         -> o.countryCode.asJson,
          "stateOrProvinceCode" -> o.stateOrProvinceCode.asJson,
          "postalCode"          -> o.postalCode.asJson,
          "residential"         -> o.residential.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[PartyAddress] = (c: HCursor) => {
    for {
      streetLines         <- c.downField("streetLines").as[Seq[String]]
      city                <- c.downField("city").as[String]
      countryCode         <- c.downField("countryCode").as[String]
      stateOrProvinceCode <- c.downField("stateOrProvinceCode").as[Option[String]]
      postalCode          <- c.downField("postalCode").as[Option[String]]
      residential         <- c.downField("residential").as[Option[Boolean]]
    } yield PartyAddress(streetLines, city, countryCode, stateOrProvinceCode, postalCode, residential)
  }
}

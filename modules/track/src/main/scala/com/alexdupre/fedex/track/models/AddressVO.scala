package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Address where the package was actually delivered. Contrast with destinationAddress, which is the location to which the package was
  * intended to be delivered. Addresses may differ due to delivery to a behavior, hold at FedEx location, etc.
  *
  * @param streetLines
  *   Combination of number, street name, etc. At least one line is required for a valid physical address; empty lines should not be
  *   included.<br> Example: ["1043 North Easy Street", "Suite 999"]
  * @param countryCode
  *   Placeholder for country code (2 characters) for the address.<br>Example: US<br><a onclick='loadDocReference("countrycodes")'>Click
  *   here to see Country Codes</a>
  * @param classification
  *   Specifies the FedEx classification type for an address. <br>Valid values are BUSINESS, RESIDENTIAL, MIXED, UNKNOWN.<br> Example:
  *   BUSINESS
  * @param residential
  *   Placeholder to indicate whether the address is residential (as opposed to commercial).
  * @param city
  *   Conditional<br>The name of the city, town, etc. <br> Example: SEATTLE
  * @param stateOrProvinceCode
  *   This is a placeholder for State or Province code. <br> Example: CA<br><a onclick='loadDocReference("canadaprovincecodes")'>Click here
  *   to see State/Province Code</a>
  * @param postalCode
  *   Placeholder to specify postal code for the address. Postal Code is required for postal-aware countries.<br> Example: 98101<br><a
  *   onclick='loadDocReference("postalawarecountries")'>Click here to see Postal aware countries</a>
  * @param countryName
  *   Field holds the fully spelled out name of a country.<br> Example: United States
  */
case class AddressVO(
    streetLines: Seq[String],
    countryCode: String,
    classification: Option[String] = None,
    residential: Option[Boolean] = None,
    city: Option[String] = None,
    stateOrProvinceCode: Option[String] = None,
    postalCode: Option[String] = None,
    countryName: Option[String] = None
)

object AddressVO {

  given Encoder[AddressVO] = new Encoder.AsObject[AddressVO] {
    final def encodeObject(o: AddressVO): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "streetLines"         -> o.streetLines.asJson,
          "countryCode"         -> o.countryCode.asJson,
          "classification"      -> o.classification.asJson,
          "residential"         -> o.residential.asJson,
          "city"                -> o.city.asJson,
          "stateOrProvinceCode" -> o.stateOrProvinceCode.asJson,
          "postalCode"          -> o.postalCode.asJson,
          "countryName"         -> o.countryName.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AddressVO] = (c: HCursor) => {
    for {
      streetLines         <- c.downField("streetLines").as[Seq[String]]
      countryCode         <- c.downField("countryCode").as[String]
      classification      <- c.downField("classification").as[Option[String]]
      residential         <- c.downField("residential").as[Option[Boolean]]
      city                <- c.downField("city").as[Option[String]]
      stateOrProvinceCode <- c.downField("stateOrProvinceCode").as[Option[String]]
      postalCode          <- c.downField("postalCode").as[Option[String]]
      countryName         <- c.downField("countryName").as[Option[String]]
    } yield AddressVO(streetLines, countryCode, classification, residential, city, stateOrProvinceCode, postalCode, countryName)
  }
}

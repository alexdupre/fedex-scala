package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Identifies and describes an individual hazardous commodity. For 201001 load, this is based on data from the FedEx Ground Hazardous
  * Materials Shipping Guide.
  *
  * @param sequenceNumber
  *   In conjunction with the regulatory identifier, this field uniquely identifies a specific hazardous materials commodity.<br>Example:
  *   876
  * @param packingInstructions
  *   Specifies Packing Instructions.<br>Example: packingInstructions
  * @param subsidiaryClasses
  *   Specifies subsidiary Classes.<br>Example:["Subsidiary Classes"]
  * @param labelText
  *   Specifies Hazard Label Text.<br>Example: labelText
  * @param tunnelRestrictionCode
  *   There are five categories of tunnel categorization with A representing the least restrictive and E as the most restrictive. Category
  *   A, as the least restrictive, will not be sign-posted. Category E, the most restrictive, only allows the passage of UN2919, UN3291,
  *   UN3331, UN3359 and UN3373.<br>Example: UN2919
  * @param specialProvisions
  *   Specifies Special Provisions if any.<br>Example: specialProvisions
  * @param properShippingNameAndDescription
  *   Fully-expanded descriptive text for a hazardous commodity.<br>Example: properShippingNameAndDescription
  * @param technicalName
  *   Specifies Technical Name.<br>Example: technicalName
  * @param symbols
  *   Specifies Symbols.<br>Example: symbols
  * @param authorization
  *   Information related to quantity limitations and operator or state variations as may be applicable to the dangerous goods commodity.
  * @param attributes
  *   Specifies attributes.<br>Example: ["attributes"]
  * @param id
  *   Specifies the Identification.<br>Example: 1234
  * @param packingGroup
  *   Specifies packing Group.<br>Example: Packing Group
  * @param properShippingName
  *   Specifies Proper Shipping Name.<br>Example: Proper Shipping Name
  * @param hazardClass
  *   Specifies hazard class.<br>Example: Hazard Class
  */
case class ValidatedHazardousCommodityDescription(
    sequenceNumber: Option[Int] = None,
    packingInstructions: Option[String] = None,
    subsidiaryClasses: Option[Seq[String]] = None,
    labelText: Option[String] = None,
    tunnelRestrictionCode: Option[String] = None,
    specialProvisions: Option[String] = None,
    properShippingNameAndDescription: Option[String] = None,
    technicalName: Option[String] = None,
    symbols: Option[String] = None,
    authorization: Option[String] = None,
    attributes: Option[Seq[String]] = None,
    id: Option[String] = None,
    packingGroup: Option[String] = None,
    properShippingName: Option[String] = None,
    hazardClass: Option[String] = None
)

object ValidatedHazardousCommodityDescription {

  given Encoder[ValidatedHazardousCommodityDescription] = new Encoder.AsObject[ValidatedHazardousCommodityDescription] {
    final def encodeObject(o: ValidatedHazardousCommodityDescription): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "sequenceNumber"                   -> o.sequenceNumber.asJson,
          "packingInstructions"              -> o.packingInstructions.asJson,
          "subsidiaryClasses"                -> o.subsidiaryClasses.asJson,
          "labelText"                        -> o.labelText.asJson,
          "tunnelRestrictionCode"            -> o.tunnelRestrictionCode.asJson,
          "specialProvisions"                -> o.specialProvisions.asJson,
          "properShippingNameAndDescription" -> o.properShippingNameAndDescription.asJson,
          "technicalName"                    -> o.technicalName.asJson,
          "symbols"                          -> o.symbols.asJson,
          "authorization"                    -> o.authorization.asJson,
          "attributes"                       -> o.attributes.asJson,
          "id"                               -> o.id.asJson,
          "packingGroup"                     -> o.packingGroup.asJson,
          "properShippingName"               -> o.properShippingName.asJson,
          "hazardClass"                      -> o.hazardClass.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ValidatedHazardousCommodityDescription] = (c: HCursor) => {
    for {
      sequenceNumber                   <- c.downField("sequenceNumber").as[Option[Int]]
      packingInstructions              <- c.downField("packingInstructions").as[Option[String]]
      subsidiaryClasses                <- c.downField("subsidiaryClasses").as[Option[Seq[String]]]
      labelText                        <- c.downField("labelText").as[Option[String]]
      tunnelRestrictionCode            <- c.downField("tunnelRestrictionCode").as[Option[String]]
      specialProvisions                <- c.downField("specialProvisions").as[Option[String]]
      properShippingNameAndDescription <- c.downField("properShippingNameAndDescription").as[Option[String]]
      technicalName                    <- c.downField("technicalName").as[Option[String]]
      symbols                          <- c.downField("symbols").as[Option[String]]
      authorization                    <- c.downField("authorization").as[Option[String]]
      attributes                       <- c.downField("attributes").as[Option[Seq[String]]]
      id                               <- c.downField("id").as[Option[String]]
      packingGroup                     <- c.downField("packingGroup").as[Option[String]]
      properShippingName               <- c.downField("properShippingName").as[Option[String]]
      hazardClass                      <- c.downField("hazardClass").as[Option[String]]
    } yield ValidatedHazardousCommodityDescription(
      sequenceNumber,
      packingInstructions,
      subsidiaryClasses,
      labelText,
      tunnelRestrictionCode,
      specialProvisions,
      properShippingNameAndDescription,
      technicalName,
      symbols,
      authorization,
      attributes,
      id,
      packingGroup,
      properShippingName,
      hazardClass
    )
  }
}

package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** RegulatoryProhibition Model
  *
  * @param derivedHarmonizedCode
  *   Indicates the derived harmonized code value<br>Example: 01
  * @param commodityIndex
  *   Indicates one based index identifying the associated commodity.<br>Example: 12
  * @param source
  *   Indicates the prohibition source type.<br>Example: source
  * @param categories
  *   Indicate the shipment rule type.<br>Example: ["categories"]
  * @param `type`
  *   Indicates the prohibition type.<br>Example: type
  * @param status
  *   Indicates the prohibitory status.<br>Example: status
  */
case class RegulatoryProhibition(
    derivedHarmonizedCode: Option[String] = None,
    advisory: Option[Message] = None,
    commodityIndex: Option[Int] = None,
    source: Option[String] = None,
    categories: Option[Seq[String]] = None,
    `type`: Option[String] = None,
    waiver: Option[RegulatoryWaiver] = None,
    status: Option[String] = None
)

object RegulatoryProhibition {

  given Encoder[RegulatoryProhibition] = new Encoder.AsObject[RegulatoryProhibition] {
    final def encodeObject(o: RegulatoryProhibition): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "derivedHarmonizedCode" -> o.derivedHarmonizedCode.asJson,
          "advisory"              -> o.advisory.asJson,
          "commodityIndex"        -> o.commodityIndex.asJson,
          "source"                -> o.source.asJson,
          "categories"            -> o.categories.asJson,
          "type"                  -> o.`type`.asJson,
          "waiver"                -> o.waiver.asJson,
          "status"                -> o.status.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RegulatoryProhibition] = (c: HCursor) => {
    for {
      derivedHarmonizedCode <- c.downField("derivedHarmonizedCode").as[Option[String]]
      advisory              <- c.downField("advisory").as[Option[Message]]
      commodityIndex        <- c.downField("commodityIndex").as[Option[Int]]
      source                <- c.downField("source").as[Option[String]]
      categories            <- c.downField("categories").as[Option[Seq[String]]]
      `type`                <- c.downField("type").as[Option[String]]
      waiver                <- c.downField("waiver").as[Option[RegulatoryWaiver]]
      status                <- c.downField("status").as[Option[String]]
    } yield RegulatoryProhibition(derivedHarmonizedCode, advisory, commodityIndex, source, categories, `type`, waiver, status)
  }
}

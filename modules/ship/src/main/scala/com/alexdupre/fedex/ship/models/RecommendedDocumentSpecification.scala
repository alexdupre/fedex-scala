package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are documents that are recommended to be included with the shipment.<br>Example:ANTIQUE_STATEMENT_EUROPEAN_UNION
  *
  * @param types
  *   This is the recommended document Type.<br><a onclick='loadDocReference("shipmentdocumenttype")'>click here to see shipment document
  *   type</a>
  */
case class RecommendedDocumentSpecification(
    types: Seq[RecommendedDocumentSpecification.Types]
)

object RecommendedDocumentSpecification {
  enum Types {
    case ANTIQUE_STATEMENT_EUROPEAN_UNION
    case ANTIQUE_STATEMENT_UNITED_STATES
    case ASSEMBLER_DECLARATION
    case BEARING_WORKSHEET
    case CERTIFICATE_OF_SHIPMENTS_TO_SYRIA
    case COMMERCIAL_INVOICE_FOR_THE_CARIBBEAN_COMMON_MARKET
    case CONIFEROUS_SOLID_WOOD_PACKAGING_MATERIAL_TO_THE_PEOPLES_REPUBLIC_OF_CHINA
    case DECLARATION_FOR_FREE_ENTRY_OF_RETURNED_AMERICAN_PRODUCTS
    case DECLARATION_OF_BIOLOGICAL_STANDARDS
    case DECLARATION_OF_IMPORTED_ELECTRONIC_PRODUCTS_SUBJECT_TO_RADIATION_CONTROL_STANDARD
    case ELECTRONIC_INTEGRATED_CIRCUIT_WORKSHEET
    case FILM_AND_VIDEO_CERTIFICATE
    case INTERIM_FOOTWEAR_INVOICE
    case USMCA_CERTIFICATION_OF_ORIGIN_CANADA_ENGLISH
    case USMCA_CERTIFICATION_OF_ORIGIN_CANADA_FRENCH
    case USMCA_CERTIFICATION_OF_ORIGIN_SPANISH
    case USMCA_CERTIFICATION_OF_ORIGIN_UNITED_STATES
    case PACKING_LIST
    case PRINTED_CIRCUIT_BOARD_WORKSHEET
    case REPAIRED_WATCH_BREAKOUT_WORKSHEET
    case STATEMENT_REGARDING_THE_IMPORT_OF_RADIO_FREQUENCY_DEVICES
    case TOXIC_SUBSTANCES_CONTROL_ACT
    case UNITED_STATES_CARIBBEAN_BASIN_TRADE_PARTNERSHIP_ACT_CERTIFICATE_OF_ORIGIN_TEXTILES
    case UNITED_STATES_CARIBBEAN_BASIN_TRADE_PARTNERSHIP_ACT_CERTIFICATE_OF_ORIGIN_NON_TEXTILES
    case UNITED_STATES_NEW_WATCH_WORKSHEET
    case UNITED_STATES_WATCH_REPAIR_DECLARATION
    case UNKNOWN_DEFAULT
  }
  object Types {
    given Encoder[Types] = Encoder.encodeString.contramap(_.toString)
    given Decoder[Types] = Decoder.decodeString.map(s => scala.util.Try(Types.valueOf(s)).getOrElse(Types.UNKNOWN_DEFAULT))
  }
  given Encoder[RecommendedDocumentSpecification] = new Encoder.AsObject[RecommendedDocumentSpecification] {
    final def encodeObject(o: RecommendedDocumentSpecification): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "types" -> o.types.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[RecommendedDocumentSpecification] = (c: HCursor) => {
    for {
      types <- c.downField("types").as[Seq[Types]]
    } yield RecommendedDocumentSpecification(types)
  }
}

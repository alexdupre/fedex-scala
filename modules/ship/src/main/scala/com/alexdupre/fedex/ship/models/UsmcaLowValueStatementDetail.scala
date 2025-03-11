package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specify the low Value statement necessary for printing the USMCA for Customs documentation.
  *
  * @param customsRole
  *   Customs Role Type.<br>Example: EXPORTER
  * @param countryOfOriginLowValueDocumentRequested
  *   Specify the country Of Origin of Low Value Document for Customs declaration.<br>Example:true
  */
case class UsmcaLowValueStatementDetail(
    customsRole: UsmcaLowValueStatementDetail.CustomsRole,
    countryOfOriginLowValueDocumentRequested: Option[Boolean] = None
)

object UsmcaLowValueStatementDetail {
  enum CustomsRole {
    case EXPORTER
    case IMPORTER
    case UNKNOWN_DEFAULT
  }
  object CustomsRole {
    given Encoder[CustomsRole] = Encoder.encodeString.contramap(_.toString)
    given Decoder[CustomsRole] =
      Decoder.decodeString.map(s => scala.util.Try(CustomsRole.valueOf(s)).getOrElse(CustomsRole.UNKNOWN_DEFAULT))
  }
  given Encoder[UsmcaLowValueStatementDetail] = new Encoder.AsObject[UsmcaLowValueStatementDetail] {
    final def encodeObject(o: UsmcaLowValueStatementDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "customsRole"                              -> o.customsRole.asJson,
          "countryOfOriginLowValueDocumentRequested" -> o.countryOfOriginLowValueDocumentRequested.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[UsmcaLowValueStatementDetail] = (c: HCursor) => {
    for {
      customsRole                              <- c.downField("customsRole").as[CustomsRole]
      countryOfOriginLowValueDocumentRequested <- c.downField("countryOfOriginLowValueDocumentRequested").as[Option[Boolean]]
    } yield UsmcaLowValueStatementDetail(customsRole, countryOfOriginLowValueDocumentRequested)
  }
}

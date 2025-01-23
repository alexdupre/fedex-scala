package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies about the statements to be declared for Customs. */
case class CustomsDeclarationStatementDetail(
    usmcaLowValueStatementDetail: UsmcaLowValueStatementDetail
)

object CustomsDeclarationStatementDetail {

  given Encoder[CustomsDeclarationStatementDetail] = new Encoder.AsObject[CustomsDeclarationStatementDetail] {
    final def encodeObject(o: CustomsDeclarationStatementDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "usmcaLowValueStatementDetail" -> o.usmcaLowValueStatementDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[CustomsDeclarationStatementDetail] = (c: HCursor) => {
    for {
      usmcaLowValueStatementDetail <- c.downField("usmcaLowValueStatementDetail").as[UsmcaLowValueStatementDetail]
    } yield CustomsDeclarationStatementDetail(usmcaLowValueStatementDetail)
  }
}

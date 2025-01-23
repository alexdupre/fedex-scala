package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** This element specifies Signature option details.
  *
  * @param signatureReleaseNumber
  *   This is release number.<br>Example: 23456
  */
case class SignatureOptionDetail(
    signatureReleaseNumber: Option[String] = None
)

object SignatureOptionDetail {

  given Encoder[SignatureOptionDetail] = new Encoder.AsObject[SignatureOptionDetail] {
    final def encodeObject(o: SignatureOptionDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "signatureReleaseNumber" -> o.signatureReleaseNumber.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[SignatureOptionDetail] = (c: HCursor) => {
    for {
      signatureReleaseNumber <- c.downField("signatureReleaseNumber").as[Option[String]]
    } yield SignatureOptionDetail(signatureReleaseNumber)
  }
}

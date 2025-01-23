package com.alexdupre.fedex.track.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** Specifies return information related to a return shipment.
  *
  * @param authorizationName
  *   Name of person authorizing the return, entered by the customer.
  * @param reasonDetail
  *   Specifies the return reason details.
  */
case class ReturnDetail(
    authorizationName: Option[String] = None,
    reasonDetail: Option[Seq[ReasonDetail]] = None
)

object ReturnDetail {

  given Encoder[ReturnDetail] = new Encoder.AsObject[ReturnDetail] {
    final def encodeObject(o: ReturnDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "authorizationName" -> o.authorizationName.asJson,
          "reasonDetail"      -> o.reasonDetail.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[ReturnDetail] = (c: HCursor) => {
    for {
      authorizationName <- c.downField("authorizationName").as[Option[String]]
      reasonDetail      <- c.downField("reasonDetail").as[Option[Seq[ReasonDetail]]]
    } yield ReturnDetail(authorizationName, reasonDetail)
  }
}

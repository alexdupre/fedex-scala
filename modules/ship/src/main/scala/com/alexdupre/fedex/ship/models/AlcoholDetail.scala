package com.alexdupre.fedex.ship.models

import io.circe.syntax.*
import io.circe.{Decoder, Encoder, HCursor, JsonObject}

/** These are detcontentails for the package containing alcohol. This is required for alcohol special service. The alcoholRecipientType is
  * required.
  *
  * @param alcoholRecipientType
  *   Specify the Alcohol Recipient Type of the shipment. <br> Example: LICENSEE
  * @param shipperAgreementType
  *   Specify the type of entity, the shipper for alcohol shipment is registered such as fulfillment house, retailer or a winery.
  */
case class AlcoholDetail(
    alcoholRecipientType: Option[AlcoholDetail.AlcoholRecipientType] = None,
    shipperAgreementType: Option[String] = None
)

object AlcoholDetail {
  enum AlcoholRecipientType {
    case LICENSEE
    case CONSUMER
  }
  object AlcoholRecipientType {
    given Encoder[AlcoholRecipientType] = Encoder.encodeString.contramap(_.toString)
    given Decoder[AlcoholRecipientType] = Decoder.decodeString.emapTry(s => scala.util.Try(AlcoholRecipientType.valueOf(s)))
  }
  given Encoder[AlcoholDetail] = new Encoder.AsObject[AlcoholDetail] {
    final def encodeObject(o: AlcoholDetail): JsonObject = {
      JsonObject.fromIterable(
        Vector(
          "alcoholRecipientType" -> o.alcoholRecipientType.asJson,
          "shipperAgreementType" -> o.shipperAgreementType.asJson
        )
      )
    }
  }.mapJson(_.dropNullValues)
  given Decoder[AlcoholDetail] = (c: HCursor) => {
    for {
      alcoholRecipientType <- c.downField("alcoholRecipientType").as[Option[AlcoholRecipientType]]
      shipperAgreementType <- c.downField("shipperAgreementType").as[Option[String]]
    } yield AlcoholDetail(alcoholRecipientType, shipperAgreementType)
  }
}
